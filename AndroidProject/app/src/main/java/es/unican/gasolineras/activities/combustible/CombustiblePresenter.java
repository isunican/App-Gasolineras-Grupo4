package es.unican.gasolineras.activities.combustible;

import android.util.Log;
import android.view.View;

import java.io.Console;
import java.util.Comparator;
import java.util.List;


import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDCCAAs;
import es.unican.gasolineras.model.TipoCombustible;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The presenter of the "filtro combustible" activity of the application. It controls {@link CombustibleView}
 */
public class CombustiblePresenter implements ICombustibleContract.Presenter{

    /** The view that is controlled by this presenter */
    private ICombustibleContract.View view;
    private TipoCombustible tipoCombustible;

    /**
     * @see ICombustibleContract.Presenter#init(ICombustibleContract.View, TipoCombustible)
     * @param view the view to control
     * @param tipoCombustible the type of fuel of the gas station
     */
    @Override
    public void init(ICombustibleContract.View view, TipoCombustible tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
        this.view = view;
        this.view.init();
        load();
    }

    /**
     * @see ICombustibleContract.Presenter#onStationClicked(Gasolinera)
     * @param station the station that has been clicked
     */
    @Override
    public void onStationClicked(Gasolinera station) {
        view.showStationDetails(station);
    }

    /**
     * @see ICombustibleContract.Presenter#onMenuBackArrowClick()
     */
    @Override
    public void onMenuBackArrowClick() {view.showMainActivity();}

    /**
     * Loads the gas stations from the repository, and sends them to the view
     */
    private void load() {
        IGasolinerasRepository repository = view.getGasolinerasRepository();
        ICallBack callBack = new ICallBack() {

            @Override
            public void onSuccess(List<Gasolinera> stations) {
                view.showStations(stations);
                view.showLoadCorrect(stations.size());
            }

            @Override
            public void onFailure(Throwable e) {
                view.showLoadError();
                view.showLoadError();
            }
        };
            repository.requestGasolinerasCombustible(callBack, IDCCAAs.CANTABRIA.id, tipoCombustible.id);
    }

    /**
     * Ordena las gasolineras segun el precio del producto en base al orden pasado
     * @param orden orden ascendente, descendento o nulo
     * @param stations lista de gasolineras a ordenar
     */
    public void orderStations(int orden, List<Gasolinera> stations) {
        if (orden == 1) {
            // Orden ascendente
            stations.sort(Comparator.comparingDouble(g -> g.getPrecioProducto()));
        } else if (orden == 0) {
            // Orden descendente
            stations.sort((g1, g2) -> Double.compare(
                    g2.getPrecioProducto(),
                    g1.getPrecioProducto()
            ));
        } else {
            return; //sin orden
        }
    }
}
