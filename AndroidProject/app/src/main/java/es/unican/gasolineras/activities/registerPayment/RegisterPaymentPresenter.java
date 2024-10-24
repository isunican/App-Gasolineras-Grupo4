package es.unican.gasolineras.activities.registerPayment;

import java.time.LocalDate;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

public class RegisterPaymentPresenter implements IRegisterPaymentContract.Presenter{

    IRegisterPaymentContract.View view;
    @Override
    public void init(IRegisterPaymentContract.View view) {
        this.view = view;
        this.view.init();
    }

    @Override
    public void onRegisterPaymentClicked(String tipoGasolina, String nombreGasolinera, String precioPorLitro, String cantidad){

        if (tipoGasolina.isEmpty()) {
            view.showAlertDialog(
                    view.getContext().getString(R.string.error_tipo_combustible),
                    view.getContext().getString(R.string.titulo_error_tipo_combustible)
            );
        } else if (nombreGasolinera.isEmpty()) {
            view.showAlertDialog(
                    view.getContext().getString(R.string.error_nombre_gasolinera),
                    view.getContext().getString(R.string.titulo_error_nombre_gasolinera)
            );
        } else if (precioPorLitro.isEmpty()) {
            view.showAlertDialog(
                    view.getContext().getString(R.string.error_precio_por_litro),
                    view.getContext().getString(R.string.titulo_error_precio_por_litro)
            );
        } else if (cantidad.isEmpty()) {
            view.showAlertDialog(
                    view.getContext().getString(R.string.error_cantidad_combustible),
                    view.getContext().getString(R.string.titulo_error_cantidad_combustible)
            );
        } else {

            //Creo el objeto de tipo Pago
            Pago pago = new Pago();
            pago.fuelType = tipoGasolina;
            pago.stationName = nombreGasolinera;
            pago.pricePerLitre = Double.parseDouble(precioPorLitro);
            pago.quantity = Double.parseDouble(cantidad);
            pago.finalPrice = pago.pricePerLitre * pago.quantity;
            pago.date = LocalDate.now().toString();

            //Persito el objeto en la base de datos
            IPagoDAO db = view.getPagoDAO();
            db.insertAll(pago);

            //Vuelvo al historial de pagos
            view.showSuccesDialog();
        }
    }
}
