package es.unican.gasolineras.activities.analyticsView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabasePayments;
import es.unican.gasolineras.repository.IPagoDAO;


@HiltAndroidTest
public class ExitoGraficaGastoDiarioUITest{

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<AnalyticsViewView> activityRule = new ActivityScenarioRule<>(AnalyticsViewView.class);

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private AppDatabasePayments db;
    private IPagoDAO pagoDAO;
    private Pago p1,p2,p3,p4;

    @Before
    public void setUp(){
        // Create the database
        db = Room.databaseBuilder(context,
                        AppDatabasePayments.class, "payments")
                .allowMainThreadQueries()
                .build();
        pagoDAO = db.pagoDAO();
        pagoDAO.vaciaBD();
    }

    /**
     * A1.a caso exito donde se muestra la grafica de gasto diario
     */
    @Test
    public void exitoGraficaGastoDIarioTest() {

        //el estado inicial esta detallado en el plan de pruebas
        // First payment
        p1 = new Pago();
        p1.stationName = "Gasolinera Arrandel";
        p1.date = "2024-11-16";
        p1.fuelType = "Bioetanol";
        p1.quantity = 20.0;
        p1.pricePerLitre = 1.24;
        p1.finalPrice = 24.8;
        pagoDAO.insertAll(p1);

        // Second payment
        p2 = new Pago();
        p2.stationName = "Gasolinera Repsol Tapuerta";
        p2.date = "2024-11-20";
        p2.fuelType = "GLP";
        p2.quantity = 25.0;
        p2.pricePerLitre = 1.45;
        p2.finalPrice = 36.25;
        pagoDAO.insertAll(p2);

        // Third payment
        p3 = new Pago();
        p3.stationName = "Gasolinera Cepsa Liencres";
        p3.date = "2024-10-25";
        p3.fuelType = "GLP";
        p3.quantity = 23.0;
        p3.pricePerLitre = 1.38;
        p3.finalPrice = 31.74;
        pagoDAO.insertAll(p3);

        // Fourth payment
        p4 = new Pago();
        p4.stationName = "Gasolinera Cepsa Laredo";
        p4.date = "2024-10-01";
        p4.fuelType = "Biodiesel";
        p4.quantity = 34.0;
        p4.pricePerLitre = 1.42;
        p4.finalPrice = 48.28;
        pagoDAO.insertAll(p4);



        onView(withId(R.id.spnMonth)).perform(click());
        onView(withText("11")).perform(click());

        onView(withId(R.id.spnYear)).perform(click());
        onView(withText("2024")).perform(click());

        onView(withId(R.id.imgBtnTick)).perform(click());


        onView(withId(R.id.spnTypeGraphic)).perform(click());
        onView(withText("Gasto diario")).perform(click());

        onView(withId(R.id.tvPrecioCombustibleMedio)).check(matches(withText("Precio Combustible Medio: 1,345 €/L")));
        onView(withId(R.id.tvLitrosPromedio)).check(matches(withText("Litros Promedio: 22,50 L/Repostaje")));
        onView(withId(R.id.tvLitrosTotales)).check(matches(withText("Litros Totales: 45,00 L")));
        onView(withId(R.id.tvGastoTotal)).check(matches(withText("Gasto Total: 61,05 €")));

        onView(withId(R.id.frmGraphic)).check(matches(isDisplayed()));
    }

}
