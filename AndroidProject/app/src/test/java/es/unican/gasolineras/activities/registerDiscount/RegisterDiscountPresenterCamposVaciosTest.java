package es.unican.gasolineras.activities.registerDiscount;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;


import es.unican.gasolineras.model.Descuento;

import es.unican.gasolineras.repository.IDescuentoDAO;


@RunWith(RobolectricTestRunner.class)
public class RegisterDiscountPresenterCamposVaciosTest {

    @Mock
    private RegisterDiscountView viewMock;

    private RegisterDiscountPresenter sut;
    private Descuento descuentoValido;

    @Mock
    private IDescuentoDAO descuentoDAOMock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sut = new RegisterDiscountPresenter();
        sut.init(viewMock);
        descuentoValido = new Descuento();
        descuentoValido.discountName="Des1";
        descuentoValido.company="REPSOL";
        descuentoValido.discountType="FIXED";
        descuentoValido.quantityDiscount=0.1;
        descuentoValido.expiranceDate="23/11/2024";
        descuentoValido.discountActive=true;

    }

    @Test
    public void testDescuentoValido() {
        // Caso donde todos los campos están completos
        boolean resultado = sut.hayCamposVacios(descuentoValido);
        assertFalse("Debe devolver false cuando todos los campos estan completos", resultado);
        verify(viewMock, never()).showAlertDialog(anyString(), anyString());
    }

    @Test
    public void testNombreVacio() {
        // Caso donde el nombre está vacío
        Descuento descuento = new Descuento();
        descuento.discountName = "";
        descuento.company = "ALSA";
        descuento.discountType = "PERCENTAGE";
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "2024-11-20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("El nombre no puede estar vacío", "Error");
    }
    @Test
    public void testCompañiaVacia() {
        // Caso donde la compañía está vacía
        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "";  // Compañía vacía
        descuento.discountType = "PERCENTAGE";
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "2024-11-20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("La compañía no puede estar vacía", "Error");
    }



    @Test
    public void testCantidadVacia() {
        // Caso donde la cantidad está vacía
        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "ALSA";
        descuento.discountType = "PERCENTAGE";
        descuento.quantityDiscount = null;  // Cantidad nula
        descuento.expiranceDate = "2024-11-20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("La cantidad no puede estar vacía", "Error");
    }


    @Test
    public void testFechaVacia() {
        // Caso donde la fecha de expiración está vacía
        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "ALSA";
        descuento.discountType = "PERCENTAGE";
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "";  // Fecha vacía

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("La fecha de expiración no puede estar vacía", "Error");
    }

    @Test
    public void testTipoDescuentoVacio() {
        // Caso donde el tipo de descuento está vacío
        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "ALSA";
        descuento.discountType = "";  // Tipo vacío
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "2024-11-20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("El tipo de descuento no puede estar vacío", "Error");
    }



}
