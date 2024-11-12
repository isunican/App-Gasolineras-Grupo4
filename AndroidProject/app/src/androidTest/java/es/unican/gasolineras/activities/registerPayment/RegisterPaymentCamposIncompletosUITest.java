package es.unican.gasolineras.activities.registerPayment;


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
public class RegisterPaymentCamposIncompletosUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<RegisterPaymentView> activityRule = new ActivityScenarioRule<>(RegisterPaymentView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    @Test
    public void registerPaymentTest() {
        //Compruebo caso de error falta nombre gasolinera
        onView(ViewMatchers.withId(R.id.btnRegistrarPago)).perform(click());
        onView(withText(R.string.titulo_error_nombre_gasolinera)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText(R.string.error_nombre_gasolinera)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo caso de error falta precio
        onView(withId(R.id.etNombreGasolinera)).perform(typeText("Gasolinera Arrandel"), closeSoftKeyboard());
        onView(withId(R.id.btnRegistrarPago)).perform(click());
        onView(withText(R.string.titulo_error_precio_por_litro)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText(R.string.error_precio_por_litro)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo el caso de error falta cantidad de combustible
        onView(withId(R.id.editTextNumberDecimal)).perform(typeText("1.54"), closeSoftKeyboard());
        onView(withId(R.id.btnRegistrarPago)).perform(click());
        onView(withText(R.string.titulo_error_cantidad_combustible)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText(R.string.error_cantidad_combustible)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Compruebo caso de error falta tipo de combustible
        /*
        * Este caso de error no es posible implementarlo, esto es debido a que el tipo de combustible se selecciona a traves
        * de un Spinner el  cual ya tiene todas las opciones bien seleccionadas.
        * */

        //Comprobacion caso de erro fallo de la base de datos
        /*
        * Este caso de error no es posible implementarlo, puesto que no se estan tratando los fallos de la base de datos
        * al no tratarse no se lanza ningun tipo de mensaje de error al suceder un hipotetico error en la base de datos.
        * */

    }



}
