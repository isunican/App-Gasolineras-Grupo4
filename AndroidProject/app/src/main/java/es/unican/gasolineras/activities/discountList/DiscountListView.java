package es.unican.gasolineras.activities.discountList;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.AppDatabaseDiscount;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IDescuentoDAO;

public class DiscountListView extends AppCompatActivity implements IDiscountListContract.View {

    private DiscountListPresenter presenter;

    private AppDatabaseDiscount db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_list);
        db = DataBase.getAppDatabaseDiscount(getApplicationContext());

        presenter = new DiscountListPresenter();
        presenter.init(this);
    }

    @Override
    public void init() {
        //Create the toolbar
        Toolbar toolbar = findViewById(R.id.toolbarDiscounts);
        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.lvDiscounts);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Descuento descuento = (Descuento) parent.getItemAtPosition(position);
        });
    }

    @Override
    public IDescuentoDAO getDescuentoDAO() {
        return db.descuentosDAO();
    }

    @Override
    public void showDescuentos(List<Descuento> descuentos) {
        if (descuentos.isEmpty()) {
            Toast.makeText(this, "No hay descuentos registrados", Toast.LENGTH_SHORT).show();
        }
        ListView list = findViewById(R.id.lvDiscounts);
        DiscountArrayAdapter adapter = new DiscountArrayAdapter(this, descuentos);
        list.setAdapter(adapter);

    }

    @Override
    public void showErrorBD() {
        Utils.showAlertDialog("Error en el acceso a la base de datos","Error base de datos", this);
    }
}