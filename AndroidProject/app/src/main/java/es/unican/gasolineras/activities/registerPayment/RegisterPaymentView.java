package es.unican.gasolineras.activities.registerPayment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.paymentHistory.IPaymentHistoryContract;
import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryView;
import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.repository.AppDatabasePayments;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IPagoDAO;

public class RegisterPaymentView extends AppCompatActivity implements IRegisterPaymentContract.View{

    private RegisterPaymentPresenter presenter;

    private AppDatabasePayments db;

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

        EditText fechaEditText = findViewById(R.id.tvExpiranceDate2);
        fechaEditText.setOnClickListener(v -> mostrarDatePickerDialog());
        fechaEditText.setFocusable(false); // Desactiva la posibilidad de enfocar
        fechaEditText.setCursorVisible(false); // Elimina el cursor
        fechaEditText.setKeyListener(null);

        Button btnRegistrarPago = findViewById(R.id.btnRegistrarPago);
        btnRegistrarPago.setOnClickListener(onClickListener -> {
            String tipoGasolina = spinner.getSelectedItem().toString();
            String nombreGasolinera = ((TextView) findViewById(R.id.etNombreGasolinera)).getText().toString();
            String precioPorLitro = ((TextView) findViewById(R.id.editTextNumberDecimal)).getText().toString();
            String cantidad = ((TextView) findViewById(R.id.editTextNumberDecimal2)).getText().toString();
            LocalDate fecha = LocalDate.now();
            if (!fechaEditText.getText().toString().isEmpty()) {
                fecha = LocalDate.parse(((TextView) fechaEditText).getText().toString(), DateTimeFormatter.ofPattern("d/M/yyyy"));
            }
            presenter.onRegisterPaymentClicked(tipoGasolina, nombreGasolinera, precioPorLitro, cantidad, fecha);

        });

        Toolbar toolbar = findViewById(R.id.tbRegistroPagos);
        setSupportActionBar(toolbar);
    }

    /**
     * This creates the menu that is shown in the action bar (the upper toolbar)
     * @param menu The options menu in which you place your items.
     *
     * @return true because we are defining a new menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_combustible, menu);
        return true;
    }

    /**
     * This is called when an item in the action bar menu is selected.
     * @param item The menu item that was selected.
     *
     * @return true if we have handled the selection
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemBackArrow) {
            presenter.onMenuBackArrowClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init() {
        //Este metodo esta vacio porque no hay necesidad de inicializar nada
    }

    @Override
    public void showRegisterHistory(){
        Intent intent = new Intent(this, PaymentHistoryView.class);
        startActivity(intent);
    }


    @Override
    public void showAlertDialog(String message, String title) {
        Utils.showAlertDialog(message, title, this);
    }

    @Override
    public void showSuccesDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(R.string.succes_reg_pay)
                .setTitle(R.string.title_succes_reg_pay);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
                showRegisterHistory();
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

    private void mostrarDatePickerDialog() {
        EditText fechaEditText = findViewById(R.id.tvExpiranceDate2);

        // Obtener la fecha actual
        final Calendar calendario = Calendar.getInstance();
        int anho = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear y mostrar el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialogTheme,
                (view, anhoSeleccionado, mesSeleccionado, diaSeleccionado) -> {
                    // El mes empieza desde 0, por eso sumamos 1
                    String fechaSeleccionada = String.format("%02d", diaSeleccionado) + "/" + String.format("%02d", mesSeleccionado + 1) + "/" + anhoSeleccionado;
                    fechaEditText.setText(fechaSeleccionada);
                },
                anho, mes, dia);

        datePickerDialog.show();
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
