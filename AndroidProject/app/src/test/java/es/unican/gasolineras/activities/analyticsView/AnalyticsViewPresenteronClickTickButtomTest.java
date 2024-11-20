package es.unican.gasolineras.activities.analyticsView;

import static org.mockito.Mockito.mock;
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
import java.util.List;

import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.IPagoDAO;

@RunWith(RobolectricTestRunner.class)
public class AnalyticsViewPresenteronClickTickButtomTest {

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


    private Pago p, p2;
    private List<Pago> pagos, pagosVacio;

    private AnalyticsViewPresenter sut;

    @Before
    public void inicializa(){
        MockitoAnnotations.openMocks(this);

        p = new Pago();
        p.setQuantity(20.0);
        p.setFuelType("Bioetanol");
        p.setPricePerLitre(1.24);
        p.setDate(String.valueOf(LocalDate.of(2024, 11, 16)));
        p.setStationName("Gasolinera Arrandel");
        p.setFinalPrice(24.8);

        p2 = new Pago();
        p2.setQuantity(25.0);
        p2.setFuelType("GLP");
        p2.setPricePerLitre(1.45);
        p2.setDate(String.valueOf(LocalDate.of(2024, 11, 20)));
        p2.setStationName("Gasolinera Repsol Tapuerta");
        p2.setFinalPrice(36.25);


        // Initialize the lists of payments
        pagos = new LinkedList<Pago>();
        pagosVacio = new LinkedList<Pago>();

        // Add the payment to the list
        pagos.add(p);
        pagos.add(p2);

        // Prepare the return of the mocks
        when(mockAnalyticsView.getPagoDAO()).thenReturn(mockPaymentDAO);
        when(mockAnalyticsView2.getPagoDAO()).thenReturn(mockPaymentDAO2);
        when(mockAnalyticsView3.getPagoDAO()).thenReturn(mockPaymentDAO3);
        when(mockPaymentDAO.getAll()).thenReturn(pagos);
        when(mockPaymentDAO2.getAll()).thenReturn(pagosVacio);
        when(mockPaymentDAO3.getAll()).thenThrow(SQLiteException.class);

        // Create the sut
        sut = new AnalyticsViewPresenter();
    }

    @Test
    public void testAnalyticsViewPresenteronClickTickButtomExito(){
        sut.init(mockAnalyticsView);
        sut.onClickTickButtom(11,2024);
        verify(mockAnalyticsView,times(2)).getPagoDAO();
        verify(mockPaymentDAO,times(2)).getAll();
        verify(mockAnalyticsView,times(1)).showAnalytics(1.345,22.5,45.0,61.05);
    }

    @Test
    public void testAnalyticsViewPresenteronClickTickButtomSinPagos(){
        sut.init(mockAnalyticsView2);
        sut.onClickTickButtom(11,2024);
        verify(mockAnalyticsView2,times(2)).getPagoDAO();
        verify(mockPaymentDAO2,times(2)).getAll();
        verify(mockAnalyticsView2,times(1)).showAnalytics(0.0,0.0,0.0,0.0);

    }

    @Test
    public void testAnalyticsViewPresenteronClickTickButtomErrorBD(){
        sut.init(mockAnalyticsView3);
        sut.onClickTickButtom(11,2024);
        verify(mockAnalyticsView3,times(2)).getPagoDAO();
        verify(mockPaymentDAO3,times(2)).getAll();
        verify(mockAnalyticsView3,times(0)).showAnalytics(0.0,0.0,0.0,0.0);

    }

}
