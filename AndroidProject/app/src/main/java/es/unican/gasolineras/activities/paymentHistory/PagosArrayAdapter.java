package es.unican.gasolineras.activities.paymentHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Pago;

/** The list of gas stations to render */


public class PagosArrayAdapter extends BaseAdapter {

    private final List<Pago> pagos;

    /** Context of the application */
    private final Context context;

    @Override
    public int getCount() { return pagos.size();}

    @Override
    public Object getItem(int position){ return pagos.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    public PagosArrayAdapter(Context context, List<Pago> pagos){
        this.pagos = pagos;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Pago pago = (Pago) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_payment_history_list_item, parent, false);
        }
        // Fecha
        {
            TextView tv = convertView.findViewById(R.id.Fecha);
            tv.setText("Fecha: " + pago.getDate());
        }
        // Nombre
        {
            TextView tv = convertView.findViewById(R.id.Estacion);
            tv.setText(pago.getStationName());
        }
        // Tipo combustible
        {
            TextView tv = convertView.findViewById(R.id.TipoCombustible);
            tv.setText("Combustible: " + pago.getFuelType());
        }
        // Cantidad
        {
            TextView tv = convertView.findViewById(R.id.Cantidad);
            tv.setText("Cantidad: " + String.valueOf(pago.getQuantity()));
        }
        // Importe total
        {
            TextView tv = convertView.findViewById(R.id.ImporteTotal);
            tv.setText("Importe: " + String.valueOf(pago.getFinalPrice()));
        }
        // Precio
        {
            TextView tv = convertView.findViewById(R.id.Precio);
            tv.setText("Precio: " + String.valueOf(pago.pricePerLitre));
        }
        return convertView;
    }
}
