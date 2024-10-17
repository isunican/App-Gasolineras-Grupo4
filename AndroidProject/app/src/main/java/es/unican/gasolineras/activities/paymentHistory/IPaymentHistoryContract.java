package es.unican.gasolineras.activities.paymentHistory;

import android.content.Intent;

import java.util.List;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

public interface IPaymentHistoryContract {
    public interface Presenter{
        public void init(View view);
    }

    public interface View{
        public void init();

        public IPagoDAO getPagoDAO();

        /**
         * Lista de pagos
         */
        public void showPagos(List<Pago> pagos);
    }
}

