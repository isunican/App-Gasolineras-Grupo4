package es.unican.gasolineras.activities.filtros;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;

public class FiltrosView extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_gasolinera_view);

        TextView tvSeleccioneCombustible = findViewById(R.id.tvSeleccioneCombustible);
        TextView tvOrdenarPorPrecio = findViewById(R.id.tvOrdenarPorPrecio);
        Spinner spinner = findViewById(R.id.spinnerCombustible);
        RadioButton rbAscendente = findViewById(R.id.rbAscendente);
        RadioButton rbDescendente = findViewById(R.id.rbDescendente);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

    }
}
