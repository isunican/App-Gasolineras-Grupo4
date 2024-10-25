package es.unican.gasolineras.activities.filtros;


import android.content.Intent;
import android.widget.RadioButton;
import android.widget.Spinner;

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
         * The presenter is informed that the Back Arrow item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuBackArrowClick();

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

    }

    /**
     *
     * Only the Presenter should call these methods.
     */
    public interface View {

        /**
         * The view is requested to open the main activity.
         * Only the Presenter should call this method
         */
        public void showMainActivity();

        /**
         * The view is requested to open the filtro activity with the values selected
         * showing a correct gas station list.
         * Only the Presenter should call this method
         */
        public void lanzarBusquedaConFiltros();

    }
}
