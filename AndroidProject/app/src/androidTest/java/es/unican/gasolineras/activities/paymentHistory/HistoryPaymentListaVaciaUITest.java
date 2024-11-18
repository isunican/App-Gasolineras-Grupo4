package es.unican.gasolineras.activities.paymentHistory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.os.SystemClock;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.R;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.model.Pago;
import es.unican.gasolineras.repository.AppDatabasePayments;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.repository.IPagoDAO;

/** TEST REALIZADO POR
 * ALEJANDRO ACEBO**/
@UninstallModules(RepositoriesModule  .class)
@HiltAndroidTest
public class HistoryPaymentListaVaciaUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<PaymentHistoryView> activityRule = new ActivityScenarioRule<>(PaymentHistoryView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @BindValue
    final IGasolinerasRepository repository = getTestRepository(context, R.raw.gasolineras_ccaa_06);

    private AppDatabasePayments db;
    private IPagoDAO pagoDAO;

    @Before
    public void setUp() {
        // Create the database
        db = Room.databaseBuilder(context,
                        AppDatabasePayments.class, "payments")
                .allowMainThreadQueries()
                .build();
        pagoDAO = db.pagoDAO();
        pagoDAO.vaciaBD();
    }

    /**
     * This test checks if the list of payments is empty.
     */
    @Test
    public void showHistoryPaymentsNoPaymentsTest() {

        pressBackUnconditionally();
        activityRule.getScenario().close();
        ActivityScenario.launch(PaymentHistoryView.class);
        // Checks that the list of payments is empty because 0 childs and it is visible to check
        onView(withId(R.id.lvPagos)).check(matches(isDisplayed())).check(matches(hasChildCount(0)));
        SystemClock.sleep(5000);

    }
}
