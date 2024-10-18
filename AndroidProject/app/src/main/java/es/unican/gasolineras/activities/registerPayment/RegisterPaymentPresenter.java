package es.unican.gasolineras.activities.registerPayment;

import java.time.LocalDate;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabase;
import es.unican.gasolineras.repository.IPagoDAO;

public class RegisterPaymentPresenter implements IRegisterPaymentContract.Presenter{

    IRegisterPaymentContract.View view;
    @Override
    public void init(IRegisterPaymentContract.View view) {
        this.view = view;
        this.view.init();
    }

    @Override
    public void onRegisterPaymentClicked(String tipoGasolina, String nombreGasolinera, double precioPorLitro, double cantidad){
        //Creo el objeto de tipo Pago
        Pago pago = new Pago();
        pago.fuelType = tipoGasolina;
        pago.stationName = nombreGasolinera;
        pago.pricePerLitre = precioPorLitro;
        pago.quantity = cantidad;
        double finalPrice = precioPorLitro * cantidad;
        pago.finalPrice = finalPrice;
        pago.date = LocalDate.now().toString();

        //Persito el objeto en la base de datos
        IPagoDAO db = view.getPagoDAO();
        db.insertAll(pago);

        //Vuelvo al historial de pagos
        view.showRegisterHistory();
    }
}
