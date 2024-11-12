package es.unican.gasolineras.activities.discountList;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.LinkedList;
import java.util.List;

import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.AppDatabaseDiscount;
import es.unican.gasolineras.repository.IDescuentoDAO;

@RunWith(RobolectricTestRunner.class)
public class DiscountListPresenterITest {

    @Mock
    DiscountListView mockDiscountListView1;

    @Mock
    DiscountListView mockDiscountListView2;

    private DiscountListPresenter sut;

    private Descuento d1 = new Descuento();
    private Descuento d2 = new Descuento();
    private Descuento d3 = new Descuento();
    private List<Descuento> descuentos, descuentosTmp;

    private AppDatabaseDiscount db;
    private IDescuentoDAO descuentosDAO;

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

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
        d2.setExpiranceDate("20/08/2026");
        d3.setExpiranceDate("21/10/2027");
        d1.setDiscountActive(true);
        d2.setDiscountActive(false);
        d3.setDiscountActive(false);

        // Initialize the lists of discounts
        descuentos = new LinkedList<Descuento>();
        descuentos.add(d1);
        descuentos.add(d2);
        descuentos.add(d3);
        descuentosTmp = new LinkedList<Descuento>();

        db = Room.databaseBuilder(context,
                        AppDatabaseDiscount.class, "discounts")
                .allowMainThreadQueries()
                .build();
        descuentosDAO = db.descuentosDAO();
        descuentosDAO = db.descuentosDAO();

        // Mocks behavior
        when(mockDiscountListView1.getDescuentoDAO()).thenReturn(descuentosDAO);
        when(mockDiscountListView2.getDescuentoDAO()).thenReturn(descuentosDAO);

        sut = new DiscountListPresenter();
    }

    @Test
    public void testPresenterInitExitoListaVacia() {
        sut.init(mockDiscountListView2);
        verify(mockDiscountListView2, times(1)).getDescuentoDAO();

        descuentosTmp = descuentosDAO.getAll();
        assert(descuentosTmp.isEmpty());

        verify(mockDiscountListView2, times(1)).showDescuentos(any());
    }

    @Test
    public void testPresenterInitExitoListaConDescuentos() {

        descuentosDAO.insertAll(d1);
        descuentosDAO.insertAll(d2);
        descuentosDAO.insertAll(d3);

        sut.init(mockDiscountListView1);
        verify(mockDiscountListView1, times(1)).getDescuentoDAO();

        descuentosTmp = descuentosDAO.getAll();

        assert(descuentosTmp.get(0).discountName.equals(descuentos.get(0).discountName));
        assert(descuentosTmp.get(0).company.equals(descuentos.get(0).company));
        assert(descuentosTmp.get(0).discountType.equals(descuentos.get(0).discountType));
        assert(descuentosTmp.get(0).quantityDiscount.equals(descuentos.get(0).quantityDiscount));
        assert(descuentosTmp.get(0).expiranceDate.equals(descuentos.get(0).expiranceDate));
        assert(descuentosTmp.get(0).discountActive == (descuentos.get(0).discountActive));

        assert(descuentosTmp.get(1).discountName.equals(descuentos.get(1).discountName));
        assert(descuentosTmp.get(1).company.equals(descuentos.get(1).company));
        assert(descuentosTmp.get(1).discountType.equals(descuentos.get(1).discountType));
        assert(descuentosTmp.get(1).quantityDiscount.equals(descuentos.get(1).quantityDiscount));
        assert(descuentosTmp.get(1).expiranceDate.equals(descuentos.get(1).expiranceDate));
        assert(descuentosTmp.get(1).discountActive == (descuentos.get(1).discountActive));

        assert(descuentosTmp.get(2).discountName.equals(descuentos.get(2).discountName));
        assert(descuentosTmp.get(2).company.equals(descuentos.get(2).company));
        assert(descuentosTmp.get(2).discountType.equals(descuentos.get(2).discountType));
        assert(descuentosTmp.get(2).quantityDiscount.equals(descuentos.get(2).quantityDiscount));
        assert(descuentosTmp.get(2).expiranceDate.equals(descuentos.get(2).expiranceDate));
        assert(descuentosTmp.get(2).discountActive == (descuentos.get(2).discountActive));

        verify(mockDiscountListView1, times(1)).showDescuentos(any());
    }
}
