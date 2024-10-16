package es.unican.gasolineras.activities.registerPayment;

import es.unican.gasolineras.activities.main.IMainContract;

public interface IRegisterPaymentContract {

    public interface Presenter {

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         *
         * @param view
         */
        public void init(IRegisterPaymentContract.View view);

    }

    public interface View {

        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        public void showRegisterHistory();

    }
}
