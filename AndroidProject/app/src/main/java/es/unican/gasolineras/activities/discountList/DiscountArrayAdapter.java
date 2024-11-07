package es.unican.gasolineras.activities.discountList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.AppDatabaseDiscount;
import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IDescuentoDAO;

/**
 * Adapter that renders the discounts in each row of a ListView
 */
public class DiscountArrayAdapter extends BaseAdapter {

    // The list of discounts to render
    private final List<Descuento> descuentos;

    /** Context of the application */
    private final Context context;

    // The discount DAO that we use to get get or change data of the database
    private IDescuentoDAO descuentoDAO;


    @Override
    public int getCount() { return descuentos.size();}

    @Override
    public Object getItem(int position){ return descuentos.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    /**
     * Constructs an adapter to handle a list of discounts.
     * @param context The application context
     * @param descuentos The list of discounts
     */
    public DiscountArrayAdapter(Context context, List<Descuento> descuentos){
        this.descuentos = descuentos;
        this.context = context;
        AppDatabaseDiscount db = DataBase.getAppDatabaseDiscount(context);
        descuentoDAO = db.descuentosDAO();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Descuento descuento = (Descuento) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_discount_list_item, parent, false);
        }

        convertView = linkTextView(convertView, descuento);
        CheckBox chk = convertView.findViewById(R.id.chkActive);
        chk.setChecked(descuento.discountActive);

        // Listener for if it is clicked it will update the value in the database
        chk.setOnClickListener(onClickListener ->
                descuentoDAO.update(chk.isChecked(), descuento.discountName)
        );


        return convertView;
    }

    /**
     * Method that establish the text that it will be shown in each row of the list, filling the data
     * of the database how it should be shown in each field.
     * @param convertView The view to modify
     * @param descuento The discount to show
     * @return The view that it was modified.
     */
    private View linkTextView(View convertView, Descuento descuento){
        // Nombre de descuento
        setTextView(convertView,R.id.discountName,"Nombre:\n" + descuento.discountName);

        // Tipo de descuento
        setTextView(convertView,R.id.discountType,"Tipo descuento\n" + descuento.discountType);

        // Compañia
        setTextView(convertView,R.id.discountBrand,"Compañia:\n" + descuento.company);

        // Valor de descuento
        setTextView(convertView,R.id.discountValue,"Valor descuento:\n" + descuento.quantityDiscount);
        return convertView;
    }

    /**
     * Method that put the text in each field of the row
     * @param convertView View to modify
     * @param textViewId Id of the text to fill
     * @param text Text to put in the ID
     */
    private void setTextView(View convertView, int textViewId, String text) {
        TextView tv = convertView.findViewById(textViewId);
        tv.setText(text);
    }


}
