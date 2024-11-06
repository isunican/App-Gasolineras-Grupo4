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

        /**
         * Metodo destinado a la llamada al View para que lance la actividad de vista de descuentos.
         */
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


        /**
         * Metodo encargado de inicializar aquello que necesita la clase View para funcionar
         */
        public void init();

        /**
         * Metodo que lanza la vista de descuentos
         */
        public void showDiscountHistory();

        /**
         * Metodo el cual es encargado de crear AlertDialogs con el titulo y mensaje indiciado.
         * @param message Mensaje del alertDialog
         * @param title Titulo del alertDialog
         */
        public void showAlertDialog(String message, String title);

        /**
         * Metodo que retorna el objeto de tipo IDescuentoDAO que es necesario para interactuar con la base de datos.
         * @return una instancia de IDescuentoDAO
         */
        public IDescuentoDAO getDescuentoDAO();

        /**
         * Metodo que retorna el contexto de la vista, para que el presenter pueda acceder a sus recursos.
         * @return el contexto de la vista
         */
        public Context getContext();

        /**
         * Metodo encargado de mostrar el alertDialog de exito.
         */
        public void showSuccesDialog();
    }

}
