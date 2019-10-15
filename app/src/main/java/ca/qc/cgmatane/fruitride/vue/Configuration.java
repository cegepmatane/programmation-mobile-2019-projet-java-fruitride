package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.gesture.ListenerSwipe;
import ca.qc.cgmatane.fruitride.controleur.ControleurConfiguration;

public class Configuration extends AppCompatActivity implements VueConfiguration {

    protected ControleurConfiguration controleurConfiguration = new ControleurConfiguration(this);
    protected RadioGroup groupeRadioBoutons;
    protected RadioButton radioBouton;

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

        groupeRadioBoutons = findViewById(R.id.groupeRadioButtonChoixTheme);


    }

    /**
     * Cette fonction est appelée automatiquement dès que l'un des radioboutons est sélectionné.
     *
     * @param v la fonction est appelée depuis le XML, on a besoin de la vue.
     */
    public void verificationBouton(View v) {
        int idThemeActif = groupeRadioBoutons.getCheckedRadioButtonId();

        radioBouton = findViewById(idThemeActif);

        //Pour le débugg
        Toast.makeText(this, "Bouton choisi : " + radioBouton.getText(), Toast.LENGTH_SHORT).show();

    }

    public void naviguerAccueil() {
        this.finish();
    }
}
