package es.unican.gasolineras.activities.registerPayment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.paymentHistory.IPaymentHistoryContract;
import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryView;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IPagoDAO;

public class RegisterPaymentView extends AppCompatActivity implements IRegisterPaymentContract.View{

    private RegisterPaymentPresenter presenter;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new RegisterPaymentPresenter();
        presenter.init(this);

        db = DataBase.getAppDatabase(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_payment);

        // Busca el spinner por la id
        Spinner spinner = findViewById(R.id.spnTipoCombustible);

        // Crea el adaptador con las opciones del spinner establecidas en strings.xml
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,
                            R.array.opcionesSpinner, android.R.layout.simple_spinner_item);

        // Establece el layout que tendra cuando se desplieguen las opciones del spinner
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        spinner.setAdapter(adaptador);

        Button btnRegistrarPago = findViewById(R.id.btnRegistrarPago);
        btnRegistrarPago.setOnClickListener(onClickListener -> {
            String tipoGasolina = spinner.getSelectedItem().toString();
            String nombreGasolinera = ((TextView) findViewById(R.id.etNombreGasolinera)).getText().toString();
            String precioPorLitro = ((TextView) findViewById(R.id.editTextNumberDecimal)).getText().toString();
            String cantidad = ((TextView) findViewById(R.id.editTextNumberDecimal2)).getText().toString();

            presenter.onRegisterPaymentClicked(tipoGasolina, nombreGasolinera, precioPorLitro, cantidad);

        });
    }

    @Override
    public void init() {

    }

    @Override
    public void showRegisterHistory(){
        Intent intent = new Intent(this, PaymentHistoryView.class);
        startActivity(intent);
    }


    @Override
    public void showErrorDialog(String message, String title) {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
            }
        });

        // 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * @see IPaymentHistoryContract.View#getPagoDAO()
     * @return IPagoDAO de la base de datos
     */
    @Override
    public IPagoDAO getPagoDAO() {
        return db.pagoDAO();
    }
}
