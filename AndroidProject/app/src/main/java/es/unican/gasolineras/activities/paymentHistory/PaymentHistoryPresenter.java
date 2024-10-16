package es.unican.gasolineras.activities.paymentHistory;

import java.util.List;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

public class PaymentHistoryPresenter implements IPaymentHistoryContract.Presenter{
    /** The view that is controlled by this presenter */
    private IPaymentHistoryContract.View view;
    @Override
    public void init(IPaymentHistoryContract.View view) {
        this.view = view;
        this.view.init();
        load();
    }

    public void load(){
        IPagoDAO dao = view.getPagoDAO();
        List<Pago> pagos = dao.getAll();
        view.showPagos(pagos);
    }

}
