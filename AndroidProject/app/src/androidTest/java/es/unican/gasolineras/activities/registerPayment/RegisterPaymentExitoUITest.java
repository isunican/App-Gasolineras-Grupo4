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
public class RegisterPaymentExitoUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<RegisterPaymentView> activityRule = new ActivityScenarioRule<>(RegisterPaymentView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    @Test
    public void registerPaymentTest() {

        //Compruebo caso de exito
        onView(withId(R.id.etNombreGasolinera)).perform(typeText("Gasolinera Arrandel"), closeSoftKeyboard());
        onView(withId(R.id.editTextNumberDecimal)).perform(typeText("1.54"), closeSoftKeyboard());
        onView(withId(R.id.editTextNumberDecimal2)).perform(typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.btnRegistrarPago)).perform(click());
        onView(withText(R.string.title_succes_reg_pay)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText(R.string.succes_reg_pay)).inRoot(isDialog()).check(matches(isDisplayed()));
    }



}
