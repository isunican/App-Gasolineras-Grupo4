package es.unican.gasolineras.activities.discountList;

import java.util.List;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;

public interface IDiscountListContract {

    public interface Presenter{

        public void init(View view);
    }

    public interface View {

        public void init();

        public IDescuentoDAO getDescuentoDAO();

        public void showDescuentos(List<Descuento> descuentos);

        /**
         * The view is requested to display when an error in the database.
         * Only the Presenter should call this method
         */
        public void showErrorBD();
    }
}
