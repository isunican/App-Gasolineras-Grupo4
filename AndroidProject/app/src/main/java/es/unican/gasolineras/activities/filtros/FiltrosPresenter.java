package es.unican.gasolineras.activities.filtros;


import es.unican.gasolineras.activities.combustible.CombustibleView;
import es.unican.gasolineras.activities.combustible.ICombustibleContract;

/**
 * The presenter of the "filtro combustible" activity of the application. It controls {@link CombustibleView}
 */
public class FiltrosPresenter implements IFiltrosContract.Presenter{


    /** The view that is controlled by this presenter */
    private IFiltrosContract.View view;

    /**
     * @see IFiltrosContract.Presenter#init(IFiltrosContract.View)
     * @param view the view to control
     */
    @Override
    public void init(IFiltrosContract.View view) {
        this.view = view;
        this.view.init();
    }

    /**
     * @see ICombustibleContract.Presenter#onMenuBackArrowClick()
     */
    @Override
    public void onMenuBackArrowClick() {view.showMainActivity();}

}
