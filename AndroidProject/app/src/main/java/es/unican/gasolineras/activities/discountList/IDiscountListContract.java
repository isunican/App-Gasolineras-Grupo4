package es.unican.gasolineras.activities.discountList;

import es.unican.gasolineras.activities.paymentHistory.IPaymentHistoryContract;
import es.unican.gasolineras.repository.IDescuentoDAO_Impl;

public interface IDiscountListContract {

    public interface Presenter{

        public void init(View view);
    }

    public interface View {

        public void init();

        public IDescuentoDAO getDescuentoDAO();

        public void showDescuentos(List<Descuento> descuentos);
    }
}
