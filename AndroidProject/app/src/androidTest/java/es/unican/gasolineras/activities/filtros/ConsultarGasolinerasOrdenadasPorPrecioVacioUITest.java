package es.unican.gasolineras.activities.filtros;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;

import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.R;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
public class ConsultarGasolinerasOrdenadasPorPrecioVacioUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<FiltrosView> activityRule = new ActivityScenarioRule<>(FiltrosView.class);

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private List<Gasolinera> gasolineras1;

    @BindValue
    IGasolinerasRepository repository;

    @Before
    public void inicializa() {
        // Inicializar las gasolineras vacias
        repository = getTestRepository(context, gasolineras1);
    }

    @Test
    public void orderVacioTest() {
        //Prueba sin ordenar

        onView(withId(R.id.rbAscendente)).perform(click());
        onView(withId(R.id.btnConfirmar)).perform(click());
        //Cambio de interfaz
        //Comprobar el numero de gasolineras
        //onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(hasChildCount(0)));
    }

}
