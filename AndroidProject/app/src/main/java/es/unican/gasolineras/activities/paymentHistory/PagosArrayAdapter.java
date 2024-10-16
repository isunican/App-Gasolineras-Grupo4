package es.unican.gasolineras.activities.paymentHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Pago;

/** The list of gas stations to render */


public class PagosArrayAdapter extends BaseAdapter {
    private final List<Pago> pagos;

    /** Context of the application */
    private final Context context;

    @Override
    public Object getItem(int position){ return pagos.get(position);}

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
            tv.setText(toString(pago.getDate().toString());
        }
    }
}
