package es.unican.gasolineras.activities.discountList;

import android.database.sqlite.SQLiteException;

import java.util.List;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;

/**
 * The presenter of the discount list activity. it controls {@link DiscountListView}
 */
public class DiscountListPresenter implements IDiscountListContract.Presenter{

    // The view that is controlled by this presenter.
    private IDiscountListContract.View view;

    /**
     * @see IDiscountListContract.Presenter#init(IDiscountListContract.View)
     * @param view View to control
     */
    @Override
    public void init(IDiscountListContract.View view) {
        this.view = view;
        this.view.init();
        load();
    }

    /**
     * This method gets the interface of discount dao. If appears an error with the database it is
     * controlled with a try-catch block.
     * With this interface it gets the data store in the databse that the method showDescuentos use
     * for show the list of discounts.
     */
    public void load(){
        IDescuentoDAO dao = view.getDescuentoDAO();
        try{
            List<Descuento> descuentos = dao.getAll();
            view.showDescuentos(descuentos);
        } catch (SQLiteException e){
            view.showErrorBD();
        }
    }
}
