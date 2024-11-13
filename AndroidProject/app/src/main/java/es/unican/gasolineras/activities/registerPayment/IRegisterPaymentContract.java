package es.unican.gasolineras.activities.registerPayment;

import android.content.Context;

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

        /**
         * The presenter is informed that the Back Arrow item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuBackArrowClick();
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

        /**
         * Metodo que retorna el objeto de tipo IPagoDAO que es necesario para interactuar con la base de datos.
         * @return una instancia de IPagoDAO
         */
        public IPagoDAO getPagoDAO();

        /**
         * Metodo el cual es encargado de crear AlertDialogs con el titulo y mensaje indiciado.
         * @param message Mensaje del alertDialog
         * @param title Titulo del alertDialog
         */
        public void showAlertDialog(String message, String title);

        /**
         * Metodo encargado de mostrar el alertDialog de exito.
         */
        public void showSuccesDialog();

        /**
         * Metodo que retorna el contexto de la vista, para que el presenter pueda acceder a sus recursos.
         * @return el contexto de la vista
         */
        public Context getContext();
    }
}
