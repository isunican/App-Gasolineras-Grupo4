package es.unican.gasolineras.activities.registerDiscount;

import android.content.Context;

import es.unican.gasolineras.repository.IDescuentoDAO;
import es.unican.gasolineras.model.Descuento;

public interface IRegisterDiscountContract {

    public interface Presenter {

        /**
         * Inicializa la vista, cargando en sus variables la base de datos y la vista.
         * @param view Vista sobre la que trabaja el presenter
         */
        public void init(IRegisterDiscountContract.View view);

        /**
         * Registra un descuento en la base de datos. Para ello comprueba que los campos no estén vacíos,
         * o que verifiquen los requisitos que tienen para que el descuento sea valido.
         * @param descuento Descuento a registrar en la base de datos
         */
        public void onRegisterDiscountClicked(Descuento descuento);

        public void onCancelRegistryClicked();

        /**
         * Metodo encargado de comprobar que no hay campos vacios en el descuento, en caso de haberlos
         * creara una ventana de alerta para que el usuario lo vea.
         * @param d Descuento a revisar
         * @return true si hay campos vacios y false si no los hay
         */
        public boolean hayCamposVacios(Descuento d);
    }

    public interface View {


        public void init();

        public void showDiscountHistory();

        public void showAlertDialog(String message, String title);

        IDescuentoDAO getDescuentoDAO();

        public Context getContext();

        public void showSuccesDialog();
    }

}
