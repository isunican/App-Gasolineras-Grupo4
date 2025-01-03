package es.unican.gasolineras.activities.combustible;

import java.util.List;


import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.TipoCombustible;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The Presenter-View contract for the "filtro combustible" activity.
 * The "filtro combustible" activity shows a list of gas stations which sell a specific fuel
 */
public interface ICombustibleContract {

    /**
     *
     * Only the View should call these methods.
     */
    public interface Presenter {

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view
         * @param tipoCombustible
         */
        public void init(ICombustibleContract.View view, TipoCombustible tipoCombustible);

        /**
         * The presenter is informed that a gas station has been clicked
         * Only the View should call this method
         * @param station the station that has been clicked
         */
        public void onStationClicked(Gasolinera station);

        /**
         * The presenter is informed that the Info item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuInfoClicked();

        /**
         * The presenter is informed that the Payment History item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuHistoryClicked();

        /**
         * The presenter is informed that the filtros item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuFiltrosClicked();

        /**
         * The presenter is informed that the descuentos item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuDescuentosClicked();

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
         * Returns a repository that can be called by the Presenter to retrieve gas stations.
         * This method must be located in the view because Android resources must be accessed
         * in order to instantiate a repository (for example Internet Access). This requires
         * dependencies to Android. We want to keep the Presenter free of Android dependencies,
         * therefore the Presenter should be unable to instantiate repositories and must rely on
         * the view to create the repository.
         * Only the Presenter should call this method
         * @return
         */
        public IGasolinerasRepository getGasolinerasRepository();

        /**
         * The view is requested to display the given list of gas stations.
         * Only the Presenter should call this method
         * @param stations the list of charging stations
         */
        public void showStations(List<Gasolinera> stations);

        /**
         * The view is requested to display a notification indicating  that the gas
         * stations were loaded correctly.
         * Only the Presenter should call this method
         * @param stations
         */
        public void showLoadCorrect(int stations);

        /**
         * The view is requested to display a notificacion indicating that the gas
         * stations were not loaded correctly.
         * Only the Presenter should call this method
         */
        public void showLoadError();

        /**
         * The view is requested to display the detailed view of the given gas station.
         * Only the Presenter should call this method
         * @param station the charging station
         */
        public void showStationDetails(Gasolinera station);

        /**
         * The view is requested to open the info activity.
         * Only the Presenter should call this method
         */
        public void showInfoActivity();


        /**
         * The view is requested to open the payment history activity.
         * Only the Presenter should call this method
         */
        public void showHistoryActivity();

        /**
         * The view is requested to open the filtros activity.
         * Only the Presenter should call this method
         */
        public void showFiltrosActivity();

        /**
         * The view is requested to open the descuentos activity.
         * Only the Presenter should call this method
         */
        public void showDescuentosActivity();

    }
}
