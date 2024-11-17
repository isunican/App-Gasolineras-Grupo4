package es.unican.gasolineras.activities.paymentHistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.analyticsView.AnalyticsViewView;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.activities.registerPayment.RegisterPaymentView;
import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabasePayments;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * View of the history of payments. It shows a list of the history of payments register in the app
 */
public class PaymentHistoryView extends AppCompatActivity implements IPaymentHistoryContract.View{

    /** Database of the payments **/
    private AppDatabasePayments db;

    /** The presenter of this view */
    private PaymentHistoryPresenter presenter;

    private PagosArrayAdapter arrayAdapter;

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
        //Create the toolbar
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
        } else if (itemId == R.id.menuItemBackArrow) {
            presenter.onMenuBackArrowClick();
            return true;
        } else if(itemId == R.id.menuItemEstadistica){
            Intent intent = new Intent(this, AnalyticsViewView.class);
            startActivity(intent);
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
     * @param pagos la lista de pagos
     */
    @Override
    public void showPagos(List<Pago> pagos) {
        if (pagos.isEmpty()) {
            Toast.makeText(this, "Todavia no hay pagos registrados.\nRegistra tu primer pago", Toast.LENGTH_SHORT).show();
        }
        ListView list = findViewById(R.id.lvPagos);
        arrayAdapter = new PagosArrayAdapter(this, pagos, presenter);
        list.setAdapter(arrayAdapter);
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }

    @Override
    public void showErrorBD(){
        Utils.showAlertDialog("Error en el acceso a la base de datos","Error base de datos", this);
    }
    

    public void showAlertDialogEliminarPago(Pago pago) {

        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage("¿Desea usted eliminar el pago en " + pago.stationName + " el día " + pago.date + "?")
                .setTitle("Eliminar pago");

        builder.setNegativeButton("Si", (dialog, id) -> {
            // User taps OK button.
            presenter.onDeleteConfirmed(pago);
        });

        builder.setPositiveButton("No", (dialog, id) ->{
            //Nothing
        });

        // 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void showAlertDialog(String titulo, String mensaje) {
        Utils.showAlertDialog(mensaje, titulo, this);
    }

}
