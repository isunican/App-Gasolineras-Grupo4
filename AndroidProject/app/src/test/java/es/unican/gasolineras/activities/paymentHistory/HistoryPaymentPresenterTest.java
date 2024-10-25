package es.unican.gasolineras.activities.paymentHistory;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.database.sqlite.SQLiteException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDate;
import java.util.LinkedList;

import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

/** TEST REALIZADO POR
 * ALEJANDRO ACEBO**/
@RunWith(RobolectricTestRunner.class)
public class HistoryPaymentPresenterTest {

    @Mock
    PaymentHistoryView mockPaymentView;

    @Mock
    PaymentHistoryView mockPaymentView2;

    @Mock
    PaymentHistoryView mockPaymentView3;

    @Mock
    IPagoDAO mockPaymentDAO;

    @Mock
    IPagoDAO mockPaymentDAO2;

    @Mock
    IPagoDAO mockPaymentDAO3;

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
        when(mockPaymentDAO3.getAll()).thenThrow(SQLiteException.class);
        when(mockPaymentView.getPagoDAO()).thenReturn(mockPaymentDAO);
        when(mockPaymentView2.getPagoDAO()).thenReturn(mockPaymentDAO2);
        when(mockPaymentView3.getPagoDAO()).thenReturn(mockPaymentDAO3);

        // Create the sut
        sut = new PaymentHistoryPresenter();
    }

    @Test
    public void testPresenterInitExitoListaConPagos(){
        sut.init(mockPaymentView);
        verify(mockPaymentView,times(1)).getPagoDAO();
        verify(mockPaymentDAO,times(1)).getAll();
        verify(mockPaymentView, times(1)).showPagos(pagos);
    }

    @Test
    public void testPresenterInitExitoListaVacia() {
        sut.init(mockPaymentView2);
        verify(mockPaymentView2,times(1)).getPagoDAO();
        verify(mockPaymentDAO2,times(1)).getAll();
        verify(mockPaymentView2, times(1)).showPagos(pagosVacio);
    }

    @Test
    public void testPresenterInitErrorAccesoBD(){
        sut.init(mockPaymentView3);
        verify(mockPaymentView3, times(1)).getPagoDAO();
        verify(mockPaymentDAO3,times(1)).getAll();
        verify(mockPaymentView3,times(0)).showPagos(pagos);
        verify(mockPaymentView3,times(1)).showErrorBD();
    }

}
