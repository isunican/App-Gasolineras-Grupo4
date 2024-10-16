package es.unican.gasolineras.activities.paymentHistory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import es.unican.gasolineras.R;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * View
 */
public class PaymentHistoryView extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
    }


    public IPagoDAO getPagoDAO() {
        return db.pagoDAO();
    }


}
