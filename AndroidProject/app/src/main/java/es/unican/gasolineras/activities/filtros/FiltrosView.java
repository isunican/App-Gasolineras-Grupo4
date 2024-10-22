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
import es.unican.gasolineras.activities.combustible.CombustiblePresenter;
import es.unican.gasolineras.activities.combustible.CombustibleView;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.model.TipoCombustible;

public class FiltrosView extends AppCompatActivity implements IFiltrosContract.View {

    private FiltrosPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_gasolinera_view);

        Spinner spinner = findViewById(R.id.spinnerCombustible);
        RadioButton rbAscendente = findViewById(R.id.rbAscendente);
        RadioButton rbDescendente = findViewById(R.id.rbDescendente);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new FiltrosPresenter();
        presenter.init(this);

        btnConfirmar.setOnClickListener(v -> {
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

                default:
                    // Manejar caso en que no haya una opción válida (opcional)
                    break;
            }

            // Pasar el valor del enumerado en el Intent
            Intent intent = new Intent(this, CombustibleView.class);
            intent.putExtra("tipoCombustible", tipoCombustible.toString());
            //0-mayor a menor 1-menor a mayor 2-nada
            String order = "";
            if (rbAscendente.isChecked()) {
                order = "1";
            } else if (rbDescendente.isChecked()) {
                order = "0";
            } else {
                order = "2";
            }
            intent.putExtra("order", order);
            startActivity(intent);
        });
    }

    /**
     * This creates the menu that is shown in the action bar (the upper toolbar)
     * @param menu The options menu in which you place your items.
     *
     * @return true because we are defining a new menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_combustible, menu);
        return true;
    }

    /**
     * This is called when an item in the action bar menu is selected.
     * @param item The menu item that was selected.
     *
     * @return true if we have handled the selection
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemBackArrow) {
            presenter.onMenuBackArrowClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init() {

    }

    public void showMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }

}
