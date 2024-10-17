package es.unican.gasolineras.activities.paymentHistory;

import android.os.Bundle;
import android.widget.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import es.unican.gasolineras.activities.info.InfoView;
import es.unican.gasolineras.activities.registerPayment.RegisterPaymentView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;
import java.util.Objects;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.GasolinerasArrayAdapter;
import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * View
 */
public class PaymentHistoryView extends AppCompatActivity implements IPaymentHistoryContract.View{
    //Cherry
    private AppDatabase db;

    private PaymentHistoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();

        // instantiate presenter and launch initial business logic
        presenter = new PaymentHistoryPresenter();
        presenter.init(this);

    }

    /**
     * No tiene uso ya que no vamos a usar un on click listener
     */
    @Override
    public void init() {
        Toolbar toolbar = findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Historial de pagos");

        ListView list = findViewById(R.id.lvPagos);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Pago pago = (Pago) parent.getItemAtPosition(position);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuItemAddPago) {
            Intent intent = new Intent(this, RegisterPaymentView.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menuItemInfo) {
            Intent intent = new Intent(this, InfoView.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * @see IPaymentHistoryContract.View#getPagoDAO()
     * @return IPagoDAO de la base de datos
     */
    @Override
    public IPagoDAO getPagoDAO() {
        return db.pagoDAO();
    }

    /**
     * @see IPaymentHistoryContract.View#showPagos(List)
     * @param pagos la lista de registro de pagos
     */
    @Override
    public void showPagos(List<Pago> pagos) {
        ListView list = findViewById(R.id.lvPagos);
        PagosArrayAdapter adapter = new PagosArrayAdapter(this, pagos);
        list.setAdapter(adapter);
    }


}
