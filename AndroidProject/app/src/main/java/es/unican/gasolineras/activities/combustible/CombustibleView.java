package es.unican.gasolineras.activities.combustible;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.parceler.Parcels;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.details.DetailsView;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.TipoCombustible;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The "filtro combustible"" view of the application. It shows a list of gas stations
 * which sell a specific fuel.
 */
@AndroidEntryPoint
public class CombustibleView extends AppCompatActivity implements ICombustibleContract.View {

    /** The presenter of this view */
    private CombustiblePresenter presenter;
    private TipoCombustible tipoCombustible;
    private int order;

    /** The repository to access the data. This is automatically injected by Hilt in this class */
    @Inject
    IGasolinerasRepository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        // The default theme does not include a toolbar.
        // In this app the toolbar is explicitly declared in the layout
        // Set this toolbar as the activity ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate presenter and launch initial business logic
        presenter = new CombustiblePresenter();

        //type of fuel
        String tipoCombustibleStr = getIntent().getStringExtra("tipoCombustible");
        tipoCombustible = TipoCombustible.valueOf(tipoCombustibleStr);

        String orderStr = getIntent().getStringExtra("order");
        order = Integer.parseInt(orderStr);

        presenter.init(this, tipoCombustible);
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
        // initialize on click listeners (when clicking on a station in the list)
        ListView list = findViewById(R.id.lvStations);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Gasolinera station = (Gasolinera) parent.getItemAtPosition(position);
            presenter.onStationClicked(station);
        });
    }


    @Override
    public IGasolinerasRepository getGasolinerasRepository() {
        return repository;
    }

    @Override
    public void showStations(List<Gasolinera> stations) {
        if (stations.isEmpty()) {
            Toast.makeText(this, "No se han localizado gasolineras con el combustible: " + tipoCombustible, Toast.LENGTH_SHORT).show();
        }
        presenter.orderStations(order, stations);
        ListView list = findViewById(R.id.lvStations);
        CombustibleArrayAdapter adapter = new CombustibleArrayAdapter(this, stations, tipoCombustible);
        list.setAdapter(adapter);
    }

    @Override
    public void showLoadCorrect(int stations) {
        Toast.makeText(this, "Cargadas " + stations + " gasolineras", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadError() {
        Toast.makeText(this, "Error cargando las gasolineras", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showStationDetails(Gasolinera station) {
        Intent intent = new Intent(this, DetailsView.class);
        intent.putExtra(DetailsView.INTENT_STATION, Parcels.wrap(station));
        startActivity(intent);
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }

}
