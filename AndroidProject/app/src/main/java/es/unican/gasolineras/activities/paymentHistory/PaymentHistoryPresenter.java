package es.unican.gasolineras.activities.paymentHistory;

import java.time.LocalDate;
import java.util.List;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

public class PaymentHistoryPresenter implements IPaymentHistoryContract.Presenter{
    // Cherry
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
     * Loads the payments from the database, and sends them to the view
     */
    public void load(){
        IPagoDAO dao = view.getPagoDAO();
        List<Pago> pagos = dao.getAll();
        Pago p = new Pago();
        p.setQuantity(1.0);
        dao.insertAll(p);
        view.showPagos(pagos);
    }

}
