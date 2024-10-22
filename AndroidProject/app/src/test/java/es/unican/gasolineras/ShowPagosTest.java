package es.unican.gasolineras;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.activities.main.MainPresenter;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.activities.paymentHistory.IPaymentHistoryContract;
import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryView;
import es.unican.gasolineras.common.exceptions.DataAccessException;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.GasolinerasResponse;
import es.unican.gasolineras.model.Pago;

@RunWith(RobolectricTestRunner.class)
public class ShowPagosTest {

    @Mock
    PaymentHistoryView mockPaymentView;

    private PaymentHistoryView sut;
    private Pago p,p2,p3;
    private List<Pago> lista, listaError;
    @Before
    public void inicializa(){
        // Init mockito
        MockitoAnnotations.initMocks(this);
        // Example payment
        p.setQuantity(1.0);
        p.setFuelType("Gasoleo A");
        p.setPricePerLitre(1.20);
        p.setDate(String.valueOf(LocalDate.now()));
        p.setStationName("Prueba");
        p.setFinalPrice(20.0);
        // List add the payment
        lista.add(p);

        // Mocks to indicate the results that the methods have to do
        when(mockPaymentView.showPagos(listaError)).thenThrow(DataAccessException.class);

        sut = new PaymentHistoryView();
    }

    @Test
    public void testShowPagosExito(){
        
        assertThrows(null, () -> sut.showPagos(lista));
        verify(mockPaymentView, times(1).showPagos());
        assertEquals(lista, sut.showPagos(lista));
    }

    @Test
    public void testShowPagosErrorBD(){
        assertThrows(DataAccessException.class, () -> sut.showPagos(listaError));
    }
}
