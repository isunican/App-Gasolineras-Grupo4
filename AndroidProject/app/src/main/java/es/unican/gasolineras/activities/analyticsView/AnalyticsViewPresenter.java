package es.unican.gasolineras.activities.analyticsView;

import android.database.sqlite.SQLiteException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryView;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * The presenter of the analytics view . It controls {@link AnalyticsViewView}
 */
public class AnalyticsViewPresenter implements IAnalyticsViewContract.Presenter {

    /** The view that is controlled by this presenter */
    private IAnalyticsViewContract.View view;

    // Variables para almacenar los resultados calculados
    private double precioCombustibleMedio;
    private double litrosPromedio;
    private double litrosTotales;
    private double gastoTotal;

    /**
     * @see IAnalyticsViewContract.Presenter#init(IAnalyticsViewContract.View)
     * @param view the view to control
     */
    @Override
    public void init(IAnalyticsViewContract.View view) {
        this.view = view;
        this.view.init();
        load();
    }



    /**
     * Loads the payments from the database and performs calculations
     */
    public void load() {
        IPagoDAO dao = view.getPagoDAO();
        try {
            // Recuperamos todos los pagos
            List<Pago> pagos = dao.getAll();
            // Llamamos al método para hacer los cálculos
            calculateAnalytics(pagos);
            // Mostramos los resultados en la vista
            //view.showAnalytics(precioCombustibleMedio, litrosPromedio, litrosTotales, gastoTotal);
        } catch (SQLiteException e) {
            view.showErrorBD();
        }
    }

    /**
     * Perform calculations on the list of payments
     * @param pagos List of Pago objects
     */
    private void calculateAnalytics(List<Pago> pagos) {
        if (pagos == null || pagos.isEmpty()) {
            // En caso de que no haya datos
            precioCombustibleMedio = 0;
            litrosPromedio = 0;
            litrosTotales = 0;
            gastoTotal = 0;
            return;
        }

        double totalPrecioCombustible = 0;
        double totalLitros = 0;
        double totalGasto = 0;

        for (Pago pago : pagos) {
            totalPrecioCombustible += pago.pricePerLitre;
            totalLitros += pago.quantity;
            totalGasto += pago.pricePerLitre * pago.quantity;
        }

        // Calculamos los promedios
        precioCombustibleMedio = totalPrecioCombustible / pagos.size();
        litrosPromedio = totalLitros / pagos.size();
        litrosTotales = totalLitros;
        gastoTotal = totalGasto;
    }

    public void loadForMonthYear(int month, int year) {
        IPagoDAO dao = view.getPagoDAO(); // Obtener el DAO para interactuar con la base de datos
        try {
            // Obtener todos los pagos de la base de datos
            List<Pago> pagos = dao.getAll();

            // Filtramos los pagos según el mes y el año
            List<Pago> filteredPagos = filterPagosByMonthYear(pagos, month, year);

            // Llamamos al método para calcular las estadísticas a partir de los pagos filtrados
            calculateAnalytics(filteredPagos);

            // Mostramos los resultados en la vista
            view.showAnalytics(precioCombustibleMedio, litrosPromedio, litrosTotales, gastoTotal);
        } catch (SQLiteException e) {
            // Si ocurre un error al acceder a la base de datos, mostramos un mensaje de error
            view.showErrorBD();
        }
    }

    private List<Pago> filterPagosByMonthYear(List<Pago> pagos, int month, int year) {
        List<Pago> filteredPagos = new ArrayList<>();

        for (Pago pago : pagos) {
            // Suponiendo que el campo `fecha` en el modelo `Pago` es un String en formato "yyyy-MM-dd"
            String fecha = pago.date;

            LocalDate fechaLocalDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Extrae el año y el mes
            int pagoYear = fechaLocalDate.getYear();
            int pagoMonth = fechaLocalDate.getMonthValue();
            // Extraemos el mes y el año de la fecha del pago
            //String[] fechaParts = fecha.split("-");
            //int pagoYear = Integer.parseInt(fechaParts[0]); // Año
            //int pagoMonth = Integer.parseInt(fechaParts[1]); // Mes

            // Comparamos el mes y año del pago con el mes y año seleccionados
            if (pagoMonth == month && pagoYear == year) {
                filteredPagos.add(pago);
            }
        }

        return filteredPagos;
    }

    public void onChartTypeSelected(String chartType) {
        view.clearContainer();
        switch (chartType) {
            case "Gasto diario":
                view.showLineChart();
                break;
            case "Precio combustible diario":
                view.showLineChartPriceLitre();
                break;
            case "Porcentaje tipo combustible":
                view.showPieChart();
                break;
            default:
                view.showLineChart();
        }
    }

}
