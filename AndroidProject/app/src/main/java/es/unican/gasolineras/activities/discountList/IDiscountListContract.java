package es.unican.gasolineras.activities.discountList;

import java.util.List;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;

/**
 * The Presenter-View contract for the Discount List Activity
 * The Discount List shows a list of the discount registered
 */
public interface IDiscountListContract {

    /**
     * Methods that must be implemented in the DiscountListPresenter.
     * Only the View should call these methods.
     */
    public interface Presenter{

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view View to be shown
         */
        public void init(View view);
    }

    /**
     * Methods that must be implemented in the DiscountListView.
     * Only the presenter should call these methods.
     */
    public interface View {

        /**
         * Initialize the view. Initialize the tool bar and the list view to show the list of
         * discounts.
         * Only the Presenter should call this method.
         */
        public void init();

        /**
         * This method using the database returns the unique instance of DescuentoDAO because is a
         * singleton.
         * Only the Presenter should call this method.
         * @return IDescuentoDAO an interface that have the methods to obtain data of database.
         */
        public IDescuentoDAO getDescuentoDAO();

        /**
         * This method show the list of discount in case it is empty it will show a text that it is empty,
         * however it is not empty it will show the list of discounts.
         * Only the Presenter should call this method.
         * @param descuentos List of discounts to be shown on the View.
         */
        public void showDescuentos(List<Descuento> descuentos);

        /**
         * The view is requested to display when an error in the database.
         * Only the Presenter should call this method.
         */
        public void showErrorBD();
    }
}
