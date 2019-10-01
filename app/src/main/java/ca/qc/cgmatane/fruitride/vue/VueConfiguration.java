package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;

public class VueConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_configuration);

        findViewById(R.id.layout).setOnTouchListener(new ListenerSwipe(VueConfiguration.this) {
            public void onSwipeRight() {
                intentionNaviguerVuePrincipale();
            }
            public void onSwipeLeft() {
            }
        });
    }

    protected void intentionNaviguerVuePrincipale() {
        this.finish();
    }
}
