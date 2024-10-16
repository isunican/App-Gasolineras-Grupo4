package es.unican.gasolineras.activities.registerPayment;

import es.unican.gasolineras.activities.main.IMainContract;

public class RegisterPaymentPresenter implements IRegisterPaymentContract.Presenter{

    IRegisterPaymentContract.View view;
    @Override
    public void init(IRegisterPaymentContract.View view) {
        this.view = view;
        this.view.init();
    }

    public void onRegisterPaymentClicked(String tipoGasolina, String nombreGasolinera, double precioPorLitro, double cantidad){


        view.showRegisterHistory();
    }
}
