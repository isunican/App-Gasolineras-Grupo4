package es.unican.gasolineras.activities.analyticsView;

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
public class AnalyticsViewPresenteronCharTypeSelectedTest {
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

        // Initialize the lists of payments
        pagos = new LinkedList<Pago>();
        pagosVacio = new LinkedList<Pago>();

        // Add the payment to the list
        pagos.add(p);
        pagos.add(p2);

        // Prepare the return of the mocks
       // when(mockAnalyticsView.getPagoDAO()).thenReturn(mockPaymentDAO);
        //when(mockAnalyticsView2.getPagoDAO()).thenReturn(mockPaymentDAO2);
        //when(mockAnalyticsView3.getPagoDAO()).thenReturn(mockPaymentDAO3);
        when(mockAnalyticsView.getPagoDAO().getPagosByMonthAndYear("11","2024")).thenReturn(pagos);
        //when(mockPaymentDAO.getAll()).thenReturn(pagos);
        //when(mockPaymentDAO2.getAll()).thenReturn(pagosVacio);
        //when(mockPaymentDAO3.getAll()).thenThrow(SQLiteException.class);

        // Create the sut
        sut = new AnalyticsViewPresenter();
    }

    @Test
    public void testAnalyticsViewPresenteronCharTypeSelectedGastoDiario(){
        sut.init(mockAnalyticsView);
        sut.onChartTypeSelected("Gasto Diario","11","2024");
        verify(mockAnalyticsView,times(1)).clearContainer();
        verify(mockAnalyticsView,times(2)).getPagoDAO().getPagosByMonthAndYear("11","2024");
        verify(mockAnalyticsView,times(1)).showLineChart(pagos);
    }
}
