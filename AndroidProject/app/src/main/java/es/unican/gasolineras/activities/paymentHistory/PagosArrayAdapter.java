package es.unican.gasolineras.activities.paymentHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Descuento;
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

    private IPaymentHistoryContract.Presenter presenter;

    public PagosArrayAdapter(Context context, List<Pago> pagos, IPaymentHistoryContract.Presenter presenter){
        this.pagos = pagos;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Pago pago = (Pago) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_payment_history_list_item, parent, false);
        }
        ImageButton btnEliminar = convertView.findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(v -> {
            presenter.onDeleteButtonClicked(pago);
        });

        convertView = linkTextView(convertView, pago);

        return convertView;
    }

    private View linkTextView(View convertView, Pago pago){
        // Fecha
        setTextView(convertView,R.id.Fecha,"Fecha: " + pago.getDate());
        // Nombre
        setTextView(convertView,R.id.Estacion,pago.getStationName());
        // Tipo combustible
        setTextView(convertView,R.id.TipoCombustible,"Combustible: \n" + pago.getFuelType());
        // Cantidad
        setTextView(convertView,R.id.Cantidad,"Cantidad: \n" + String.valueOf(pago.getQuantity()));
        // Importe total
        setTextView(convertView,R.id.ImporteTotal,"Importe: \n" + String.valueOf(pago.getFinalPrice()));
        // Precio
        setTextView(convertView,R.id.Precio,"Precio: \n" + String.valueOf(pago.pricePerLitre));
        return convertView;
    }

    private void setTextView(View convertView, int textViewId, String text) {
        TextView tv = convertView.findViewById(textViewId);
        tv.setText(text);
    }
}
