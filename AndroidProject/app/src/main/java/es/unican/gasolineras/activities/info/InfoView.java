package es.unican.gasolineras.activities.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.combustible.CombustibleView;

/**
 * View that shows application general information. Since this view does not have business logic,
 * it can be implemented as an activity directly, without the MVP pattern.
 */
public class InfoView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_view);

        }
    }
