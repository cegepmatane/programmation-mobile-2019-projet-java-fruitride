package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.NotificationService;
import ca.qc.cgmatane.fruitride.donnee.ActiviteDAO;
import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.Activite;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class Accueil extends AppCompatActivity implements SensorEventListener {

    protected boolean doubleTap = false;
    protected boolean premiereOuverture = true;
    protected boolean running;

    protected SensorManager sensorManager;
    protected Sensor countSensor;

    protected TextView nbPas;
    protected TextView vueAccueilNomUtilisateur;
    protected TextView vueAccueilNiveauUtilisateur;
    protected ProgressBar vueAccueilBarreDeNiveau;

    protected float etatSensor;
    protected float etatSensorDemarage;

    protected UtilisateurDAO accesseurUtilisateur;
    protected Utilisateur utilisateur;

    protected ActiviteDAO accesseurActivite;
    protected Activite activite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        BaseDeDonnee.getInstance(getApplicationContext());

        stopService(new Intent( this, NotificationService. class));

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setUtilisateur();
        setActivite();

        setProgressBar();

        afficherUtilisateur();

        setListener();

        nbPas = (TextView)findViewById(R.id.vue_score_label_nombre_de_pas);
        nbPas.setText(Float.toString(activite.getNombreDePas()));

        vueAccueilNiveauUtilisateur = (TextView)findViewById(R.id.vue_score_label_niveau_joueur);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }

    public void setProgressBar() {
        vueAccueilBarreDeNiveau = (ProgressBar)findViewById(R.id.vue_score_barre_de_niveau);
        vueAccueilBarreDeNiveau.setProgress(utilisateur.getExperience() % 100);
        vueAccueilBarreDeNiveau.setScaleY(5f);
        vueAccueilBarreDeNiveau.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    }

    public void setUtilisateur() {
        accesseurUtilisateur = UtilisateurDAO.getInstance();
        utilisateur = accesseurUtilisateur.recupererUtilisateur();
    }

    public void setActivite() {
        accesseurActivite = ActiviteDAO.getInstance();
        accesseurActivite.isActiviteAjourdhui(new Activite(Calendar.getInstance(),0,
                0,utilisateur.getId_utilisateur()));
        activite = accesseurActivite.recupererActivite();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setListener() {
        final Intent intentionNaviguerVueConfiguration = new Intent(this, VueConfiguration.class);
        final Intent intentionNaviguerVueStatistiques = new Intent(this, VueStatistique.class);
        final Intent intentionNaviguerVueCarte = new Intent(this, VueCarte.class);


        ConstraintLayout monLayout = (ConstraintLayout) findViewById(R.id.layout);

        monLayout.setOnTouchListener(new ListenerSwipe(Accueil.this) {
            public void onSwipeRight() {
                startActivity(intentionNaviguerVueCarte);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
            public void onSwipeLeft() {
                startActivity(intentionNaviguerVueConfiguration);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        findViewById(R.id.vue_score_label_nombre_de_pas).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (doubleTap) {
                    startActivity(intentionNaviguerVueStatistiques);
                } else {
                    doubleTap = true;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleTap = false;
                        }
                    }, 500);
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Sensor non trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {
            if (premiereOuverture) {
                etatSensorDemarage = sensorEvent.values[0];
                etatSensor = sensorEvent.values[0];
                premiereOuverture = false;
            }
            float nombreDePas = (sensorEvent.values[0] - etatSensorDemarage) + activite.getNombreDePas();
            nbPas.setText(String.valueOf(nombreDePas));
            int experience = Math.round(utilisateur.getExperience() + (sensorEvent.values[0] - etatSensor));
            utilisateur.setExperience(experience);
            utilisateur.setNiveau(experience / 100);
            System.out.println(utilisateur.getExperience() + " XP");
            System.out.println((sensorEvent.values[0] - etatSensor) + " ETAT");
            vueAccueilNiveauUtilisateur.setText("Niveau : " + utilisateur.getNiveau());
            vueAccueilBarreDeNiveau.setProgress(utilisateur.getExperience() % 100);
            etatSensor = sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService( new Intent( this, NotificationService. class ));
        accesseurActivite.enregistrerNombreDePas((etatSensor - etatSensorDemarage) + activite.getNombreDePas());
        accesseurUtilisateur.enregistrerExperience(utilisateur);
    }

    @SuppressLint("SetTextI18n")
    protected void afficherUtilisateur() {
        vueAccueilNomUtilisateur = (TextView) findViewById(R.id.vue_score_label_nom_joueur);
        vueAccueilNomUtilisateur.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
    }
}
