package es.unican.gasolineras.activities.filtros;

import java.util.List;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.TipoCombustible;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The Presenter-View contract for the "filtro combustible" activity.
 * The "filtro combustible" activity shows a list of gas stations which sell a specific fuel
 */
public interface IFiltrosContract {

    /**
     *
     * Only the View should call these methods.
     */
    public interface Presenter {

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view the view to control
         */
        public void init(IFiltrosContract.View view);

        /**
         * The presenter is informed that the Back Arrow item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuBackArrowClick();

    }

    /**
     *
     * Only the Presenter should call these methods.
     */
    public interface View {

        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        /**
         * The view is requested to open the main activity.
         * Only the Presenter should call this method
         */
        public void showMainActivity();

    }
}
