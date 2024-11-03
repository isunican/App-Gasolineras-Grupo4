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

public class DiscountArrayAdapter extends BaseAdapter {

    private final List<Descuento> descuentos;

    /** Context of the application */
    private final Context context;

    private IDescuentoDAO descuentoDAO;


    @Override
    public int getCount() { return descuentos.size();}

    @Override
    public Object getItem(int position){ return descuentos.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    public DiscountArrayAdapter(Context context, List<Descuento> descuentos){
        this.descuentos = descuentos;
        this.context = context;
        AppDatabaseDiscount db = DataBase.getAppDatabaseDiscount(context);
        descuentoDAO = db.descuentosDAO();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Descuento descuento = (Descuento) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_discount_list_item, parent, false);
        }
        // Nombre de descuento
        {
            TextView tv = convertView.findViewById(R.id.discountName);
            tv.setText("Nombre:\n" + descuento.discountName);
        }
        // Tipo de descuento
        {
            TextView tv = convertView.findViewById(R.id.discountType);
            tv.setText("Tipo descuento\n" + descuento.discountType);
        }
        // Compañia
        {
            TextView tv = convertView.findViewById(R.id.discountBrand);
            tv.setText("Compañia:\n" + descuento.company);
        }
        // Valor de descuento
        {
            TextView tv = convertView.findViewById(R.id.discountValue);
            tv.setText("Valor descuento:\n" + String.valueOf(descuento.quantityDiscount));
        }
        // Activo
        {
            CheckBox chk = convertView.findViewById(R.id.chkActive);
            chk.setChecked(descuento.discountActive);

            // Listener for if it is clicked it will update the value in the database
            chk.setOnClickListener(OnClickListener -> {
                descuentoDAO.update(chk.isChecked(), descuento.discountName);
            });
        }
        
        return convertView;
    }
}
