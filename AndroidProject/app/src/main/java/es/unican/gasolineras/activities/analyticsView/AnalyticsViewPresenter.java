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
        load();
    }

    /**
     * Carga la lista de pagos de la base del repositorio
     */
    public void load() {
        IPagoDAO dao = view.getPagoDAO();
        try {
            List<Pago> pagos = dao.getAll();

        } catch (SQLiteException e) {
            view.showErrorBD();
        }
    }

    /**
     * Realiza los calculos y operaciones necesarias para realizar mostrar en la analitica de datos
     * @param pagos lista de pagos con la que se realizan los calculos
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

    @Override
    public void onClickTickButtom(int month, int year) {
        IPagoDAO dao = view.getPagoDAO();
        try {

            List<Pago> pagos = dao.getAll();

            List<Pago> filteredPagos = filterPagosByMonthYear(pagos, month, year);

            calculateAnalytics(filteredPagos);

            view.showAnalytics(precioCombustibleMedio, litrosPromedio, litrosTotales, gastoTotal);
        } catch (SQLiteException e) {

            view.showErrorBD();
        }
    }

    /**
     * metodo que extrae los pagos de un mes y anho dado
     * @param pagos lista de pagos a filtrar
     * @param month mes del anho que se desea analizar
     * @param year anho a analizar
     * @return lista de pagos del anho y mes indicados
     */
    private List<Pago> filterPagosByMonthYear(List<Pago> pagos, int month, int year) {
        List<Pago> filteredPagos = new ArrayList<>();

        for (Pago pago : pagos) {
            // Suponiendo que el campo `fecha` en el modelo `Pago` es un String en formato "yyyy-MM-dd"
            String fecha = pago.date;

            LocalDate fechaLocalDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            int pagoYear = fechaLocalDate.getYear();
            int pagoMonth = fechaLocalDate.getMonthValue();

            if (pagoMonth == month && pagoYear == year) {
                filteredPagos.add(pago);
            }
        }

        return filteredPagos;
    }

    /**
     * Maneja la selección del tipo de gráfico a mostrar en la vista.
     * Dependiendo del tipo de grafico seleccionado, se limpia el contenedor de la vista
     * y se muestra el grafico correspondiente.
     * @param chartType El tipo de grafico seleccionado.
     *
     */
    public void onChartTypeSelected(String chartType,String month, String year) {
        view.clearContainer();
        List<Pago> pagos = view.getPagoDAO().getPagosByMonthAndYear(year,month);
        switch (chartType) {
            case "Gasto diario":
                view.showLineChart(pagos);
                break;
            case "Precio combustible diario":
                view.showLineChartPriceLitre(pagos);
                break;
            case "Porcentaje tipo combustible":
                view.showPieChart(pagos);
                break;
            default:
        }
    }

}
