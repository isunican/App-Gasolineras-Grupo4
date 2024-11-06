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
import android.view.View;
import android.widget.DatePicker;

import androidx.room.Room;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabaseDiscount;
import es.unican.gasolineras.repository.AppDatabasePayments;
import es.unican.gasolineras.repository.IDescuentoDAO;
import es.unican.gasolineras.repository.IPagoDAO;


@HiltAndroidTest
public class RegisterDiscountErrorNombreRepetidoUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<RegisterDiscountView> activityRule = new ActivityScenarioRule<>(RegisterDiscountView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    private AppDatabaseDiscount db;
    private IDescuentoDAO descuentosDAO;



    @Before
    public void setUp(){
        // Create the database
        db = Room.databaseBuilder(context,
                        AppDatabaseDiscount.class, "discounts")
                .allowMainThreadQueries()
                .build();
        descuentosDAO = db.descuentosDAO();
    }




    @Test
    public void errorNombreRepetido(){
        //Inserto descuentos en la base de datos
        Descuento descuento1 = new Descuento();
        descuento1.discountName = "Des1";
        descuento1.company = "REPSOL";
        descuento1.discountType = "FIXED";
        descuento1.quantityDiscount = 0.1;
        descuento1.expiranceDate = "2024-11-23";
        descuento1.discountActive = true;
        descuentosDAO.insertAll(descuento1);

        Descuento descuento2 = new Descuento();
        descuento2.discountName = "Des2";
        descuento2.company = "CEPSA";
        descuento2.discountType = "PERCENTAGE";
        descuento2.quantityDiscount = 20.0;
        descuento2.expiranceDate = "2026-11-20";
        descuento2.discountActive = false;
        descuentosDAO.insertAll(descuento2);

        Descuento descuento3 = new Descuento();
        descuento3.discountName = "Des3";
        descuento3.company = "BP";
        descuento3.discountType = "FIXED";
        descuento3.quantityDiscount = 0.2;
        descuento3.expiranceDate = "2027-10-21";
        descuento3.discountActive = false;
        descuentosDAO.insertAll(descuento3);

        //Compruebo el caso de error nombre repetido
        onView(withId(R.id.etName)).perform(typeText("Des1"), closeSoftKeyboard());
        onView(withId(R.id.spnCompany)).perform(click());
        onView(withText("ALSA")).perform(click());
        onView(withId(R.id.rbPercentaje)).perform(click());
        onView(withId(R.id.etQuantity)).perform(typeText("50"), closeSoftKeyboard());

        onView(withId(R.id.tvExpiranceDate)).check(matches(isDisplayed())).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2024, 11, 20));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.btnCreate)).perform(click());
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("No se puede crear el descuento porque ya existe uno con el mismo nombre")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
    }
}
