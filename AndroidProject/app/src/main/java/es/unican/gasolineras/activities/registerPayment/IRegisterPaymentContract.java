package es.unican.gasolineras.activities.registerPayment;

import android.content.Context;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.repository.IPagoDAO;

public interface IRegisterPaymentContract {

    public interface Presenter {

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         *
         * @param view
         */
        public void init(IRegisterPaymentContract.View view);

        /**
         * Register the payment in the database.
         * @param tipoGasolina A string that represents the type of fuel.
         * @param nombreGasolinera A string that represents the name of the gas station.
         * @param precioPorLitro A double that represents the price per litre of the fuel.
         * @param cantidad A double that represents the amount of fuel.
         */
        public void onRegisterPaymentClicked(String tipoGasolina, String nombreGasolinera, String precioPorLitro, String cantidad);
    }

    public interface View {

        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        /**
         * Show the paymnent history view.
         */
        public void showRegisterHistory();

        public IPagoDAO getPagoDAO();

        public void showAlertDialog(String message, String title);

        public void showSuccesDialog();

        public Context getContext();
    }
}
