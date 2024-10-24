package es.unican.gasolineras;

import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.activities.registerPayment.RegisterPaymentView;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
public class RegisterPaymentUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<RegisterPaymentView> activityRule = new ActivityScenarioRule<>(RegisterPaymentView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void registerPaymentTest() {
        onView(withId(R.id.etNombreGasolinera)).perform(typeText("Gasolinera Arrandel"));
        onView(withId(R.id.editTextNumberDecimal)).perform(typeText("1.54"));
        onView(withId(R.id.editTextNumberDecimal2)).perform(typeText("30"));
        String[] opciones = context.getResources().getStringArray(R.array.opcionesSpinner);
        String itemSeleccionado = opciones[4];
        onData(withText(itemSeleccionado)).inAdapterView(withId(R.id.spnTipoCombustible)).perform(click());

        onView(withId(R.id.btnRegistrarPago)).perform(click());
    }

}
