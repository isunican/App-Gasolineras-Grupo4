package es.unican.gasolineras.activities.registerDiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.repository.AppDatabase;

public class RegisterDiscountView extends AppCompatActivity implements IRegisterDiscountContract.View{

    private IRegisterDiscountContract.Presenter presenter;

    private AppDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //Initialize the interface
        super.onCreate(savedInstanceState);
        init();

        //Catch all the elements in the interface and make the buttons work
        //TODO cuando se tenga la interfaz ya creada
    }

    @Override
    public void init() {
        //Setting the content view
        setContentView(R.layout.activity_register_discount); //TODO cuando este la interfaz creada
        //Creation of the presenter
        presenter = new RegisterDiscountPresenter();
        presenter.init(this);
    }

    @Override
    public void showDiscountHistory() {

    }

    @Override
    public void showAlertDialog(String message, String title) {
        Utils.showAlertDialog(message, title, this);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showSuccesDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(R.string.succes_reg_discount)
                .setTitle(R.string.title_succes_reg_discount);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
                showDiscountHistory();
            }
        });

        // 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
