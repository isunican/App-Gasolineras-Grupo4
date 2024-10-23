package es.unican.gasolineras.common;

import static es.unican.gasolineras.repository.GasolinerasService.deserializer;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

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

    public void showAlertDialog(String message, String title, Context context) {
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
}
