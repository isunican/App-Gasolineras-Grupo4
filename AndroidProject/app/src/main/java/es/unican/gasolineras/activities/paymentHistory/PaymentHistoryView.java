package es.unican.gasolineras.activities.paymentHistory;

import android.os.Bundle;
import android.provider.ContactsContract;
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
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * View of the history of payments. It shows a list of the history of payments register in the app
 */
public class PaymentHistoryView extends AppCompatActivity implements IPaymentHistoryContract.View{

    /** Database of the payments **/
    private AppDatabase db;

    /** The presenter of this view */
    private PaymentHistoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        db = DataBase.getAppDatabase(getApplicationContext());

        // instantiate presenter and launch initial business logic
        presenter = new PaymentHistoryPresenter();
        presenter.init(this);
    }


    /**
     * @see IPaymentHistoryContract.View#init()
     */
    @Override
    public void init() {
        // Initialize the list view of payments
        ListView list = findViewById(R.id.lvPagos);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Pago pago = (Pago) parent.getItemAtPosition(position);
        });
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
     * @param pagos la lista de pagos
     */
    @Override
    public void showPagos(List<Pago> pagos) {
        ListView list = findViewById(R.id.lvPagos);
        PagosArrayAdapter adapter = new PagosArrayAdapter(this, pagos);
        list.setAdapter(adapter);
    }


}
