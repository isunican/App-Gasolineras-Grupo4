package es.unican.gasolineras.activities.paymentHistory;

import android.database.sqlite.SQLiteException;

import java.util.List;

import es.unican.gasolineras.activities.combustible.ICombustibleContract;
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
