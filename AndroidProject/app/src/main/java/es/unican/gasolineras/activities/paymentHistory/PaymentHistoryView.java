package es.unican.gasolineras.activities.paymentHistory;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

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

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
    }

    /**
     * No tiene uso ya que no vamos a usar un on click listener
     */
    @Override
    public void init() {

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
