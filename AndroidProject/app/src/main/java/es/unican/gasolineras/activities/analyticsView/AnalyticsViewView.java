package es.unican.gasolineras.activities.analyticsView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.activities.registerDiscount.RegisterDiscountPresenter;
import es.unican.gasolineras.activities.registerPayment.RegisterPaymentView;
import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabasePayments;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IPagoDAO;

public class AnalyticsViewView extends AppCompatActivity implements IAnalyticsViewContract.View {

    private AppDatabasePayments db;
    private AnalyticsViewPresenter presenter;

    // Define los Spinners para el mes y el año
    private Spinner spnMonth;
    private Spinner spnYear;

    // Define los TextViews para mostrar los resultados
    private TextView tvPrecioCombustibleMedio;
    private TextView tvLitrosPromedio;
    private TextView tvLitrosTotales;
    private TextView tvGastoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_view);
        db = DataBase.getAppDatabase(getApplicationContext());
        //Creation of the presenter
        presenter = new AnalyticsViewPresenter();
        presenter.init(this);

        // Inicializamos los Spinners
        spnMonth = findViewById(R.id.spnMonth);
        spnYear = findViewById(R.id.spnYear);

        // Inicializamos los TextViews para mostrar los resultados
        tvPrecioCombustibleMedio = findViewById(R.id.tvPrecioCombustibleMedio);
        tvLitrosPromedio = findViewById(R.id.tvLitrosPromedio);
        tvLitrosTotales = findViewById(R.id.tvLitrosTotales);
        tvGastoTotal = findViewById(R.id.tvGastoTotal);

        //Settear la toolbar correctamente
        Toolbar toolbar = findViewById(R.id.tbAnalytics);
        setSupportActionBar(toolbar);

        // Crea el adaptador con las opciones del spinner establecidas en strings.xml
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,
                R.array.opcionesSpinnerMes, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(adaptador);

        // Crea el adaptador con las opciones del spinner establecidas en strings.xml
        ArrayAdapter<CharSequence> adaptador2 = ArrayAdapter.createFromResource(this,
                R.array.opcionesSpinnerAnho, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(adaptador2);

        // Inicializamos el botón para calcular los resultados
        ImageButton imgBtnTick = findViewById(R.id.imgBtnTick);
        setUpSpinners();
        imgBtnTick.setOnClickListener(v -> {

            // Obtenemos el valor seleccionado en el Spinner de mes y año
            // Obtener el valor seleccionado del Spinner como String
            String selectedMonthString = spnMonth.getSelectedItem().toString();
            int month = Integer.parseInt(selectedMonthString);

            // Convertir el valor de int a String nuevamente
            String monthString = String.format("%02d", (month + 1));  // Asegura que el mes tenga 2 dígitos (por ejemplo, "03")


            int year = Integer.parseInt(spnYear.getSelectedItem().toString());

            // Verificamos que se haya seleccionado un mes y un año válidos
            if (month == 0 || year == 0) {
                Toast.makeText(AnalyticsViewView.this, "Por favor seleccione mes y año válidos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamamos al método para cargar los pagos y calcular las estadísticas
            presenter.loadForMonthYear(month, year);
        });
    }

    @Override
    public void init() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_analytics, menu);
        return true;
    }


    @Override
    public IPagoDAO getPagoDAO() {
        return db.pagoDAO();
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }

    @Override
    public void showErrorBD() {
        Utils.showAlertDialog("Error en el acceso a la base de datos", "Error base de datos", this);
    }

    @Override
    public void showAnalytics(Double precioCombustibleMedio, Double litrosPromedio, Double litrosTotales, Double gastoTotal) {
        // Muestra los resultados en la vista
        tvPrecioCombustibleMedio.setText("Precio Combustible Medio: " + precioCombustibleMedio);
        tvLitrosPromedio.setText("Litros Promedio: " + litrosPromedio);
        tvLitrosTotales.setText("Litros Totales: " + litrosTotales);
        tvGastoTotal.setText("Gasto Total: " + gastoTotal);
    }

    @Override
    public void showNoDataFound() {

    }


    private void setUpSpinners() {
        LocalDate currentDate = LocalDate.now();

        int anhoActual = currentDate.getYear();


        Month mesActual = currentDate.getMonth();

        int mesInt = mesActual.getValue();

        spnMonth.setSelection(mesInt - 1);


        spnYear.setSelection(anhoActual);
    }
}
