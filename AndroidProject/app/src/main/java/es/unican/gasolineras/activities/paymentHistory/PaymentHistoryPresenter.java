package es.unican.gasolineras.activities.paymentHistory;

import android.database.sqlite.SQLiteException;

import java.util.List;

import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

/**
 * The presenter of the payment history. It controls {@link PaymentHistoryView}
 */
public class PaymentHistoryPresenter implements IPaymentHistoryContract.Presenter{

    /** The view that is controlled by this presenter */
    private IPaymentHistoryContract.View view;

    /**
     * @see IPaymentHistoryContract.Presenter#init(IPaymentHistoryContract.View)
     * @param view the view to control
     */
    @Override
    public void init(IPaymentHistoryContract.View view) {
        this.view = view;
        this.view.init();
        load();
    }

    @Override
    public void onDeleteButtonClicked(Pago pago) {
        view.showAlertDialogEliminarPago(pago);
    }

    @Override
    public void onDeleteConfirmed(Pago p) throws SQLiteException{
        IPagoDAO dao = view.getPagoDAO();
        try {
            dao.delete(p);
            load();
            view.showAlertDialog("Éxito eliminación", "El pago ha sido eliminado de manera exitosa de la base de datos");
        } catch (SQLiteException e) {
            view.showErrorBD();
        } catch (NullPointerException e) {
            //Caso de null pointer exception, no se hace nada pero
            // no se recarga la vista ni se muestra mensaje de exito
        }
    }

    /**
     * @see IPaymentHistoryContract.Presenter#onMenuBackArrowClick()
     */
    public void onMenuBackArrowClick() {view.showMainActivity();}

    /**
     * Loads the payments from the database, and sends them to the view
     */
    public void load(){
        IPagoDAO dao = view.getPagoDAO();
        try{
            List<Pago> pagos = dao.getAll();
            view.showPagos(pagos);
        } catch (SQLiteException e){
            view.showErrorBD();
        }


    }

}
