package es.unican.gasolineras.activities.filtros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.combustible.CombustibleView;
import es.unican.gasolineras.model.TipoCombustible;

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

        btnConfirmar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CombustibleView.class);
            intent.putExtra("tipoCombustible", TipoCombustible.GASES_LICUADOS_DEL_PETROLEO.toString()); //Cambiar a valor del spiner
            startActivity(intent);
        });
    }

}
