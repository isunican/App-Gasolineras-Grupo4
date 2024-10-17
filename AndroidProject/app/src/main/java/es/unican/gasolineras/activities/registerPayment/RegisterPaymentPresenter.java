package es.unican.gasolineras.activities.registerPayment;

import es.unican.gasolineras.activities.main.IMainContract;

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


        //Persito el objeto en la base de datos


        //Vuelvo al historial de pagos
        view.showRegisterHistory();
    }
}
