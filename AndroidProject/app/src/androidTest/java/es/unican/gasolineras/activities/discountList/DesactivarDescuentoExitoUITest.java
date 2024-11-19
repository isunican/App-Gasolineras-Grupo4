package es.unican.gasolineras.activities.discountList;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.os.SystemClock;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.DataInteraction;
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
import es.unican.gasolineras.model.Descuento;
import es.unican.gasolineras.repository.AppDatabaseDiscount;
import es.unican.gasolineras.repository.IDescuentoDAO;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
public class DesactivarDescuentoExitoUITest {

    @Rule(order = 0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<DiscountListView> activityRule = new ActivityScenarioRule<>(DiscountListView.class);

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @BindValue
    final IGasolinerasRepository repository = getTestRepository(context, R.raw.gasolineras_ccaa_06);

    private AppDatabaseDiscount db;
    private IDescuentoDAO descuentosDAO;
    private Descuento d1 = new Descuento();
    private Descuento d2 = new Descuento();
    private Descuento d3 = new Descuento();

    @Before
    public void setUp(){
        // Create the database
        db = Room.databaseBuilder(context,
                        AppDatabaseDiscount.class, "discounts")
                .allowMainThreadQueries()
                .build();
        descuentosDAO = db.descuentosDAO();
        descuentosDAO.vaciaBD();

    }

    @Test
    public void desactivarDescuentoExito() {
        d1.setDiscountName("Descuento1");
        d2.setDiscountName("Descuento2");
        d3.setDiscountName("Descuento3");
        d1.setCompany("Repsol");
        d2.setCompany("Cepsa");
        d3.setCompany("BP");
        d1.setDiscountType("Fijo");
        d2.setDiscountType("Variable");
        d3.setDiscountType("Fijo");
        d1.setQuantityDiscount(0.1);
        d2.setQuantityDiscount(20.0);
        d3.setQuantityDiscount(0.2);
        d1.setExpiranceDate("23/11/2024");
        d2.setExpiranceDate("20/08/2026");
        d3.setExpiranceDate("21/10/2027");
        d1.setDiscountActive(true);
        d2.setDiscountActive(false);
        d3.setDiscountActive(false);

        descuentosDAO.insertAll(d1);
        descuentosDAO.insertAll(d2);
        descuentosDAO.insertAll(d3);

        pressBackUnconditionally();
        activityRule.getScenario().close();
        ActivityScenario.launch(DiscountListView.class);

        SystemClock.sleep(5000);

        DataInteraction descuento1 = onData(anything()).inAdapterView(withId(R.id.lvDiscounts)).atPosition(0);

        descuento1.onChildView(withId(R.id.chkActive)).check(matches(isChecked()));

        descuento1.onChildView(withId(R.id.chkActive)).perform(click());
        descuento1.onChildView(withId(R.id.chkActive)).check(matches(isNotChecked()));

    }
}
