package es.unican.gasolineras.activities.discountList;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryPresenter;
import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryView;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabase;

public class DiscountListView extends AppCompatActivity implements IDiscountListContract.View {

    private PaymentHistoryPresenter presenter;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_list);
        db = ;

        presenter = new PaymentHistoryPresenter();
        presenter.init(this);
    }

    @Override
    public void init() {
        //Create the toolbar
        Toolbar toolbar = findViewById(R.id.toolbarDiscounts);
        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.lvDiscouts);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Descuento descuento = (Descuento) parent.getItemAtPosition(position);
        });
    }

    @Override
    public IDescuentoDAO getDescuentoDAO() {
        return null;
    }

    @Override
    public void showDescuentos(List<Descuento> descuentos) {

    }
}