package es.unican.gasolineras.common;

import static es.unican.gasolineras.repository.GasolinerasService.deserializer;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.GasolinerasResponse;

/**
 * Utility methods that may be used by several classes
 */
public class Utils {

    /**
     * Parses a list of gas stations from a json resource file.
     * The json must contain a serialized GasolinerasResponse object.
     * It uses GSON to parse the json file
     * @param context the application context
     * @param jsonId the resource id of the json file
     * @return list of gas stations parsed from the file
     */
    public static List<Gasolinera> parseGasolineras(Context context, int jsonId) {
        InputStream is = context.getResources().openRawResource(jsonId);
        Type typeToken = new TypeToken<GasolinerasResponse>() { }.getType();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        GasolinerasResponse response = new GsonBuilder()
                .registerTypeAdapter(double.class, deserializer)
                .create()
                .fromJson(reader, typeToken);
        List<Gasolinera> gasolineras = response.getGasolineras();
        return gasolineras;
    }

    public static void showAlertDialog(String message, String title, Context context) {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
            }
        });

        // 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static Set<String> obtenerRotulosUnicos(InputStream jsonInputStream) {
        Set<String> rotulosUnicos = new HashSet<>();

        try {
            // Leer el archivo JSON desde el InputStream
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            // Convertir a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString.toString());
            JSONArray listaEESSPrecio = jsonObject.getJSONArray("ListaEESSPrecio");

            // Iterar por cada objeto en la lista
            for (int i = 0; i < listaEESSPrecio.length(); i++) {
                JSONObject estacion = listaEESSPrecio.getJSONObject(i);
                String rotulo = estacion.optString("Rótulo", "");
                if (!rotulo.isEmpty() && !rotulo.matches("\\d+")) {
                    rotulo = rotulo.trim();
                    if (rotulo.equals("(SIN RÓTULO)")) {
                        rotulo = "";
                    }
                    rotulosUnicos.add(rotulo);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return rotulosUnicos;
    }
}
