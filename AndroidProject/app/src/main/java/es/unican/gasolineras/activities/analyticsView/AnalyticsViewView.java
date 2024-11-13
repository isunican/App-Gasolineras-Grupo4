package es.unican.gasolineras.activities.analyticsView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        init();

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
        // Inicializamos el botón para calcular los resultados
        ImageButton imgBtnTick = findViewById(R.id.imgBtnTick);
        imgBtnTick.setOnClickListener(v -> {
            // Obtenemos el valor seleccionado en el Spinner de mes y año
            int month = spnMonth.getSelectedItemPosition() + 1; // Los spinner empiezan desde 0, pero los meses empiezan desde 1
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
        //Setting the content view
        setContentView(R.layout.activity_analytics_view);
        //Creation of the presenter
        presenter = new AnalyticsViewPresenter();
        presenter.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuItemAddPago) {
            Intent intent = new Intent(this, RegisterPaymentView.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void showAnalytics(double precioCombustibleMedio, double litrosPromedio, double litrosTotales, double gastoTotal) {
        // Muestra los resultados en la vista
        tvPrecioCombustibleMedio.setText("Precio Combustible Medio: " + precioCombustibleMedio);
        tvLitrosPromedio.setText("Litros Promedio: " + litrosPromedio);
        tvLitrosTotales.setText("Litros Totales: " + litrosTotales);
        tvGastoTotal.setText("Gasto Total: " + gastoTotal);
    }

    @Override
    public void showNoDataFound() {

    }
}
