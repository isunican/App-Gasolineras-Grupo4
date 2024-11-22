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
public class ConsultarGasolinerasOrdenadasPorPrecioExitoUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<FiltrosView> activityRule = new ActivityScenarioRule<>(FiltrosView.class);

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private List<Gasolinera> gasolineras1;
    private Gasolinera g1;
    private Gasolinera g2;
    private Gasolinera g3;

    @BindValue
    IGasolinerasRepository repository;

    @Before
    public void inicializa() {
        // Inicializar las gasolineras
        g1 = new Gasolinera();
        g1.setId("g1");
        g1.setRotulo("GasolineraA");
        g1.setPrecioProducto(1);

        g2 = new Gasolinera();
        g2.setId("g2");
        g2.setRotulo("GasolineraB");
        g2.setPrecioProducto(3);

        g3 = new Gasolinera();
        g3.setId("g3");
        g3.setRotulo("GasolineraC");
        g3.setPrecioProducto(2);

        // Inicializar la lista de gasolineras
        gasolineras1 = new ArrayList<>();
        gasolineras1.add(g1);
        gasolineras1.add(g2);
        gasolineras1.add(g3);

        repository = getTestRepository(context, gasolineras1);

    }

    @Test
    public void ordenarExitoTest() {
        String[] opciones = context.getResources().getStringArray(R.array.opciones_filtro_combustible);

        //Prueba GLP sin ordenar
        String itemSeleccionado = opciones[0];
        onView(withId(R.id.spinnerCombustible)).perform(click());
        onView(withText(itemSeleccionado)).perform(click());
        onView(withId(R.id.btnConfirmar)).perform(click());

        //Cambio de interfaz
        //Comprobar el numero de gasolineras
        onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(hasChildCount(3)));
        //Primer elemento
        DataInteraction elementoLista1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraA")));
        elementoLista1.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("1.000")));
        elementoLista1.onChildView(withId(R.id.tvCombustible)).check(matches(withText("BIODIESEL:")));
        //Segundo elemento
        DataInteraction elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraB")));
        elementoLista2.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("3.000")));
        //Tercer elemento
        DataInteraction elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraC")));
        elementoLista3.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("2.000")));


        //Volver a la vista principal y acceder de nuevo a los filtros
        onView(withId(R.id.menuItemEmbudo)).perform(click());

        //Prueba GLP descendente
        itemSeleccionado = opciones[0];
        onView(withId(R.id.spinnerCombustible)).perform(click());
        onView(withText(itemSeleccionado)).perform(click());
        onView(withId(R.id.rbDescendente)).perform(click());
        onView(withId(R.id.btnConfirmar)).perform(click());

        //Cambio de interfaz
        //Comprobar el numero de gasolineras
        onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(hasChildCount(3)));
        //Primer elemento
        elementoLista1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraB")));
        elementoLista1.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("3.000")));
        elementoLista1.onChildView(withId(R.id.tvCombustible)).check(matches(withText("BIODIESEL:")));
        //Segundo elemento
        elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraC")));
        elementoLista2.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("2.000")));
        //Tercer elemento
        elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraA")));
        elementoLista3.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("1.000")));


        //Volver a la vista principal y acceder de nuevo a los filtros
        onView(withId(R.id.menuItemEmbudo)).perform(click());

        //Prueba bioetanol ascendente
        itemSeleccionado = opciones[4];
        onView(withId(R.id.spinnerCombustible)).perform(click());
        onView(withText(itemSeleccionado)).perform(click());
        onView(withId(R.id.rbAscendente)).perform(click());
        onView(withId(R.id.btnConfirmar)).perform(click());

        //Cambio de interfaz
        //Comprobar el numero de gasolineras
        onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(hasChildCount(3)));
        //Primer elemento
        elementoLista1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraA")));
        elementoLista1.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("1.000")));
        elementoLista1.onChildView(withId(R.id.tvCombustible)).check(matches(withText("GASES_LICUADOS_DEL_PETROLEO:")));
        //Segundo elemento
        elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraC")));
        elementoLista2.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("2.000")));
        //Tercer elemento
        elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("GasolineraB")));
        elementoLista3.onChildView(withId(R.id.tvPrecioCombustible)).check(matches(withText("3.000")));

    }


}

