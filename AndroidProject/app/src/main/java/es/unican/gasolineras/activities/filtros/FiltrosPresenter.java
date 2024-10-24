package es.unican.gasolineras.activities.filtros;


import android.content.Intent;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.combustible.CombustibleView;
import es.unican.gasolineras.activities.combustible.ICombustibleContract;
import es.unican.gasolineras.model.TipoCombustible;

/**
 * The presenter of the "filtros" activity of the application. It controls {@link FiltrosView}
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
    }

    /**
     * @see IFiltrosContract.Presenter#onMenuBackArrowClick()
     */
    @Override
    public void onMenuBackArrowClick() {view.showMainActivity();}

    /**
     * @see IFiltrosContract.Presenter#onButtonConfirmarClick()
     */
    @Override
    public void onButtonConfirmarClick() {view.lanzarBusquedaConFiltros();}

    /**
     * @see IFiltrosContract.Presenter#seleccionarFiltros(Spinner, RadioButton, RadioButton, Intent)
     */
    @Override
    public Intent seleccionarFiltros(Spinner spinner,RadioButton rbAscendente, RadioButton rbDescendente, Intent intent) {

        // Obtener el valor seleccionado del Spinner
        String seleccion = spinner.getSelectedItem().toString();

        // Asignar el valor del enumerado basado en la selección
        TipoCombustible tipoCombustible = null;

        switch (seleccion) {
            case "Biodiesel":
                tipoCombustible = TipoCombustible.BIODIESEL;
                break;
            case "Bioetanol":
                tipoCombustible = TipoCombustible.BIOETANOL;
                break;
            case "Gas Natural Comprimido":
                tipoCombustible = TipoCombustible.GAS_NATURAL_COMPRIMIDO;
                break;
            case "Gas Natural Licuado":
                tipoCombustible = TipoCombustible.GAS_NATURAL_LICUADO;
                break;
            case "Gases licuados del petróleo":
                tipoCombustible = TipoCombustible.GASES_LICUADOS_DEL_PETROLEO;
                break;
            case "Gasoleo A":
                tipoCombustible = TipoCombustible.GASOLEO_A_HABITUAL;
                break;
            case "Gasoleo B":
                tipoCombustible = TipoCombustible.GASOLEO_B;
                break;
            case "Gasoleo Premium":
                tipoCombustible = TipoCombustible.GASOLEO_PREMIUM;
                break;
            case "Gasolina 95 E10":
                tipoCombustible = TipoCombustible.GASOLINA_95_E10;
                break;
            case "Gasolina 95 E5":
                tipoCombustible = TipoCombustible.GASOLINA_95_E5;
                break;
            case "Gasolina 95 E5 Premium":
                tipoCombustible = TipoCombustible.GASOLINA_95_E5_PREMIUM;
                break;
            case "Gasolina 98 E10":
                tipoCombustible = TipoCombustible.GASOLINA_98_E10;
                break;
            case "Gasolina 98 E5":
                tipoCombustible = TipoCombustible.GASOLINA_98_E5;
                break;
            case "Hidrogeno":
                tipoCombustible = TipoCombustible.HIDROGENO;
                break;
        }

        //0-mayor a menor 1-menor a mayor 2-nada
        String order = "";
        if (rbAscendente.isChecked()) {
            order = "1";
        } else if (rbDescendente.isChecked()) {
            order = "0";
        } else {
            order = "2";
        }

        // Pasar el valor del enumerado y el orden en el Intent
        intent.putExtra("tipoCombustible", tipoCombustible.toString());
        intent.putExtra("order", order);

        return intent;
    }
}
