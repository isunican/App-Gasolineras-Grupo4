package es.unican.gasolineras.activities.registerPayment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainPresenter;
import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryView;

public class RegisterPaymentView extends AppCompatActivity implements IRegisterPaymentContract.View{

    private RegisterPaymentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new RegisterPaymentPresenter();
        presenter.init(this);

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
            double precioPorLitro = Double.parseDouble(((TextView) findViewById(R.id.editTextNumberDecimal)).getText().toString());
            double cantidad = Double.parseDouble(((TextView) findViewById(R.id.editTextNumberDecimal2)).getText().toString());

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
}
