package es.unican.gasolineras.activities.discountList;

import android.database.sqlite.SQLiteException;

import java.util.List;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;

public class DiscountListPresenter implements IDiscountListContract.Presenter{
    private IDiscountListContract.View view;

    @Override
    public void init(IDiscountListContract.View view) {
        this.view = view;
        this.view.init();
        load();
    }

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
