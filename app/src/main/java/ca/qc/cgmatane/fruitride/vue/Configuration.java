package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.accestelephone.LectureEcriture;
import ca.qc.cgmatane.fruitride.gesture.ListenerSwipe;
import ca.qc.cgmatane.fruitride.controleur.ControleurConfiguration;

public class Configuration extends AppCompatActivity implements VueConfiguration {

    protected ControleurConfiguration controleurConfiguration = new ControleurConfiguration(this);
    protected RadioGroup groupeRadioBoutons;
    protected RadioButton radioBouton;
    protected Switch notifications, lieux;

    private LectureEcriture lectureEcriture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lectureEcriture = new LectureEcriture(this);

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

        notifications = (Switch) findViewById(R.id.boutonActiverNotification);
        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    lectureEcriture.activerNotification();
                }
                else {
                    lectureEcriture.desactiverNotification();
                }
                lectureEcriture.miseAjourXML(Configuration.this);
                Log.d("Contenu XML notifications ", ""+lectureEcriture.notificationsActives());
            }
        });

        lieux = (Switch) findViewById(R.id.boutonActiverLieuxAproximiter);
        lieux.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    lectureEcriture.activerLieuxAproximite();
                }
                else {
                    lectureEcriture.desactiverLieuxAproximite();
                }
                lectureEcriture.miseAjourXML(Configuration.this);
                Log.d("Contenu XML lieux à proximité ", ""+lectureEcriture.lieuxAproximiteActifs());
            }
        });

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
        //Toast.makeText(this, "Bouton choisi : " + radioBouton.getText(), Toast.LENGTH_SHORT).show();

        if (radioBouton.getText().equals("Sombre")) {
            lectureEcriture.choisirThemeSombre();
        } else if (radioBouton.getText().equals("Clair")) {
            lectureEcriture.choisirThemeClair();
        }

        lectureEcriture.miseAjourXML(this);

        //Pour le débug
        //Toast.makeText(this, "Contenu XML themeClair : " + lectureEcriture.themeClair(), Toast.LENGTH_SHORT).show();
        Log.d("Contenu XML themeClair ", ""+lectureEcriture.themeClair());

    }

    public void naviguerAccueil() {
        this.finish();
    }
}
