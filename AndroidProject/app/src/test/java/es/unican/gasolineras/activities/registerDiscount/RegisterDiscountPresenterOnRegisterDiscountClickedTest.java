package es.unican.gasolineras.activities.registerDiscount;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.IDescuentoDAO;


@RunWith(RobolectricTestRunner.class)
public class RegisterDiscountPresenterOnRegisterDiscountClickedTest {

    @Mock
    private RegisterDiscountView mockView;

    @Mock
    private IDescuentoDAO mockDescuentoDAO;

    private RegisterDiscountPresenter sut;

    private Descuento descuentoValido;
    private Descuento descuentoExistente;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sut = new RegisterDiscountPresenter();


        descuentoValido = new Descuento();
        descuentoValido.discountName = "Descuento1";
        descuentoValido.company = "REPSOL";
        descuentoValido.discountType = "PERCENTAGE";
        descuentoValido.quantityDiscount = 50.0;
        descuentoValido.expiranceDate = "23/11/2024";
        descuentoValido.discountActive = true;


        descuentoExistente = new Descuento();
        descuentoExistente.discountName = "Descuento1";
        descuentoExistente.company = "REPSOL";
        descuentoExistente.discountType = "PERCENTAGE";
        descuentoExistente.quantityDiscount = 50.0;
        descuentoExistente.expiranceDate = "23/11/2024";
        descuentoExistente.discountActive = true;


        when(mockDescuentoDAO.findByName(descuentoValido.discountName)).thenReturn(null);
        when(mockDescuentoDAO.findByName(descuentoExistente.discountName)).thenReturn(descuentoExistente);



    }

    @Test
    public void testRegistrarDescuentoExitoso() {

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);
        sut.onRegisterDiscountClicked(descuentoValido);
        verify(mockDescuentoDAO, times(1)).insertAll(descuentoValido);
        verify(mockView, times(0)).showAlertDialog(anyString(), anyString());


    }

    @Test
    public void testDescuentoDuplicado() {

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);


        sut.onRegisterDiscountClicked(descuentoExistente);


        verify(mockDescuentoDAO, times(0)).insertAll(descuentoExistente);


        verify(mockView, times(1)).showAlertDialog(
                "No se puede crear el descuento porque ya existe uno con el mismo nombre", "Error"
        );
    }

    @Test
    public void testCamposVacios() {

        Descuento descuentoConNombreVacio = new Descuento();
        descuentoConNombreVacio.discountName = "";
        descuentoConNombreVacio.company = "ALSA";
        descuentoConNombreVacio.discountType = "PERCENTAGE";
        descuentoConNombreVacio.quantityDiscount = 50.0;
        descuentoConNombreVacio.expiranceDate = "2024-11-20";

        sut.init(mockView);
        boolean resultado = sut.hayCamposVacios(descuentoConNombreVacio);

        assertTrue("Debe haber un error cuando el nombre está vacío", resultado);

        verify(mockView).showAlertDialog("El nombre no puede estar vacío", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoConNombreVacio);
    }

    @Test
    public void testPorcentajeInvalido() {

        Descuento descuentoConPorcentajeInvalido = new Descuento();
        descuentoConPorcentajeInvalido.discountName = "Descuento Negativo";
        descuentoConPorcentajeInvalido.company = "ALSA";
        descuentoConPorcentajeInvalido.discountType = "PERCENTAGE";
        descuentoConPorcentajeInvalido.quantityDiscount = -1.0;
        descuentoConPorcentajeInvalido.expiranceDate = "23/11/2024";

        sut.init(mockView);
        boolean resultado = sut.hayCamposVacios(descuentoConPorcentajeInvalido);

        assertTrue("Debe haber un error cuando el porcentaje es inválido", resultado);

        verify(mockView).showAlertDialog("El porcentaje debe estar entre 0 y 100", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoConPorcentajeInvalido);
    }


    @Test
    public void testFechaVacia() {
        Descuento descuentoConFechaVacia = new Descuento();
        descuentoConFechaVacia.discountName = "Descuento Fecha Vacía";
        descuentoConFechaVacia.company = "ALSA";
        descuentoConFechaVacia.discountType = "PERCENTAGE";
        descuentoConFechaVacia.quantityDiscount = 50.0;
        descuentoConFechaVacia.expiranceDate = "";


        sut.init(mockView);
        boolean resultado = sut.hayCamposVacios(descuentoConFechaVacia);

        assertTrue("Debe haber un error cuando la fecha está vacía", resultado);

        verify(mockView).showAlertDialog("La fecha de expiración no puede estar vacía", "Error");

        verify(mockDescuentoDAO, times(0)).insertAll(descuentoConFechaVacia);
    }

}
