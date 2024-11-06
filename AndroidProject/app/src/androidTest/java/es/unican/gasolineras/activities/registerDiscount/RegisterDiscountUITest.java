package es.unican.gasolineras.activities.registerDiscount;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import es.unican.gasolineras.R;


@HiltAndroidTest
public class RegisterDiscountUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<RegisterDiscountView> activityRule = new ActivityScenarioRule<>(RegisterDiscountView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    @Test
    public void registerDiscountTest() {


        //Compruebo caso de error falta nombre de descuento
        onView(ViewMatchers.withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("El nombre no puede estar vacío")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo caso de error falta marca
        onView(withId(R.id.etName)).perform(typeText("Descuento4"), closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("La compañía no puede estar vacía" )).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo el caso de error el tipo de descuento no puede estar vacio
        onView(withId(R.id.etName)).perform(typeText("Descuento5"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("El tipo de descuento no puede estar vacío")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo el caso de error cantidad vacia
        onView(withId(R.id.etName)).perform(typeText("Descuento5"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("La cantidad no puede estar vacía")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());


        //Compruebo el caso de error fecha de expiracion vacia
        onView(withId(R.id.etName)).perform(typeText("Descuento6"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("La fecha de expiración no puede estar vacía" )).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());


        //Compruebo el caso de error nombre repetido
        onView(withId(R.id.etName)).perform(typeText("Descuento1"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("50"), closeSoftKeyboard());

        onView(withId(R.id.tvExpiranceDate)).perform(click());
        onView(withText("20")).perform(click());

        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("No se puede crear el descuento porque ya existe uno con el mismo nombre")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());


        //Compruebo el caso de error cantidad menor que 0
        onView(withId(R.id.etName)).perform(typeText("Descuento8"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.rbFixed)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.tvExpiranceDate)).perform(click());
        onView(withText("20")).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("La cantidad fija debe ser mayor que 0" )).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo el caso de error cantidad menor que 0
        onView(withId(R.id.etName)).perform(typeText("Descuento9"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("200"), closeSoftKeyboard());
        onView(withId(R.id.tvExpiranceDate)).perform(click());
        onView(withText("20")).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("El porcentaje debe estar entre 0 y 100" )).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo el caso de error fecha de expiracion anterior a actual
        onView(withId(R.id.etName)).perform(typeText("Descuento10"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.tvExpiranceDate)).perform(click());
        onView(withText("1")).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("La fecha de expiración no puede ser anterior a la fecha actual")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());


        //Compruebo el caso cancelar registro
        onView(ViewMatchers.withId(R.id.btnCancel)).perform(click());

        //Prueba acierto
        onView(withId(R.id.etName)).perform(typeText("Descuento1"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Alsa")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.tvExpiranceDate)).perform(click());
        onView(withText("20")).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText(R.string.succes_reg_discount)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("El descuento se ha creado de manera correcta")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Prueba acierto 2
        onView(withId(R.id.etName)).perform(typeText("Descuento2"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("Repsol")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.tvExpiranceDate)).perform(click());
        onView(withText("30")).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText(R.string.succes_reg_discount)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("El descuento se ha creado de manera correcta")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());



        //Comprobacion caso de erro fallo de la base de datos
        /*
         * Este caso de error no es posible implementarlo, puesto que no se estan tratando los fallos de la base de datos
         * al no tratarse no se lanza ningun tipo de mensaje de error al suceder un hipotetico error en la base de datos.
         * */


    }



}
