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
    Spinner spinner;
    RadioButton rbAscendente;
    RadioButton rbDescendente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_gasolinera_view);

        spinner = findViewById(R.id.spinnerCombustible);
        rbAscendente = findViewById(R.id.rbAscendente);
        rbDescendente = findViewById(R.id.rbDescendente);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnConfirmar.setOnClickListener(onClickListener -> {
            presenter.onButtonConfirmarClick();
        });
        presenter = new FiltrosPresenter();
        presenter.init(this);
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

    /**
     * @see IFiltrosContract.View#showMainActivity()
     */
    public void showMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
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

}
