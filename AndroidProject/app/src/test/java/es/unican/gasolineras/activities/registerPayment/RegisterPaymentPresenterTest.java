package es.unican.gasolineras.activities.registerPayment;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
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

import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabasePayments;
import es.unican.gasolineras.repository.IPagoDAO;

@RunWith(RobolectricTestRunner.class)
public class RegisterPaymentPresenterTest {

    @Mock
    private RegisterPaymentView viewMock;

    private RegisterPaymentPresenter sut;

    @Mock
    private IPagoDAO pagoDAOmock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sut = new RegisterPaymentPresenter();

        Context context = ApplicationProvider.getApplicationContext(); 
        when(viewMock.getContext()).thenReturn(context);
        when(viewMock.getPagoDAO()).thenReturn(pagoDAOmock);
    }

    @Test
    public void testOnRegisterPaymentClicked() {

        sut.init(viewMock);

        String tipoGasolina = "GLP";
        String nombreGasolinera = "Gasolinera arrandel";
        String precioPorLitro = "1.54";
        String cantidad = "30";

        sut.onRegisterPaymentClicked(tipoGasolina, nombreGasolinera, precioPorLitro, cantidad, LocalDate.now());

        Pago pago = new Pago();
        pago.fuelType = tipoGasolina;
        pago.stationName = nombreGasolinera;
        pago.pricePerLitre = Double.parseDouble(precioPorLitro);
        pago.quantity = Double.parseDouble(cantidad);
        pago.finalPrice = pago.pricePerLitre * pago.quantity;
        pago.date = LocalDate.now().toString();

        //Caso de exito
        verify(viewMock, times(1)).getPagoDAO();
        verify(pagoDAOmock, times(1)).insertAll(pago);
        verify(viewMock, times(1)).showSuccesDialog();

        //Caso error tipo gasolina
        tipoGasolina = "";

        sut.onRegisterPaymentClicked(tipoGasolina, nombreGasolinera, precioPorLitro, cantidad,LocalDate.now());
        verify(viewMock, times(1)).showAlertDialog("Debes seleccionar un tipo de combustible", "Error en el tipo de combustible");

        //Caso error nombre gasolinera
        tipoGasolina = "GLP";
        nombreGasolinera = "";

        sut.onRegisterPaymentClicked(tipoGasolina, nombreGasolinera, precioPorLitro, cantidad, LocalDate.now());
        verify(viewMock, times(1)).showAlertDialog("Debes introducir un nombre de gasolinera", "Error en el nombre de gasolinera");

        //Caso error precio por litro
        nombreGasolinera = "Gasolinera arrandel";
        precioPorLitro = "";

        sut.onRegisterPaymentClicked(tipoGasolina, nombreGasolinera, precioPorLitro, cantidad, LocalDate.now());
        verify(viewMock, times(1)).showAlertDialog("Debes introducir un precio", "Error en el precio por litro");

        //Caso error cantidad
        precioPorLitro = "1.54";
        cantidad = "";
        sut.onRegisterPaymentClicked(tipoGasolina, nombreGasolinera, precioPorLitro, cantidad, LocalDate.now());
        verify(viewMock, times(1)).showAlertDialog("Debes introducir una cantidad de combustible", "Error en la cantidad");

    }
}
