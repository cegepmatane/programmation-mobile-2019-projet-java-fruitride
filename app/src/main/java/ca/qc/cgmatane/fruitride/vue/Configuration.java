package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;

public class Configuration extends AppCompatActivity implements VueConfiguration {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_configuration);

        findViewById(R.id.layout).setOnTouchListener(new ListenerSwipe(Configuration.this) {
            public void onSwipeRight() {
                intentionNaviguerVuePrincipale();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
            public void onSwipeLeft() {
            }
        });
    }

    public void intentionNaviguerVuePrincipale() {
        this.finish();
    }
}
