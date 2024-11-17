package es.unican.gasolineras.activities.analyticsView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.MainView;
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
    private String chartType;

    // Define los TextViews para mostrar los resultados
    private TextView tvPrecioCombustibleMedio;
    private TextView tvLitrosPromedio;
    private TextView tvLitrosTotales;
    private TextView tvGastoTotal;

    private FrameLayout chartFrame;
    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;
    private Spinner chartTypeSpinner;
    private IPagoDAO pagosDAO;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_view);
        db = DataBase.getAppDatabase(getApplicationContext());
        pagosDAO = getPagoDAO();
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

        // Inicializamos spinner y frame para graficas
        chartFrame = findViewById(R.id.frmGraphic);
        chartTypeSpinner = findViewById(R.id.spnTypeGraphic);
        chartTypeSpinner.setEnabled(false);
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
            // Permite seleccionar un grafico después de que se haya clickado el botón, teniendo asi
            // mes y anho sobre el que hacer el grafico, además de actualizarlo.
            chartTypeSpinner.setEnabled(true);

            // Obtenemos el valor seleccionado en el Spinner de mes y año
            // Obtener el valor seleccionado del Spinner como String
            String selectedMonthString = spnMonth.getSelectedItem().toString();
            month = Integer.parseInt(selectedMonthString);

            // Convertir el valor de int a String nuevamente
            String monthString = String.format("%02d", (month + 1));  // Asegura que el mes tenga 2 dígitos (por ejemplo, "03")

            year = Integer.parseInt(spnYear.getSelectedItem().toString());

            // Verificamos que se haya seleccionado un mes y un año válidos
            if (month == 0 || year == 0) {
                Toast.makeText(AnalyticsViewView.this, "Por favor seleccione mes y año válidos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamamos al método para cargar los pagos y calcular las estadísticas
            presenter.loadForMonthYear(month, year);

            updateChart();
        });

        // GRAFICAS
        // Configura el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opcionesSpinnerGrafico, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartTypeSpinner.setAdapter(adapter);
        chartTypeSpinner.setSelection(0);
        chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chartType = parent.getItemAtPosition(position).toString();
                updateChart();  // Llama al Presenter
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona nada
            }
        });


    }

    private void updateChart() {
        presenter.onChartTypeSelected(chartType);
    }

    @Override
    public void clearContainer() {
        // Limpia el contenedor antes de agregar un nuevo gráfico
        chartFrame.removeAllViews();
    }

    // Métodos de ChartView para mostrar cada tipo de gráfico
    @Override
    public void showLineChart() {
        lineChart = new LineChart(this);
        chartFrame.addView(lineChart);

        // Obtener pagos filtrados por mes y año
        String monthStr = String.format("%02d", month);
        String yearStr = String.valueOf(year);
        List<Pago> pagos = pagosDAO.getPagosByMonthAndYear(yearStr, monthStr);

        // Obtener el último día del mes
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(yearStr));
        calendar.set(Calendar.MONTH, Integer.parseInt(monthStr) - 1);
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Crear las entradas del gráfico con los días con pagos
        ArrayList<Entry> lineEntries = new ArrayList<>();
        ArrayList<String> xLabels = new ArrayList<>();

        // Agrupar pagos por día
        TreeMap<Integer, Float> paymentsByDay = new TreeMap<>(); // Usar TreeMap para mantener el orden

        // Añadir el primer día del mes
        paymentsByDay.put(1, 0f);

        // Añadir los días con pagos
        for (Pago pago : pagos) {
            String dayStr = pago.getDate().substring(8, 10);
            int day = Integer.parseInt(dayStr);
            paymentsByDay.put(day, paymentsByDay.getOrDefault(day, 0f) + pago.getFinalPrice().floatValue());
        }

        // Añadir el último día del mes si no está ya incluido
        paymentsByDay.put(lastDayOfMonth, paymentsByDay.getOrDefault(lastDayOfMonth, 0f));

        // Crear las entradas para el gráfico
        float xIndex = 0;
        for (Map.Entry<Integer, Float> entry : paymentsByDay.entrySet()) {
            int day = entry.getKey();
            float totalPago = entry.getValue();
            lineEntries.add(new Entry(xIndex, totalPago));
            xLabels.add(String.valueOf(day));
            xIndex++;
        }

        // Configurar el conjunto de datos
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Pagos del mes");
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setCircleRadius(6f);
        lineDataSet.setLineWidth(2f);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        // Configurar el eje X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(0);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setLabelCount(xLabels.size());

        // Asegurar que se muestren todas las etiquetas
        xAxis.setAvoidFirstLastClipping(true);
        lineChart.setVisibleXRangeMaximum(xLabels.size());

        // Configurar márgenes para que las etiquetas sean visibles
        lineChart.setExtraOffsets(10f, 10f, 10f, 20f);

        // Configurar el eje Y izquierdo
        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setDrawGridLines(true);
        leftYAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("$%.2f", value);
            }
        });

        // Desactivar el eje Y derecho
        lineChart.getAxisRight().setEnabled(false);

        // Configuraciones adicionales del gráfico
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);

        // Actualizar el gráfico
        lineChart.invalidate();
    }

    @Override
    public void showBarChart() {
        barChart = new BarChart(this);
        chartFrame.addView(barChart);

        // Obtener pagos filtrados por mes y año
        String monthStr = String.format("%02d", month);
        String yearStr = String.valueOf(year);
        List<Pago> pagos = pagosDAO.getPagosByMonthAndYear(yearStr, monthStr);

        // Obtener el último día del mes
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(yearStr));
        calendar.set(Calendar.MONTH, Integer.parseInt(monthStr) - 1);
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Crear las entradas del gráfico con los días con pagos
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> xLabels = new ArrayList<>();

        // Agrupar pagos por día
        TreeMap<Integer, Float> paymentsByDay = new TreeMap<>(); // Usar TreeMap para mantener el orden

        // Añadir el primer día del mes
        paymentsByDay.put(1, 0f);

        // Añadir los días con pagos
        for (Pago pago : pagos) {
            String dayStr = pago.getDate().substring(8, 10);
            int day = Integer.parseInt(dayStr);
            paymentsByDay.put(day, paymentsByDay.getOrDefault(day, 0f) + pago.getFinalPrice().floatValue());
        }

        // Añadir el último día del mes si no está ya incluido
        paymentsByDay.put(lastDayOfMonth, paymentsByDay.getOrDefault(lastDayOfMonth, 0f));

        // Crear las entradas para el gráfico
        float xIndex = 0;
        for (Map.Entry<Integer, Float> entry : paymentsByDay.entrySet()) {
            int day = entry.getKey();
            float totalPago = entry.getValue();
            barEntries.add(new BarEntry(xIndex, totalPago));
            xLabels.add(String.valueOf(day));
            xIndex++;
        }

        // Configurar el conjunto de datos
        BarDataSet barDataSet = new BarDataSet(barEntries, "Pagos del mes");
        barDataSet.setColors(Color.BLUE);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Configurar el eje X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setLabelCount(xLabels.size());

        // Asegurar que se muestren todas las etiquetas
        xAxis.setAvoidFirstLastClipping(true);
        barChart.setVisibleXRangeMaximum(xLabels.size());

        // Configurar márgenes para que las etiquetas sean visibles
        barChart.setExtraOffsets(10f, 10f, 10f, 20f);

        // Configurar el eje Y izquierdo
        YAxis leftYAxis = barChart.getAxisLeft();
        leftYAxis.setDrawGridLines(true);
        leftYAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("$%.2f", value);
            }
        });

        // Desactivar el eje Y derecho
        barChart.getAxisRight().setEnabled(false);

        // Configuraciones adicionales del gráfico
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(true);

        // Actualizar el gráfico
        barChart.invalidate();
    }


    @Override
    public void showPieChart() {
        clearContainer();
        pieChart = new PieChart(this);
        chartFrame.addView(pieChart);

        // Obtener pagos filtrados por mes y año
        String monthStr = String.format("%02d", month);
        String yearStr = String.valueOf(year);
        List<Pago> pagos = pagosDAO.getPagosByMonthAndYear(yearStr, monthStr);

        Map<String, Float> pagosPorCombustible = new HashMap<>();

        for (Pago pago : pagos) {
            String tipoCombustible = pago.fuelType; // Método que retorna el tipo de combustible
            pagosPorCombustible.put(tipoCombustible, pagosPorCombustible.getOrDefault(tipoCombustible, 0f) + 1);
        }

        for (Map.Entry<String, Float> entry : pagosPorCombustible.entrySet()) {
            float porcentaje = (entry.getValue() / pagos.size()) * 100;
            pagosPorCombustible.put(entry.getKey(), porcentaje);
        }

        // Crear las entradas para el gráfico de pastel
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : pagosPorCombustible.entrySet()) {
            if(entry.getValue() > 0){
                pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
            }
        }

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);    // Primer color
        colors.add(Color.BLUE);   // Segundo color
        colors.add(Color.GREEN);  // Tercer color
        colors.add(Color.YELLOW); // Cuarto color
        colors.add(Color.MAGENTA); // Quinto color
        // Agregar más colores si es necesario

        // Configurar el conjunto de datos
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colors); // Asignar colores personalizados
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Mostrar solo el porcentaje con un formato adecuado
                return String.format("%.1f%%", value);
            }
        });

        // Configurar los datos del gráfico
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Configuraciones adicionales del gráfico
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(40f); // Tamaño del agujero central
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setCenterText("Pagos por combustible");
        pieChart.setCenterTextSize(16f);

        // Actualizar el gráfico
        pieChart.invalidate();
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


        spnYear.setSelection(anhoActual -2010);
    }
}
