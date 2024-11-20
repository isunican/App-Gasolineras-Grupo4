package es.unican.gasolineras.activities.analyticsView;

import static org.mockito.Mockito.when;

import android.database.sqlite.SQLiteException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import es.unican.gasolineras.activities.paymentHistory.PaymentHistoryPresenter;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

@RunWith(RobolectricTestRunner.class)
public class AnalyticsViewPresenterLoadForMonthYear {

    @Mock
    IPagoDAO mockPaymentDAO;

    @Mock
    IPagoDAO mockPaymentDAO2;

    @Mock
    IPagoDAO mockPaymentDAO3;

    @Mock
    AnalyticsViewView mockAnalyticsView;

    @Mock
    AnalyticsViewView mockAnalyticsView2;
    @Mock
    AnalyticsViewView mockAnalyticsView3;


    private Pago p;
    private List<Pago> pagos, pagosVacio;

    private AnalyticsViewPresenter sut;

    @Before
    public void inicializa(){
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
        when(mockAnalyticsView.getPagoDAO()).thenReturn(mockPaymentDAO);
        when(mockAnalyticsView2.getPagoDAO()).thenReturn(mockPaymentDAO2);
        when(mockAnalyticsView3.getPagoDAO()).thenReturn(mockPaymentDAO3);

        // Create the sut
        sut = new AnalyticsViewPresenter();
    }

}
