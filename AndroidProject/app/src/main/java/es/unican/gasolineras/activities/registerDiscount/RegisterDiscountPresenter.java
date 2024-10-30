package es.unican.gasolineras.activities.registerDiscount;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;

public class RegisterDiscountPresenter implements IRegisterDiscountContract.Presenter {

    private IRegisterDiscountContract.View view;

    public IDescuentoDAO db;

    @Override
    public void init(IRegisterDiscountContract.View view) {
        this.view = view;
        this.db = view.getDescuentoDAO();
    }

    @Override
    public void onRegisterDiscountClicked(String name, String company, String discountType, String quantity, String expirationDate, String active) {
        //Comprobamos que no hay campos vacios
        if (name.isEmpty()) {
            view.showAlertDialog("El nombre no puede estar vacío", "Error");
        } else if (company.isEmpty()) {
            view.showAlertDialog("La compañía no puede estar vacía", "Error");
        } else if (discountType.isEmpty()) {
            view.showAlertDialog("El tipo de descuento no puede estar vacío", "Error");
        } else if (quantity.isEmpty()) {
            view.showAlertDialog("La cantidad no puede estar vacía", "Error");
        } else if (expirationDate.isEmpty()) {
            view.showAlertDialog("La fecha de expiración no puede estar vacía", "Error");
        } else {
            //Comprobamos que la fecha de expiración no sea anterior a la fecha actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(expirationDate, formatter);
            if (date.isBefore(LocalDate.now())) {
                view.showAlertDialog("La fecha de expiración no puede ser anterior a la fecha actual", "Error");
            } else {
                //comprobar que el nombre no esta repetido
                if (db.findByName(name) == null) {
                    Descuento descuento = new Descuento();
                    descuento.discountName = name;
                    descuento.company = company;
                    descuento.discountType = discountType;
                    descuento.expiranceDate = expirationDate;
                    if (active.equals("true")) {
                        descuento.discountActive = true;
                    } else if (active.equals("false")) {
                        descuento.discountActive = false;
                    }

                    //Comprobamos los errores en descuento
                    double discount = 0;
                    if (discountType.equals("%")) {
                        //Comprobamos que la cantidad sea un número
                        try {
                            discount = Double.parseDouble(quantity);
                            descuento.quantityDiscount = discount;
                        } catch (NumberFormatException e) {
                            view.showAlertDialog("El porcentaje debe ser un número", "Error");
                        }
                        //Comprobamos que la cantidad esté entre 0 y 100
                        if (discount < 0 || discount > 100) {
                            view.showAlertDialog("El porcentaje debe estar entre 0 y 100", "Error");
                        } else {
                            view.showSuccesDialog();
                            //guardar los datos en la base de datos
                            db.insertAll(descuento);
                        }

                    } else if (discountType.equals("€/l")) {
                        //Comprobamos que la cantidad sea un número
                        try {
                            discount = Double.parseDouble(quantity);
                            descuento.quantityDiscount = discount;
                        } catch (NumberFormatException e) {
                            view.showAlertDialog("La cantidad fija debe ser un número", "Error");
                        }
                        //Comprobamos que es mayor que 0
                        if (discount <= 0) {
                            view.showAlertDialog("La cantidad fija debe ser mayor que 0", "Error");
                        } else {
                            view.showSuccesDialog();
                            //guardar los datos en la base de datos
                            db.insertAll(descuento);
                        }

                    }
                } else {
                    view.showAlertDialog("No se puede crear el descuento porque ya existe uno con el mismo nombre", "Error");
                }


            }
        }


    }

    @Override
    public void onCancelRegistryClicked() {
        view.showDiscountHistory();
    }
}
