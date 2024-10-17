package es.unican.gasolineras.activities.combustible;

import android.util.Log;
import android.view.View;

import java.io.Console;
import java.util.List;


import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDCCAAs;
import es.unican.gasolineras.model.TipoCombustible;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

public class CombustiblePresenter implements ICombustibleContract.Presenter{


    /** The view that is controlled by this presenter */
    private ICombustibleContract.View view;
    private TipoCombustible tipoCombustible;


    @Override
    public void init(ICombustibleContract.View view, TipoCombustible tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
        this.view = view;
        this.view.init();
        load();
    }


    @Override
    public void onStationClicked(Gasolinera station) {
        view.showStationDetails(station);
    }


    @Override
    public void onMenuInfoClicked() {
        view.showInfoActivity();
    }

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
}
