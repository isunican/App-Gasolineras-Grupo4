package es.unican.gasolineras.activities.registerDiscount;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
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


import es.unican.gasolineras.model.Descuento;

import es.unican.gasolineras.repository.IDescuentoDAO;


@RunWith(RobolectricTestRunner.class)
public class RegisterDiscountPresenterTest {


    @Mock
    private RegisterDiscountView mockView;

    @Mock
    private IDescuentoDAO mockDescuentoDAO;

    @Mock
    private IDescuentoDAO mockDescuentoDAO2;


    private RegisterDiscountPresenter sut;

    private Descuento descuentoValido;
    private Descuento descuentoExistente;

    @Mock
    private RegisterDiscountView viewMock;

    private Descuento descuentoValidoCamposVacios;

    @Mock
    private IDescuentoDAO descuentoDAOMock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sut = new RegisterDiscountPresenter();
        sut.init(viewMock);
        descuentoValidoCamposVacios = new Descuento();
        descuentoValidoCamposVacios.discountName="Des1";
        descuentoValidoCamposVacios.company="REPSOL";
        descuentoValidoCamposVacios.discountType="€/l";
        descuentoValidoCamposVacios.quantityDiscount=0.1;
        descuentoValidoCamposVacios.expiranceDate="23/11/2024";
        descuentoValidoCamposVacios.discountActive=true;


        descuentoValido = new Descuento();
        descuentoValido.discountName = "Descuento1";
        descuentoValido.company = "ALSA";
        descuentoValido.discountType = "%";
        descuentoValido.quantityDiscount = 50.0;
        descuentoValido.expiranceDate = "23/11/2024";
        descuentoValido.discountActive = true;


        descuentoExistente = new Descuento();
        descuentoExistente.discountName = "Des1";
        descuentoExistente.company = "REPSOL";
        descuentoExistente.discountType = "€/l";
        descuentoExistente.quantityDiscount = 0.1;
        descuentoExistente.expiranceDate = "23/11/2024";
        descuentoExistente.discountActive = true;


        when(mockDescuentoDAO.findByName(descuentoValido.discountName)).thenReturn(null);
        when(mockDescuentoDAO.findByName(descuentoExistente.discountName)).thenReturn(descuentoExistente);
        when(mockDescuentoDAO2.getAll()).thenThrow(SQLiteException.class);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);

    }

    @Test
    public void testDescuentoValidoCamposVacios() {

        boolean resultado = sut.hayCamposVacios(descuentoValidoCamposVacios);
        assertFalse("Debe devolver false cuando todos los campos estan completos", resultado);
        verify(viewMock, never()).showAlertDialog(anyString(), anyString());
    }

    @Test
    public void testNombreVacioCamposVacios() { // UP1.2

        Descuento descuento = new Descuento();
        descuento.discountName = "";
        descuento.company = "ALSA";
        descuento.discountType = "%";
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "2024/11/20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("El nombre no puede estar vacío", "Error");
    }
    @Test
    public void testCompañiaVaciaCamposVacios() { // UP1.3

        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "";
        descuento.discountType = "%";
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "2024/11/20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("La compañía no puede estar vacía", "Error");
    }



    @Test
    public void testCantidadVaciaCamposVacios() { // UP1.4

        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "ALSA";
        descuento.discountType = "%";
        descuento.quantityDiscount = null;
        descuento.expiranceDate = "2024/11/20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("La cantidad no puede estar vacía", "Error");
    }


    @Test
    public void testFechaVaciaCamposVacios() { // UP1.5

        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "ALSA";
        descuento.discountType = "%";
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("La fecha de expiración no puede estar vacía", "Error");
    }

    @Test
    public void testTipoDescuentoVacioCamposVacios() { // UP1.6
        Descuento descuento = new Descuento();
        descuento.discountName = "Nombre";
        descuento.company = "ALSA";
        descuento.discountType = "";
        descuento.quantityDiscount = 50.0;
        descuento.expiranceDate = "2024/11/20";

        boolean resultado = sut.hayCamposVacios(descuento);
        assertTrue("Debe haber un error", resultado);

        verify(viewMock).showAlertDialog("El tipo de descuento no puede estar vacío", "Error");
    }


    @Test
    public void testRegistrarDescuentoExitoso() { // UP 2.1

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);
        sut.onRegisterDiscountClicked(descuentoValido);
        verify(mockDescuentoDAO, times(1)).insertAll(descuentoValido);
        verify(mockView, times(0)).showAlertDialog(anyString(), anyString());


    }

    @Test
    public void testDescuentoDuplicado() { //UP 2.8

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);
        sut.onRegisterDiscountClicked(descuentoExistente);


        verify(mockDescuentoDAO, times(0)).insertAll(descuentoExistente);
        verify(mockView, times(1)).showAlertDialog(
                "No se puede crear el descuento porque ya existe uno con el mismo nombre", "Error"
        );
    }

    @Test
    public void testNombreVacio() { //UP 2.2

        Descuento descuentoConNombreVacio = new Descuento();
        descuentoConNombreVacio.discountName = "";
        descuentoConNombreVacio.company = "ALSA";
        descuentoConNombreVacio.discountType = "%";
        descuentoConNombreVacio.quantityDiscount = 50.0;
        descuentoConNombreVacio.expiranceDate = "2024-11-20";

        sut.init(mockView);
        sut.onRegisterDiscountClicked(descuentoConNombreVacio);


        verify(mockView).showAlertDialog("El nombre no puede estar vacío", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoConNombreVacio);


    }

    @Test
    public void testPorcentajeInvalido() {  // UP 2.9
        Descuento descuentoNoValido = new Descuento();
        descuentoNoValido.discountName = "Nombre";
        descuentoNoValido.company = "ALSA";
        descuentoNoValido.discountType = "%";
        descuentoNoValido.quantityDiscount = 0.0; //
        descuentoNoValido.expiranceDate = "20/11/2024";

        sut.init(mockView);

        sut.onRegisterDiscountClicked(descuentoNoValido);

        verify(mockView).showAlertDialog("El porcentaje debe estar entre 0 y 100", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoNoValido);
    }


    @Test
    public void testFechaVacia() {  // UP 2.5
        Descuento descuentoConFechaVacia = new Descuento();
        descuentoConFechaVacia.discountName = "Nombre";
        descuentoConFechaVacia.company = "ALSA";
        descuentoConFechaVacia.discountType = "%";
        descuentoConFechaVacia.quantityDiscount = 50.0;
        descuentoConFechaVacia.expiranceDate = "";

        sut.init(mockView);
        sut.onRegisterDiscountClicked(descuentoConFechaVacia);


        verify(mockView).showAlertDialog("La fecha de expiración no puede estar vacía"
                , "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoConFechaVacia);

    }

    @Test
    public void testCompaniaVacia() {  // UP 2.3
        Descuento descuentoConCompaniaVacia = new Descuento();
        descuentoConCompaniaVacia.discountName = "Nombre";
        descuentoConCompaniaVacia.company = "";
        descuentoConCompaniaVacia.discountType = "%";
        descuentoConCompaniaVacia.quantityDiscount = 50.0;
        descuentoConCompaniaVacia.expiranceDate = "20/11/2024";

        sut.init(mockView);
        sut.onRegisterDiscountClicked(descuentoConCompaniaVacia);


        verify(mockView).showAlertDialog("La compañía no puede estar vacía"

                , "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoConCompaniaVacia);

    }

    @Test
    public void testCantidadVacia() {  // UP 2.4
        Descuento descuentoCantidadVacia = new Descuento();
        descuentoCantidadVacia.discountName = "Nombre";
        descuentoCantidadVacia.company = "ALSA";
        descuentoCantidadVacia.discountType = "%";
        descuentoCantidadVacia.quantityDiscount = null;
        descuentoCantidadVacia.expiranceDate = "20/11/2024";

        sut.init(mockView);
        sut.onRegisterDiscountClicked(descuentoCantidadVacia);


        verify(mockView).showAlertDialog("La cantidad no puede estar vacía", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoCantidadVacia);

    }

    @Test
    public void testTipoDescuentoVacio() {  // UP 2.6
        Descuento descuentoTipoVacio = new Descuento();
        descuentoTipoVacio.discountName = "Nombre";
        descuentoTipoVacio.company = "ALSA";
        descuentoTipoVacio.discountType = "";
        descuentoTipoVacio.quantityDiscount = 50.0;
        descuentoTipoVacio.expiranceDate = "20/11/2024";

        sut.init(mockView);
        sut.onRegisterDiscountClicked(descuentoTipoVacio);


        verify(mockView).showAlertDialog("El tipo de descuento no puede estar vacío", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoTipoVacio);

    }



    @Test
    public void testFechaNoValida() {  // UP 2.7
        Descuento descuentoFechaNoValida = new Descuento();
        descuentoFechaNoValida.discountName = "Nombre";
        descuentoFechaNoValida.company = "ALSA";
        descuentoFechaNoValida.discountType = "%";
        descuentoFechaNoValida.quantityDiscount = 50.0;
        descuentoFechaNoValida.expiranceDate = "01/11/2024";

        sut.init(mockView);
        sut.onRegisterDiscountClicked(descuentoFechaNoValida);


        verify(mockView).showAlertDialog("La fecha de expiración no puede ser anterior a la fecha actual", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoFechaNoValida);

    }

    @Test
    public void testDescuentoExito2() {  // UP 2.10
        Descuento descuentoExito2 = new Descuento();
        descuentoExito2.discountName = "Nombre2";
        descuentoExito2.company = "ALSA";
        descuentoExito2.discountType = "%";
        descuentoExito2.quantityDiscount = 0.1;
        descuentoExito2.expiranceDate = "20/11/2024";

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);
        sut.onRegisterDiscountClicked(descuentoExito2);
        verify(mockDescuentoDAO, times(1)).insertAll(descuentoExito2);
        verify(mockView, times(0)).showAlertDialog(anyString(), anyString());


    }

    @Test
    public void testDescuentoExito3() {  // UP 2.11
        Descuento descuentoExito3 = new Descuento();
        descuentoExito3.discountName = "Nombre3";
        descuentoExito3.company = "ALSA";
        descuentoExito3.discountType = "%";
        descuentoExito3.quantityDiscount = 100.0;
        descuentoExito3.expiranceDate = "20/11/2024";

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);
        sut.onRegisterDiscountClicked(descuentoExito3);
        verify(mockDescuentoDAO, times(1)).insertAll(descuentoExito3);
        verify(mockView, times(0)).showAlertDialog(anyString(), anyString());


    }

    @Test
    public void testCantidadFijaNoValida() {  // UP 2.12
        Descuento descuentoCantidadVacia = new Descuento();
        descuentoCantidadVacia.discountName = "Nombre";
        descuentoCantidadVacia.company = "ALSA";
        descuentoCantidadVacia.discountType = "€/l";
        descuentoCantidadVacia.quantityDiscount = 0.0;
        descuentoCantidadVacia.expiranceDate = "20/11/2024";

        sut.init(mockView);
        sut.onRegisterDiscountClicked(descuentoCantidadVacia);


        verify(mockView).showAlertDialog("La cantidad fija debe ser mayor que 0", "Error");
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoCantidadVacia);

    }
    @Test
    public void testDescuentoExito4() {  // UP 2.13
        Descuento descuentoExito4 = new Descuento();
        descuentoExito4.discountName = "Nombre4";
        descuentoExito4.company = "ALSA";
        descuentoExito4.discountType = "€/l";
        descuentoExito4.quantityDiscount = 0.1;
        descuentoExito4.expiranceDate = "20/11/2024";

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);
        sut.onRegisterDiscountClicked(descuentoExito4);
        verify(mockDescuentoDAO, times(1)).insertAll(descuentoExito4);
        verify(mockView, times(0)).showAlertDialog(anyString(), anyString());


    }
    @Test
    public void testTipoNoValido() {  // UP 2.14
        Descuento descuentoTipoNoValido = new Descuento();
        descuentoTipoNoValido.discountName = "Nombre";
        descuentoTipoNoValido.company = "ALSA";
        descuentoTipoNoValido.discountType = "otro";
        descuentoTipoNoValido.quantityDiscount = 50.0;
        descuentoTipoNoValido.expiranceDate = "20/11/2024";

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO);
        sut.onRegisterDiscountClicked(descuentoTipoNoValido);
        verify(mockDescuentoDAO, times(0)).insertAll(descuentoTipoNoValido);



    }

    @Test
    public void testDescuentoBBDD() {  // UP 2.15
        Descuento descuentoFalloBBDD = new Descuento();
        descuentoFalloBBDD.discountName = "Nombre5";
        descuentoFalloBBDD.company = "ALSA";
        descuentoFalloBBDD.discountType = "€/l,";
        descuentoFalloBBDD.quantityDiscount = 50.0;
        descuentoFalloBBDD.expiranceDate = "20/11/2024";

        sut.init(mockView);
        when(mockView.getDescuentoDAO()).thenReturn(mockDescuentoDAO2);
        sut.onRegisterDiscountClicked(descuentoFalloBBDD);
        verify(mockDescuentoDAO2, times(0)).insertAll(descuentoFalloBBDD);

    }



}
