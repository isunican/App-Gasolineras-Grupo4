package es.unican.gasolineras.activities.filtros;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.combustible.CombustibleView;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.model.TipoCombustible;

public class FiltrosView extends AppCompatActivity implements IFiltrosContract.View {

    private FiltrosPresenter presenter;
    private String order = "";
    private TipoCombustible tipoCombustible = TipoCombustible.BIODIESEL;
    Spinner spinner;
    RadioButton rbAscendente;
    RadioButton rbDescendente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_gasolinera_view);
        String orderS = getIntent().getStringExtra("order");

        spinner = findViewById(R.id.spinnerCombustible);
        rbAscendente = findViewById(R.id.rbAscendente);
        rbDescendente = findViewById(R.id.rbDescendente);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        opcionesIniciales();

        btnConfirmar.setOnClickListener(onClickListener -> {
            presenter.onButtonConfirmarClick();
        });
        presenter = new FiltrosPresenter();
        presenter.init(this);
    }


    /**
     * @see IFiltrosContract.View#lanzarBusquedaConFiltros()
     */
    @Override
    public void lanzarBusquedaConFiltros() {
        Intent intent = new Intent(this, CombustibleView.class);
        intent = presenter.seleccionarFiltros(spinner, rbAscendente, rbDescendente, intent);
        startActivity(intent);
    }


    @Override
    public void opcionesIniciales() {
        order = getIntent().getStringExtra("order");
        //Parametro de la llamadas desde el main
        if (order == null) {
            order = "2";
        }
        //Orden
        rbDescendente.setChecked(false);
        rbAscendente.setChecked(false);
        if (order.equals("0")) {
            rbDescendente.setChecked(true);
        } else if (order.equals("1")) {
            rbAscendente.setChecked(true);
        }

        //Tipo combustible
        String tipoCombustibleStr = getIntent().getStringExtra("tipoCombustible");
        if (tipoCombustibleStr == null) {
            tipoCombustibleStr = "BIODIESEL";
        }
        tipoCombustible = TipoCombustible.valueOf(tipoCombustibleStr);
        switch (tipoCombustible) {
            case BIODIESEL:
                spinner.setSelection(0);
                break;
            case BIOETANOL:
                spinner.setSelection(1);
                break;
            case GAS_NATURAL_COMPRIMIDO:
                spinner.setSelection(2);
                break;
            case GAS_NATURAL_LICUADO:
                spinner.setSelection(3);
                break;
            case GASES_LICUADOS_DEL_PETROLEO:
                spinner.setSelection(4);
                break;
            case GASOLEO_A_HABITUAL:
                spinner.setSelection(5);
                break;
            case GASOLEO_B:
                spinner.setSelection(6);
                break;
            case GASOLEO_PREMIUM:
                spinner.setSelection(7);
                break;
            case GASOLINA_95_E10:
                spinner.setSelection(8);
                break;
            case GASOLINA_95_E5:
                spinner.setSelection(9);
                break;
            case GASOLINA_95_E5_PREMIUM:
                spinner.setSelection(10);
                break;
            case GASOLINA_98_E10:
                spinner.setSelection(11);
                break;
            case GASOLINA_98_E5:
                spinner.setSelection(12);
                break;
            case HIDROGENO:
                spinner.setSelection(13);
                break;
            default:
                spinner.setSelection(0);
                break;
        }

    }

}
