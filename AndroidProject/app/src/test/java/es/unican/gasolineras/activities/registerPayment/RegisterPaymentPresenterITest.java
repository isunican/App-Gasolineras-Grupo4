package es.unican.gasolineras.activities.registerPayment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDate;
import java.util.List;

import es.unican.gasolineras.repository.DataBase;
import es.unican.gasolineras.repository.IPagoDAO;
import es.unican.gasolineras.model.Pago;


@RunWith(RobolectricTestRunner.class)
public class RegisterPaymentPresenterITest {

    private RegisterPaymentPresenter presenter;

    @Mock
    private IRegisterPaymentContract.View viewMock;

    private IPagoDAO pagoDAO;

    private Context contexto;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        contexto = ApplicationProvider.getApplicationContext();
        when(viewMock.getPagoDAO()).thenReturn(DataBase.getAppDatabase(contexto).pagoDAO());
        when(viewMock.getContext()).thenReturn(contexto);
        pagoDAO = viewMock.getPagoDAO();
        presenter = new RegisterPaymentPresenter();
        presenter.init(viewMock);

        //Considero como estado inicial de la base de datos, que está vacía
        List<Pago> pagos = pagoDAO.getAll();
        for (Pago pago : pagos) {
            pagoDAO.delete(pago);
        }
        presenter.init(viewMock);
    }

    @Test
    public void testOnRegisterPaymentClicked() {
        //Caso de exito
        Pago pago = new Pago();
        pago.date = LocalDate.now().toString();
        pago.fuelType = "GLP";
        pago.stationName = "Gasolinera arrandel";
        pago.pricePerLitre = 1.54;
        pago.quantity = (double) 30;
        pago.finalPrice = pago.pricePerLitre * pago.quantity;
        presenter.onRegisterPaymentClicked
                ("GLP", "Gasolinera arrandel", "1.54", "30");
        List<Pago> pagos = pagoDAO.getAll();
        assert(pagos.contains(pago));

        //Caso de error tipo gasolina
        pagoDAO.delete(pagos.get(0));
        presenter.onRegisterPaymentClicked
                ("", "Gasolinera arrandel", "1.54", "30");
        pagos = pagoDAO.getAll();
        assertEquals(pagos.size(), 0);
        verify(viewMock, times(1)).showAlertDialog
                ("Debes seleccionar un tipo de combustible", "Error en el tipo de combustible");

        //Caso de error nombre gasolinera
        presenter.onRegisterPaymentClicked
                ("GLP", "", "1.54", "30");
        pagos = pagoDAO.getAll();
        assertEquals(pagos.size(), 0);
        verify(viewMock, times(1)).showAlertDialog
                ("Debes introducir un nombre de gasolinera", "Error en el nombre de gasolinera");

        //Caso de error precio por litro
        presenter.onRegisterPaymentClicked
                ("GLP", "Gasolinera arrandel", "", "30");
        pagos = pagoDAO.getAll();
        assertEquals(pagos.size(), 0);
        verify(viewMock, times(1)).showAlertDialog
                ("Debes introducir un precio", "Error en el precio por litro");

        //Caso de error cantidad repostada
        presenter.onRegisterPaymentClicked
                ("GLP", "Gasolinera arrandel", "1.54", "");
        pagos = pagoDAO.getAll();
        assertEquals(pagos.size(), 0);
        verify(viewMock, times(1)).showAlertDialog
                ("Debes introducir una cantidad de combustible", "Error en la cantidad");


    }
}
