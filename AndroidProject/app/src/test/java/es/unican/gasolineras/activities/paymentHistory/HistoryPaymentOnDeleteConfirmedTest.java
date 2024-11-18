package es.unican.gasolineras.activities.paymentHistory;

import static org.mockito.Mockito.doThrow;
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
 * JESÚS LOBOS**/
@RunWith(RobolectricTestRunner.class)
public class HistoryPaymentOnDeleteConfirmedTest {

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
        p.stationName="Avia";
        p.date= String.valueOf(LocalDate.now());
        p.fuelType="Biodiesel";
        p.quantity=20.0;
        p.pricePerLitre=1.50;
        p.finalPrice=30.0;


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
        when(mockPaymentView3.getPagoDAO()).thenReturn(mockPaymentDAO3);

        // Create the sut
        sut = new PaymentHistoryPresenter();
    }

    @Test
    public void testOnDeleteConfirmedSuccess() {
        sut.init(mockPaymentView);
        sut.onDeleteConfirmed(p);
        verify(mockPaymentDAO, times(1)).delete(p);
        verify(mockPaymentView, times(1)).showAlertDialog("Éxito eliminación", "El pago ha sido eliminado de manera exitosa de la base de datos");
    }


    @Test
    public void testOnDeleteConfirmedBDError() {
        doThrow(new SQLiteException()).when(mockPaymentDAO3).delete(p);
        sut.init(mockPaymentView3);
        sut.onDeleteConfirmed(p);
        verify(mockPaymentView3, times(1)).showErrorBD();
    }


}
