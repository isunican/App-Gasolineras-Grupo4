package es.unican.gasolineras.activities.paymentHistory;

import android.database.sqlite.SQLiteException;

import java.util.List;

import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * The Presenter-View contract for the PaymentHistory activity.
 * The PaymentHistory activity shows a list of the payments history.
 */
public interface IPaymentHistoryContract {

    /** Methods that must be implemented in the PaymentHistory Presenter.
     * Only the View should call these methods.
    **/
    public interface Presenter{

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view
         */
        public void init(View view);

        /**
         * Metodo destinado a desencadenar las acciones necesarias para eliminar un pago,
         * solicitando una previa confirmacion por parte del usuario.
         * @param pago Pago que se propone eliminar
         */
        public void onDeleteButtonClicked(Pago pago);

        /**
         * Metodo que elimina efectivamente un pago de la base de datos.
         * @param p Pago a eliminar de la base de datos
         * @throws SQLiteException en caso de fallo de acceso a la base de datos
         */
        public void onDeleteConfirmed(Pago p) throws SQLiteException;

    }

    /**
     * The presenter is informed that the Back Arrow item in the menu has been clicked
     * Only the View should call this method
     */
    public void onMenuBackArrowClick();

    /**
     * Methods that must be implemented in the PaymentHistory View.
     * Only the Presenter should call these methods.
     */
    public interface View{
        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        /**
         * Return the interface pagoDAO which can return the data from the database.
         * Only the Presenter should call this method
         * @return IPagoDAO interface of the data base
         */
        public IPagoDAO getPagoDAO();

        /**
         * The view is requested to display the history payments.
         * Only the Presenter should call this method
         */
        public void showPagos(List<Pago> pagos);

        /**
         * The view is requested to open the main activity.
         * Only the Presenter should call this method
         */
        public void showMainActivity();

        /**
         * The view is requested to display when an error in the database.
         * Only the Presenter should call this method
         */
        public void showErrorBD();

        /**
         * Metodo encargado de la creacion de un mensaje de alerta que verifica
         * si el usuario desea o no eliminar un pago, en caso de querer eliminarlo
         * lo hace saber al presenter para que lleve a cabo las acciones pertinentes.
         * @param pago Pago que se puede eliminar
         */
        public void showAlertDialogEliminarPago(Pago pago);

        /**
         * Metodo encargado de crear un mensaje de alerta con un titulo y mensaje indicados.
         * @param titulo Titulo del mensaje
         * @param mensaje Mensaje a mostrar
         */
        public void showAlertDialog(String titulo, String mensaje);
    }
}

