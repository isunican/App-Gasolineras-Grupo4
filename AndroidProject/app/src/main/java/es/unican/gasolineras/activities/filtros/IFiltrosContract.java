package es.unican.gasolineras.activities.filtros;


import android.content.Intent;
import android.widget.RadioButton;
import android.widget.Spinner;

import es.unican.gasolineras.model.TipoCombustible;

/**
 * The Presenter-View contract for the "filtros" activity.
 *
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
         * The presenter is informed that the button Confirmar has been clicked
         * Only the View should call this method
         */
        public void onButtonConfirmarClick();

        /**
         * The presenter saves the filters selected
         * @return the intent with the filters selected
         * @param spinner the spinner where the fuel type will be selected
         * @param rbAscendente the radio button that indicates that the fuel stations will be ordered by price
         * from lesser to greater
         * @param rbDescendente the radio button that indicates that the fuel stations will be ordered by price
         * from greater to lesser
         * @param intent the intent where the filters will be saved
         */
        public Intent seleccionarFiltros(Spinner spinner, RadioButton rbAscendente, RadioButton rbDescendente, Intent intent);

        /**
         * The presenter saves the order filter selected
         * @return the order selected
         * @param rbAscendente the radio button that indicates that the fuel stations will be ordered by price
         * from lesser to greater
         * @param rbDescendente the radio button that indicates that the fuel stations will be ordered by price
         * from greater to lesser
         *
         */
        public String seleccionarFiltrosOrden(RadioButton rbAscendente, RadioButton rbDescendente);

        /**
         * The presenter saves the fuel type filters selected
         * @return the intent with the filters selected
         * @param spinner the spinner where the fuel type will be selected
         * from lesser to greater
         * from greater to lesser
         */
        public TipoCombustible seleccionarFiltrosTipoCombustible(Spinner spinner);
    }

    /**
     *
     * Only the Presenter should call these methods.
     */
    public interface View {
        /**
         * The view is requested to open the filtro activity with the values selected
         * showing a correct gas station list.
         * Only the Presenter should call this method
         */
        public void lanzarBusquedaConFiltros();

        /**
         * Select the last filters
         */
        public void opcionesIniciales();

    }
}
