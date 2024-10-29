package es.unican.gasolineras.activities.registerDiscount;

import android.content.Context;

public interface IRegisterDiscountContract {

    public interface Presenter {

        public void init(IRegisterDiscountContract.View view);

        public void onRegisterDiscountClicked(String name, String company, String discountType, String quantity, String expirationDate, String active);

        public void onCancelRegistryClicked();
    }

    public interface View {

        public void init();

        public void showDiscountHistory();

        public void showAlertDialog(String message, String title);

        public Context getContext();

        public void showSuccesDialog();
    }

}
