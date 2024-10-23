package es.unican.gasolineras.activities.registerPayment;

import java.time.LocalDate;

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
    public void onMenuBackArrowClick() {view.showPaymentHistoryActivity();}

    @Override
    public void onRegisterPaymentClicked(String tipoGasolina, String nombreGasolinera, String precioPorLitro, String cantidad){

        if (tipoGasolina.isEmpty()) {
            view.showAlertDialog("Debes seleccionar un tipo de combustible", "Error en el tipo de combustible");

        } else if (nombreGasolinera.isEmpty()) {
            view.showAlertDialog("Debes introducir un nombre de gasolinera", "Error en el nombre de gasolinera");

        } else if (precioPorLitro.isEmpty()) {
            view.showAlertDialog("Debes introducir un precio", "Error en el precio por litro");

        } else if (cantidad.isEmpty()) {
            view.showAlertDialog("Debes introducir una cantidad de combustible", "Error en la cantidad");

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
