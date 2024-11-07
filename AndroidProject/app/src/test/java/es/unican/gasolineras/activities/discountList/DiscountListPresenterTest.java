package es.unican.gasolineras.activities.discountList;

import static org.mockito.ArgumentMatchers.any;
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

import java.util.LinkedList;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;

@RunWith(RobolectricTestRunner.class)
public class DiscountListPresenterTest {

    @Mock
    DiscountListView mockDiscountListView1;

    @Mock
    DiscountListView mockDiscountListView2;

    @Mock
    DiscountListView mockDiscountListView3;

    @Mock
    IDescuentoDAO mockDescuentoDAO1;

    @Mock
    IDescuentoDAO mockDescuentoDAO2;

    @Mock
    IDescuentoDAO mockDescuentoDAO3;

    private DiscountListPresenter sut;

    private Descuento d1 = new Descuento();
    private Descuento d2 = new Descuento();
    private Descuento d3 = new Descuento();
    private LinkedList<Descuento> descuentos, descuentosVacio;

    @Before
    public void inicializa() {
        // Init mockito
        MockitoAnnotations.openMocks(this);

        // Initialize the discounts
        d1.setDiscountName("Descuento1");
        d2.setDiscountName("Descuento2");
        d3.setDiscountName("Descuento3");
        d1.setCompany("Repsol");
        d2.setCompany("Cepsa");
        d3.setCompany("BP");
        d1.setDiscountType("Fijo");
        d2.setDiscountType("Variable");
        d3.setDiscountType("Fijo");
        d1.setQuantityDiscount(0.1);
        d2.setQuantityDiscount(20.0);
        d3.setQuantityDiscount(0.2);
        d1.setExpiranceDate("23/11/2024");
        d2.setExpiranceDate("20/8/2026");
        d3.setExpiranceDate("21/10/2027");
        d1.setDiscountActive(true);
        d2.setDiscountActive(false);
        d3.setDiscountActive(false);

        // Initialize the lists of discounts
        descuentos = new LinkedList<Descuento>();
        descuentos.add(d1);
        descuentos.add(d2);
        descuentos.add(d3);
        descuentosVacio = new LinkedList<Descuento>();

        // Mocks behavior
        when(mockDescuentoDAO1.getAll()).thenReturn(descuentos);
        when(mockDescuentoDAO2.getAll()).thenReturn(descuentosVacio);
        when(mockDescuentoDAO3.getAll()).thenThrow(SQLiteException.class);
        when(mockDiscountListView1.getDescuentoDAO()).thenReturn(mockDescuentoDAO1);
        when(mockDiscountListView2.getDescuentoDAO()).thenReturn(mockDescuentoDAO2);
        when(mockDiscountListView3.getDescuentoDAO()).thenReturn(mockDescuentoDAO3);

        sut = new DiscountListPresenter();
    }

    @Test
    public void testPresenterInitExitoListaConDescuentos() {
        sut.init(mockDiscountListView1);
        verify(mockDiscountListView1, times(1)).getDescuentoDAO();
        verify(mockDescuentoDAO1, times(1)).getAll();
        verify(mockDiscountListView1, times(1)).showDescuentos(descuentos);

    }

    @Test
    public void testPresenterInitExitoListaVacia() {
        sut.init(mockDiscountListView2);
        verify(mockDiscountListView2, times(1)).getDescuentoDAO();
        verify(mockDescuentoDAO2, times(1)).getAll();
        verify(mockDiscountListView2, times(1)).showDescuentos(descuentosVacio);

    }

    @Test
    public void testPresenterInitErrorAccesoBD() {
        sut.init(mockDiscountListView3);
        verify(mockDiscountListView3, times(1)).getDescuentoDAO();
        verify(mockDescuentoDAO3, times(1)).getAll();
        verify(mockDiscountListView3, times(0)).showDescuentos(any());
        verify(mockDiscountListView3, times(1)).showErrorBD();
    }

}
