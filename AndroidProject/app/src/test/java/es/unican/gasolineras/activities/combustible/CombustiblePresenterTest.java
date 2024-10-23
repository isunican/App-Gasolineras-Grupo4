package es.unican.gasolineras.activities.combustible;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.TipoCombustible;
import es.unican.gasolineras.repository.IGasolinerasRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
public class CombustiblePresenterTest {

    private Gasolinera g1;
    private Gasolinera g2;
    private Gasolinera g3;
    private Context context;
    private CombustiblePresenter sut;

    //Mocks
    @Mock
    CombustibleView mockCombustibleView;

    @Mock
    CombustibleView mockCombustibleViewError;

    @Before
    public void inicializa() {
        // Iniciar mockito
        MockitoAnnotations.openMocks(this);

        context = ApplicationProvider.getApplicationContext();
        sut = new CombustiblePresenter();
        //Parametros de las gasolineras
        g1 = new Gasolinera();
        g1.setId("g1");
        g1.setRotulo("GasolineraA");
        g1.setPrecioProducto(1.24);
        g2 = new Gasolinera();
        g2.setId("g2");
        g2.setRotulo("GasolineraB");
        g2.setPrecioProducto(1.45);
        g3 = new Gasolinera();
        g3.setId("g3");
        g3.setRotulo("GasolineraC");
        g3.setPrecioProducto(1.11);


        List<Gasolinera> gasolineras1 = new ArrayList<>();
        gasolineras1.add(g1);
        gasolineras1.add(g2);
        gasolineras1.add(g3);

        IGasolinerasRepository testRepository = MockRepositories.getTestRepository(context, gasolineras1);
        when(mockCombustibleView.getGasolinerasRepository()).thenReturn(testRepository);

        IGasolinerasRepository testRepositoryFail = MockRepositories.getTestRepositoryFail(context, gasolineras1);
        when(mockCombustibleViewError.getGasolinerasRepository()).thenReturn(testRepositoryFail);
    }

    @Test
    public void OrderStationsTest() {
        //UP.1a ordenar ascendente
        List<Gasolinera> gasolineras1 = new ArrayList<>();
        gasolineras1.add(g1);
        gasolineras1.add(g2);
        gasolineras1.add(g3);

        sut.orderStations(1, gasolineras1);
        assertEquals(g3, gasolineras1.get(0));
        assertEquals(g1, gasolineras1.get(1));
        assertEquals(g2, gasolineras1.get(2));

        //UP.1b ordenar descendente
        List<Gasolinera> gasolineras2 = new ArrayList<>();
        gasolineras2.add(g1);
        gasolineras2.add(g2);
        gasolineras2.add(g3);

        sut.orderStations(0, gasolineras2);
        assertEquals(g2, gasolineras2.get(0));
        assertEquals(g1, gasolineras2.get(1));
        assertEquals(g3, gasolineras2.get(2));

        //UP.1c devolver sin ordenar
        List<Gasolinera> gasolineras3 = new ArrayList<>();
        gasolineras3.add(g1);
        gasolineras3.add(g2);
        gasolineras3.add(g3);

        sut.orderStations(2, gasolineras3);
        assertEquals(g1, gasolineras3.get(0));
        assertEquals(g2, gasolineras3.get(1));
        assertEquals(g3, gasolineras3.get(2));

        //UP.1d lista vacia
        List<Gasolinera> gasolineras4 = new ArrayList<>();
        sut.orderStations(1, gasolineras4);
        assertEquals(0, gasolineras4.size());
    }

    @Test
    public void initLoadTest() {
        List<Gasolinera> gasolineras1 = new ArrayList<>();
        gasolineras1.add(g1);
        gasolineras1.add(g2);
        gasolineras1.add(g3);

        //Caso exito

        sut.init(mockCombustibleView, TipoCombustible.GASOLINA_95_E5);
        //Comprobar init
        verify(mockCombustibleView,times(1)).init();
        //Comprobar load
        verify(mockCombustibleView,times(1)).getGasolinerasRepository();
        verify(mockCombustibleView,times(1)).showStations(gasolineras1);
        verify(mockCombustibleView,times(1)).showLoadCorrect(gasolineras1.size());

        //Caso fallo

        sut.init(mockCombustibleViewError, TipoCombustible.GASOLINA_95_E5);
        //Comprobar init
        verify(mockCombustibleViewError,times(1)).init();
        //Comprobar load
        verify(mockCombustibleViewError,times(1)).getGasolinerasRepository();
        verify(mockCombustibleViewError,times(2)).showLoadError();
    }
}
