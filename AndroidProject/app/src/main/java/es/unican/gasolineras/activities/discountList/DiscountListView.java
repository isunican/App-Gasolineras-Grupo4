package es.unican.gasolineras.activities.discountList;

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

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.activities.registerDiscount.RegisterDiscountView;
import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.AppDatabaseDiscount;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IDescuentoDAO;

/**
 * The view of discounts. It shows a list of discounts.
 */
public class DiscountListView extends AppCompatActivity implements IDiscountListContract.View {

    // The presenter of the view
    private DiscountListPresenter presenter;

    // The database of discounts
    private AppDatabaseDiscount db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_list);
        db = DataBase.getAppDatabaseDiscount(getApplicationContext());

        presenter = new DiscountListPresenter();
        presenter.init(this);
    }

    /**
     * @see IDiscountListContract.View#init()
     */
    @Override
    public void init() {
        //Create the toolbar
        Toolbar toolbar = findViewById(R.id.toolbarDiscounts);
        setSupportActionBar(toolbar);
    }

    /**
     * This creates the menu that it is shown in the action bar(the upper toolbar)
     * @param menu The options menu in which you place your items.
     *
     * @return true because we are defining a new menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_discounts, menu);
        return true;
    }

    /**
     * This is called when an item in the action bar menu is selected.
     * @param item The menu item that was selected.
     *
     * @return true if we have handled the selection.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuItemAddPago) {
            Intent intent = new Intent(this, RegisterDiscountView.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menuItemBackArrow) {
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @see IDiscountListContract.View#getDescuentoDAO()
     * @return the database to access the data
     */
    @Override
    public IDescuentoDAO getDescuentoDAO() {
        return db.descuentosDAO();
    }

    /**
     * @see IDiscountListContract.View#showDescuentos(List)
     * @param descuentos List of discounts to be shown on the View.
     */
    @Override
    public void showDescuentos(List<Descuento> descuentos) {
        if (descuentos.isEmpty()) {
            Toast.makeText(this, "No hay descuentos registrados", Toast.LENGTH_SHORT).show();
        }
        ListView list = findViewById(R.id.lvDiscounts);
        DiscountArrayAdapter adapter = new DiscountArrayAdapter(this, descuentos);
        list.setAdapter(adapter);

    }

    /**
     * @see IDiscountListContract.View#showErrorBD()
     */
    @Override
    public void showErrorBD() {
        Utils.showAlertDialog("Error en el acceso a la base de datos","Error base de datos", this);
    }
}