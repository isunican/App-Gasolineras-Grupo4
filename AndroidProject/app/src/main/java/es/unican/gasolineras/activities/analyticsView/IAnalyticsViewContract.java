package es.unican.gasolineras.activities.analyticsView;

import java.util.List;

import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * The Presenter-View contract for the Analytics View Activity.
 * The Analytics View shows data regarding expenses on a specific month and year.
 */
public interface IAnalyticsViewContract {

    /**
     * The Presenter interface defines methods that the View will call.
     * Only the View should call these methods, not the Presenter itself.
     */
    public interface Presenter {

        /**
         * Initializes the presenter by linking it with the view.
         * The View should call this method when initializing the presenter.
         *
         * @param view the view that will be controlled by this presenter
         */
        public void init(View view);

        /**
         * Maneja la selección del tipo de grafico a mostrar en la vista.
         * Dependiendo del tipo de grafico seleccionado, se limpia el contenedor de la vista
         * y se muestra el grafico correspondiente.
         * @param chartType El tipo de grafico seleccionado.
         *
         */
        public void onChartTypeSelected(String chartType);

    }

    /**
     * The View interface defines methods that the Presenter will call.
     * Only the Presenter should call these methods, not the View itself.
     */
    public interface View {

        /**
         * Initializes the view. Typically, this should initialize all listeners and UI elements.
         * Only the Presenter should call this method.
         */
        public void init();

        /**
         * Returns the interface to interact with the PagoDAO (data access object).
         * This allows the Presenter to access data from the database.
         *
         * @return IPagoDAO the interface to the database for retrieving payment data
         */
        public IPagoDAO getPagoDAO();


        /**
         * Informs the view to show the main activity.
         * Only the Presenter should call this method.
         */
        public void showMainActivity();

        /**
         * Displays an error message related to a database operation failure.
         * Only the Presenter should call this method.
         */
        public void showErrorBD( );

        /**
         * Displays the analytics results (fuel price, average liters, total liters, and total cost).
         * This method is called by the Presenter after the analytics are calculated.
         *
         * @param precioCombustibleMedio the average fuel price
         * @param litrosPromedio the average liters purchased
         * @param litrosTotales the total liters purchased
         * @param gastoTotal the total amount spent on fuel
         */
        public void showAnalytics(Double precioCombustibleMedio, Double litrosPromedio, Double litrosTotales, Double gastoTotal);

        /**
         * Limpia el contenedor de la vista, eliminando cualquier grafico o contenido previo.
         * Este metodo es llamado antes de mostrar un nuevo grafico o contenido.
         *
         */
        void clearContainer();

        /**
         * Muestra un gráfico de líneas con los datos correspondientes.
         * Este metodo es llamado cuando se selecciona un grafico de tipo "Gasto diario"
         * con una serie temporal de datos.
         */
        public void showLineChart();

        /**
         * Muestra un gráfico de líneas con los datos de precio por litro de combustible.
         * Este metodo es llamado cuando se selecciona un grafico de tipo "Precio combustible diario".
         */
        public void showLineChartPriceLitre();


        /**
         * Muestra un gráfico circular (pastel) con los datos correspondientes.
         * Este metodo es llamado cuando se selecciona un grafico de tipo "Porcentaje tipo combustible".
         */
        public void showPieChart();
    }
}
