package es.unican.gasolineras.activities.registerDiscount;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;

public class RegisterDiscountPresenter implements IRegisterDiscountContract.Presenter {

    private IRegisterDiscountContract.View view;

    private IDescuentoDAO db;

    @Override
    public void init(IRegisterDiscountContract.View view) {
        this.view = view;
        this.db = view.getDescuentoDAO();
    }

    @Override
    public void onRegisterDiscountClicked(Descuento d) {
        String errorTitle = "Error";
        //Comprobamos que no hay campos vacios
        if (!hayCamposVacios(d)) {
            //Comprobamos que la fecha de expiración no sea anterior a la fecha actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(d.expiranceDate, formatter);
            if (date.isBefore(LocalDate.now())) {
                view.showAlertDialog("La fecha de expiración no puede ser anterior a la fecha actual", errorTitle);
            } else {
                //comprobar que el nombre no esta repetido
                if (db.findByName(d.discountName) == null) {

                    //Comprobamos los errores en descuento
                    if (d.discountType.equals("%")) {

                        //Comprobamos que la cantidad esté entre 0 y 100
                        if (d.quantityDiscount <= 0 || d.quantityDiscount > 100) {
                            view.showAlertDialog("El porcentaje debe estar entre 0 y 100", errorTitle);
                        } else {
                            view.showSuccesDialog();
                            //guardar los datos en la base de datos
                            db.insertAll(d);
                        }

                    } else if (d.discountType.equals("€/l")) {

                        //Comprobamos que es mayor que 0
                        if (d.quantityDiscount <= 0) {
                            view.showAlertDialog("La cantidad fija debe ser mayor que 0", errorTitle);
                        } else {
                            view.showSuccesDialog();
                            //guardar los datos en la base de datos
                            db.insertAll(d);
                        }

                    }
                } else {
                    view.showAlertDialog("No se puede crear el descuento porque ya existe uno con el mismo nombre", errorTitle);
                }


            }
        }


    }


    /**
     * Metodo encargado de comprobar que no hay campos vacios en el descuento, en caso de haberlos
     * creara una ventana de alerta para que el usuario lo vea.
     * @param d Descuento a revisar
     * @return true si hay campos vacios y false si no los hay
     */
    public boolean hayCamposVacios(Descuento d){
        String errorTitle = "Error";
        boolean error = false;
        if (d.discountName.isEmpty()) {
            view.showAlertDialog("El nombre no puede estar vacío", errorTitle);
            error = true;
        } else if (d.company.isEmpty()) {
            view.showAlertDialog("La compañía no puede estar vacía", errorTitle);
            error = true;
        } else if (d.discountType.isEmpty()) {
            view.showAlertDialog("El tipo de descuento no puede estar vacío", errorTitle);
            error = true;
        } else if (d.quantityDiscount == null) {
            view.showAlertDialog("La cantidad no puede estar vacía", errorTitle);
            error = true;
        } else if (d.expiranceDate.isEmpty()) {
            view.showAlertDialog("La fecha de expiración no puede estar vacía", errorTitle);
            error = true;
        }

        return error;
    }

    @Override
    public void onCancelRegistryClicked() {
        view.showDiscountHistory();
    }
}
