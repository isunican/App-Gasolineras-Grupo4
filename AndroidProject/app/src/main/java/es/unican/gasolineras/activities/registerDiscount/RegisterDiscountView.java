package es.unican.gasolineras.activities.registerDiscount;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Set;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.repository.AppDatabaseDiscount;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IDescuentoDAO;

public class RegisterDiscountView extends AppCompatActivity implements IRegisterDiscountContract.View{

    private IRegisterDiscountContract.Presenter presenter;

    private AppDatabaseDiscount db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //Initialize the interface
        super.onCreate(savedInstanceState);
        init();
        db = DataBase.getAppDatabaseDiscount(getApplicationContext());
        //Catch all the elements in the interface and make the buttons work
        //TODO cuando se tenga la interfaz ya creada
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(onClickListener -> {
            presenter.onCancelRegistryClicked();
        });

        //Funcionalidad de selección del porcentaje o precio fijo
        TextView tvType = findViewById(R.id.tvType);
        tvType.setText("");
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int percentage = R.id.rbPercentaje;
            int fixed = R.id.rbFixed;
            if (checkedId == percentage) {
                tvType.setText("%");
            } else if (checkedId == fixed) {
                tvType.setText("€/l");
            }

        });

        //Inicializar el calendario
        initCalendarExpirationDate();

        //Inicializar el spinner
        initSpinnerCompany();

        //Boton de registro
        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(onClickListener -> {
            String name = ((TextView) findViewById(R.id.etName)).getText().toString();
            String company = ((Spinner) findViewById(R.id.spnCompany)).getSelectedItem().toString();
            String discountType = tvType.getText().toString();
            String quantity = ((EditText) findViewById(R.id.etQuantity)).getText().toString();
            String expirationDate = ((EditText) findViewById(R.id.tvExpiranceDate)).getText().toString();
            String active = ((CheckBox) findViewById(R.id.chkActive)).isChecked() ? "true" : "false";
            presenter.onRegisterDiscountClicked(name,company,discountType,quantity,expirationDate,active);
        });

        //Settear la toolbar correctamente
        Toolbar toolbar = findViewById(R.id.tbRegisterDiscount);
        setSupportActionBar(toolbar);
    }

    private void initSpinnerCompany() {
        // Busca el spinner por la id
        Spinner spinner = findViewById(R.id.spnCompany);

        Set<String> nombresMarcas = Utils.obtenerRotulosUnicos(getResources().openRawResource(R.raw.gasolineras_ccaa_06));

        // Convertir el Set a una lista
        ArrayList<String> rotulosList = new ArrayList<>(nombresMarcas);
        rotulosList.sort(String::compareToIgnoreCase);

        // Crear un ArrayAdapter usando la lista y el diseño predeterminado para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item, // Diseño predeterminado para los elementos del Spinner
                rotulosList
        );

        // Configurar el diseño desplegable para el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador en el Spinner
        spinner.setAdapter(adapter);
    }

    private void initCalendarExpirationDate(){
        EditText etDate = findViewById(R.id.tvExpiranceDate);

        // Hacer que el EditText sea de solo lectura
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.setFocusable(false);

        // Configurar el OnClickListener para mostrar el DatePickerDialog
        etDate.setOnClickListener(v -> mostrarDatePickerDialog());
    }

    private void mostrarDatePickerDialog() {
        EditText fechaEditText = findViewById(R.id.tvExpiranceDate);

        // Obtener la fecha actual
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int día = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear y mostrar el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int añoSeleccionado, int mesSeleccionado, int díaSeleccionado) {
                        // El mes empieza desde 0, por eso sumamos 1
                        String fechaSeleccionada = díaSeleccionado + "/" + (mesSeleccionado + 1) + "/" + añoSeleccionado;
                        fechaEditText.setText(fechaSeleccionada);
                    }
                },
                año, mes, día);

        datePickerDialog.show();
    }

    @Override
    public void init() {
        //Setting the content view
        setContentView(R.layout.activity_register_discount);
        //Creation of the presenter
        presenter = new RegisterDiscountPresenter();
        presenter.init(this);
    }

    @Override
    public void showDiscountHistory() {
        Intent intent = new Intent(this, null/*TODO cuando este creado el historial de descuentos*/);
        startActivity(intent);
    }

    @Override
    public void showAlertDialog(String message, String title) {
        Utils.showAlertDialog(message, title, this);
    }

    @Override
    public IDescuentoDAO getDescuentoDAO(){return db.descuentosDAO();}

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showSuccesDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(R.string.succes_reg_discount)
                .setTitle(R.string.title_succes_reg_discount);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
                showDiscountHistory();
            }
        });

        // 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
