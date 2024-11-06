package es.unican.gasolineras.activities.registerDiscount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.content.Context;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import es.unican.gasolineras.R;
@HiltAndroidTest
public class RegisterDiscountErrorCantidadUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<RegisterDiscountView> activityRule = new ActivityScenarioRule<>(RegisterDiscountView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();



    @Test
    public void errorCantidad(){
        //Compruebo el caso de error cantidad vacia
        onView(withId(R.id.etName)).perform(typeText("Descuento5"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("ALSA")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("La cantidad no puede estar vacía")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
    }
}
