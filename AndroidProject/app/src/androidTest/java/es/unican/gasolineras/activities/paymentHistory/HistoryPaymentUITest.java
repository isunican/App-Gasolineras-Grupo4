package es.unican.gasolineras.activities.paymentHistory;
import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.*;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.CoreMatchers.anything;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/** TEST REALIZADO POR
 * ALEJANDRO ACEBO**/
@UninstallModules(RepositoriesModule  .class)
@HiltAndroidTest
public class HistoryPaymentUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<PaymentHistoryView> activityRule = new ActivityScenarioRule<>(PaymentHistoryView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @BindValue
    final IGasolinerasRepository repository = getTestRepository(context, R.raw.gasolineras_ccaa_06);

    private AppDatabase db;
    private IPagoDAO pagoDAO;
    private Pago p1,p2,p3,p4;
    private View decorView;

    @Before
    public void setUp(){
        // Create the database
        db = Room.databaseBuilder(context,
                        AppDatabase.class, "payments")
                .allowMainThreadQueries()
                .build();
        pagoDAO = db.pagoDAO();
    }

    /**
     * This test checks if the list of payments is empty.
     */
    @Test
    public void showHistoryPaymentsNoPaymentsTest() {

        // Checks that the list of payments is empty because 0 childs and it is visible to check
        onView(withId(R.id.lvPagos)).check(matches(isDisplayed())).check(matches(hasChildCount(0)));
        SystemClock.sleep(5000);

    }

    /**
     * This test checks if the shown view has all the payments that we previously introduce in the database.
     */
    @Test
    public void showHistoryPaymentsWithPaymentsTest(){
        // Insert of payments
        // First payment
        p1 = new Pago();
        p1.setStationName("Gasolinera Arrandel");
        p1.setDate(LocalDate.of(2024, 9, 16).toString());
        p1.setFuelType("Bioetanol");
        p1.setQuantity(20.0);
        p1.setPricePerLitre(1.24);
        p1.setFinalPrice(24.8);
        pagoDAO.insertAll(p1);

        // Second payment
        p2 = new Pago();
        p2.setStationName("Gasolinera Repsol Tapuerta");
        p2.setDate(LocalDate.of(2024, 9, 20).toString());
        p2.setFuelType("GLP");
        p2.setQuantity(25.0);
        p2.setPricePerLitre(1.45);
        p2.setFinalPrice(36.25);
        pagoDAO.insertAll(p2);

        // Third payment
        p3 = new Pago();
        p3.setStationName("Gasolinera cepsa Liencres");
        p3.setDate(LocalDate.of(2024, 9, 25).toString());
        p3.setFuelType("GLP");
        p3.setQuantity(23.0);
        p3.setPricePerLitre(1.38);
        p3.setFinalPrice(31.74);
        pagoDAO.insertAll(p3);

        // Forth payment
        p4 = new Pago();
        p4.setStationName("Gasolinera cepsa Laredo");
        p4.setDate(LocalDate.of(2024, 10, 1).toString());
        p4.setFuelType("Biodiesel");
        p4.setQuantity(34.0);
        p4.setPricePerLitre(1.42);
        p4.setFinalPrice(48.28);
        pagoDAO.insertAll(p4);

        // Need it for update the view after insert the payments
        pressBackUnconditionally();
        activityRule.getScenario().close();
        ActivityScenario.launch(PaymentHistoryView.class);

        // Wait for 5 seconds
        SystemClock.sleep(5000);

        // Check if the listView has 4 elements
        onView(withId(R.id.lvPagos)).check(matches(isDisplayed())).check(matches(hasChildCount(4)));

        // The payments will be shown order by the date, so it is not shown as we previously introduce
        // in the database

        // First payment check - Gasolinera cepsa Laredo
        DataInteraction pago1 = onData(anything()).inAdapterView(withId(R.id.lvPagos)).atPosition(0);
        pago1.onChildView(withId(R.id.Fecha)).check(matches(withText("Fecha: 2024-10-01")));
        pago1.onChildView(withId(R.id.Estacion)).check(matches(withText("Gasolinera cepsa Laredo")));
        pago1.onChildView(withId(R.id.Precio)).check(matches(withText("Precio: 1.42")));
        pago1.onChildView(withId(R.id.TipoCombustible)).check(matches(withText("Combustible: Biodiesel")));
        pago1.onChildView(withId(R.id.ImporteTotal)).check(matches(withText("Importe: 48.28")));
        pago1.onChildView(withId(R.id.Cantidad)).check(matches(withText("Cantidad: 34.0")));

        // Second payment check - Gasolinera cepsa Liencres
        DataInteraction pago2 = onData(anything()).inAdapterView(withId(R.id.lvPagos)).atPosition(1);
        pago2.onChildView(withId(R.id.Fecha)).check(matches(withText("Fecha: 2024-09-25")));
        pago2.onChildView(withId(R.id.Estacion)).check(matches(withText("Gasolinera cepsa Liencres")));
        pago2.onChildView(withId(R.id.Precio)).check(matches(withText("Precio: 1.38")));
        pago2.onChildView(withId(R.id.TipoCombustible)).check(matches(withText("Combustible: GLP")));
        pago2.onChildView(withId(R.id.ImporteTotal)).check(matches(withText("Importe: 31.74")));
        pago2.onChildView(withId(R.id.Cantidad)).check(matches(withText("Cantidad: 23.0")));

        // Third payment check - Gasolinera Repsol Tapuerta
        DataInteraction pago3 = onData(anything()).inAdapterView(withId(R.id.lvPagos)).atPosition(2);
        pago3.onChildView(withId(R.id.Fecha)).check(matches(withText("Fecha: 2024-09-20")));
        pago3.onChildView(withId(R.id.Estacion)).check(matches(withText("Gasolinera Repsol Tapuerta")));
        pago3.onChildView(withId(R.id.Precio)).check(matches(withText("Precio: 1.45")));
        pago3.onChildView(withId(R.id.TipoCombustible)).check(matches(withText("Combustible: GLP")));
        pago3.onChildView(withId(R.id.ImporteTotal)).check(matches(withText("Importe: 36.25")));
        pago3.onChildView(withId(R.id.Cantidad)).check(matches(withText("Cantidad: 25.0")));

        // Fourth payment check - Gasolinera Arrandel
        DataInteraction pago4 = onData(anything()).inAdapterView(withId(R.id.lvPagos)).atPosition(3);
        pago4.onChildView(withId(R.id.Fecha)).check(matches(withText("Fecha: 2024-09-16")));
        pago4.onChildView(withId(R.id.Estacion)).check(matches(withText("Gasolinera Arrandel")));
        pago4.onChildView(withId(R.id.Precio)).check(matches(withText("Precio: 1.24")));
        pago4.onChildView(withId(R.id.TipoCombustible)).check(matches(withText("Combustible: Bioetanol")));
        pago4.onChildView(withId(R.id.ImporteTotal)).check(matches(withText("Importe: 24.8")));
        pago4.onChildView(withId(R.id.Cantidad)).check(matches(withText("Cantidad: 20.0")));

    }
}
