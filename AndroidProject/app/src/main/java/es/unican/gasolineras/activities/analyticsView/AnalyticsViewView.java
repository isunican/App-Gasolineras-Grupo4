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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

        // Establecer el color del gráfico (línea y círculos)
        lineDataSet.setColor(Color.parseColor("#3F4FB3")); // Color para la línea
        lineDataSet.setCircleColor(Color.parseColor("#3F4FB3")); // Color para los círculos en cada punto

        // Usar un ValueFormatter para formatear los valores de los puntos con dos decimales
        lineDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f", value); // Formato para mostrar con dos decimales
            }
        });

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

        // Usar un ValueFormatter para formatear los valores del eje Y con dos decimales
        leftYAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f €", value); // Formato para mostrar con dos decimales
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
    public void showLineChartPriceLitre() {
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

        // Agrupar pagos por día y calcular la media
        TreeMap<Integer, ArrayList<Float>> paymentsByDay = new TreeMap<>(); // Usar TreeMap para mantener el orden

        // Añadir pagos agrupados por día
        for (Pago pago : pagos) {
            String dayStr = pago.getDate().substring(8, 10);
            int day = Integer.parseInt(dayStr);
            paymentsByDay.putIfAbsent(day, new ArrayList<>());
            paymentsByDay.get(day).add(pago.getPricePerLitre().floatValue());
        }

        // Añadir el primer y último día del mes si no están incluidos
        if (!paymentsByDay.containsKey(1)) paymentsByDay.put(1, new ArrayList<>());
        if (!paymentsByDay.containsKey(lastDayOfMonth)) paymentsByDay.put(lastDayOfMonth, new ArrayList<>());

        // Crear las entradas para el gráfico
        float xIndex = 0;
        for (Map.Entry<Integer, ArrayList<Float>> entry : paymentsByDay.entrySet()) {
            int day = entry.getKey();
            ArrayList<Float> dailyPayments = entry.getValue();

            // Calcular la media de los pagos del día
            float average = 0;
            if (!dailyPayments.isEmpty()) {
                float sum = 0;
                for (float payment : dailyPayments) {
                    sum += payment;
                }
                average = sum / dailyPayments.size();
            }

            lineEntries.add(new Entry(xIndex, average));
            xLabels.add(String.valueOf(day));
            xIndex++;
        }

        // Configurar el conjunto de datos
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Promedio de Pagos del Mes");

        // Cambiar el color de la línea y los círculos
        lineDataSet.setColor(Color.parseColor("#3F4FB3")); // Color para la línea
        lineDataSet.setCircleColor(Color.parseColor("#3F4FB3")); // Color para los círculos de los puntos

        // Otros ajustes
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(4f);

        // Usar un ValueFormatter para formatear los valores de los puntos con tres decimales
        lineDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.3f", value); // Formato para mostrar con tres decimales
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        // Configurar el eje X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
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
                return String.format("%.3f €/L", value); // Formato para mostrar con dos decimales
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

    public void showPieChart() {
        clearContainer();
        pieChart = new PieChart(this);
        chartFrame.addView(pieChart);

        // Obtener pagos filtrados por mes y año
        String monthStr = String.format("%02d", month);
        String yearStr = String.valueOf(year);
        List<Pago> pagos = pagosDAO.getPagosByMonthAndYear(yearStr, monthStr);

        if (pagos.isEmpty()) {
            pieChart.setNoDataText("No hay datos disponibles para mostrar.");
            return;
        }

        Map<String, Float> pagosPorCombustible = new HashMap<>();

        for (Pago pago : pagos) {
            String tipoCombustible = pago.getFuelType(); // Ajusta según cómo se obtenga el tipo
            pagosPorCombustible.put(tipoCombustible, pagosPorCombustible.getOrDefault(tipoCombustible, 0f) + 1);
        }

        for (Map.Entry<String, Float> entry : pagosPorCombustible.entrySet()) {
            float porcentaje = (entry.getValue() / pagos.size()) * 100;
            pagosPorCombustible.put(entry.getKey(), porcentaje);
        }

        // Crear las entradas para el gráfico de pastel
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : pagosPorCombustible.entrySet()) {
            if (entry.getValue() > 0) {
                pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
            }
        }

        // Colores personalizados (20 colores diferentes)
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.DKGRAY);
        colors.add(Color.LTGRAY);
        colors.add(Color.BLACK);
        colors.add(Color.GRAY);
        colors.add(Color.parseColor("#FF5722"));
        colors.add(Color.parseColor("#4CAF50"));
        colors.add(Color.parseColor("#2196F3"));
        colors.add(Color.parseColor("#FFC107"));
        colors.add(Color.parseColor("#9C27B0"));
        colors.add(Color.parseColor("#00BCD4"));
        colors.add(Color.parseColor("#673AB7"));
        colors.add(Color.parseColor("#FF9800"));
        colors.add(Color.parseColor("#8BC34A"));
        colors.add(Color.parseColor("#E91E63"));

        // Configurar colores personalizados en el gráfico
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colors); // Usar los colores personalizados
        pieDataSet.setValueTextSize(12f); // Tamaño ajustado para las etiquetas de porcentaje
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f%%", value); // Mostrar solo porcentaje
            }
        });

        // Configurar los datos del gráfico
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Configuraciones de las etiquetas de los sectores
        pieChart.setDrawEntryLabels(false); // Deshabilitar las etiquetas de los nombres

        // Configuraciones adicionales del gráfico
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(30f); // Tamaño del agujero central más pequeño (hacer el círculo más grande)
        pieChart.setTransparentCircleRadius(35f); // Tamaño del círculo transparente (ajustar más grande si es necesario)
        pieChart.setExtraOffsets(10f, 10f, 10f, 40f); // Márgenes adicionales (espacio optimizado)

        // Configuración de la leyenda
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true); // Activar la leyenda
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL); // Configurar orientación horizontal
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); // Colocar debajo del gráfico
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // Centrado horizontal
        legend.setDrawInside(false); // Colocar fuera del gráfico
        legend.setWordWrapEnabled(true); // Habilitar salto de línea si es necesario
        legend.setMaxSizePercent(0.9f); // Limitar el tamaño máximo al 90% del ancho del gráfico
        legend.setTextSize(12f); // Tamaño del texto

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
        tvPrecioCombustibleMedio.setText("Precio Combustible Medio: " + String.format("%.3f", precioCombustibleMedio));
        tvLitrosPromedio.setText("Litros Promedio: " + String.format("%.2f", litrosPromedio));
        tvLitrosTotales.setText("Litros Totales: " + String.format("%.2f", litrosTotales));
        tvGastoTotal.setText("Gasto Total: " + String.format("%.2f", gastoTotal));

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
