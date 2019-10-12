package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.Gesture.ListenerSwipe;
import ca.qc.cgmatane.fruitride.controleur.ControleurAccueil;
import ca.qc.cgmatane.fruitride.controleur.ControleurConfiguration;

public class Configuration extends AppCompatActivity implements VueConfiguration {

    protected ControleurConfiguration controleurConfiguration = new ControleurConfiguration(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_configuration);

        findViewById(R.id.layout).setOnTouchListener(new ListenerSwipe(Configuration.this) {
            public void onSwipeRight() {
                controleurConfiguration.actionNaviguerAccueil();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
            public void onSwipeLeft() {
            }
        });
    }

    public void naviguerAccueil() {
        this.finish();
    }
}
