package es.unican.gasolineras;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDate;
import java.util.LinkedList;

import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryPresenter;
import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryView;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

@RunWith(RobolectricTestRunner.class)
public class HistoryPaymentPresenterInitTest {

    @Mock
    PaymentHistoryView mockPaymentView;

    @Mock
    PaymentHistoryView mockPaymentView2;

    @Mock
    IPagoDAO mockPaymentDAO;

    @Mock
    IPagoDAO mockPaymentDAO2;

    private PaymentHistoryPresenter sut;

    private Pago p = new Pago();
    private LinkedList<Pago> pagos, pagosVacio;

    @Before
    public void inicializa(){
        // Init mockito
        MockitoAnnotations.openMocks(this);

        // Example payment
        p.setQuantity(1.0);
        p.setFuelType("Gasoleo A");
        p.setPricePerLitre(1.20);
        p.setDate(String.valueOf(LocalDate.now()));
        p.setStationName("Prueba");
        p.setFinalPrice(20.0);

        // Initialize the lists of payments
        pagos = new LinkedList<Pago>();
        pagosVacio = new LinkedList<Pago>();

        // Add the payment to the list
        pagos.add(p);

        // Prepare the return of the mocks
        when(mockPaymentDAO.getAll()).thenReturn(pagos);
        when(mockPaymentDAO2.getAll()).thenReturn(pagosVacio);
        when(mockPaymentView.getPagoDAO()).thenReturn(mockPaymentDAO);
        when(mockPaymentView2.getPagoDAO()).thenReturn(mockPaymentDAO2);

        // Create the sut
        sut = new PaymentHistoryPresenter();
    }

    @Test
    public void testPresenterInitExitoListaConPagos(){
        sut.init(mockPaymentView);
        verify(mockPaymentView,times(1)).getPagoDAO();
        verify(mockPaymentDAO,times(2)).getAll();
        verify(mockPaymentView, times(1)).showPagos(pagos);
    }

    @Test
    public void testPresenterInitExitoListaVacia() {
        sut.init(mockPaymentView2);
        verify(mockPaymentView,times(1)).getPagoDAO();
        verify(mockPaymentDAO2,times(2)).getAll();
        verify(mockPaymentView2, times(1)).showPagos(pagosVacio);
    }

}
