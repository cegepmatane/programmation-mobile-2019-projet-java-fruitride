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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setUtilisateur();
        setActivite();

        setProgressBar();

        afficherUtilisateur();

        setListener();

        nbPas = (TextView)findViewById(R.id.vue_score_label_nombre_de_pas);
        nbPas.setText(Float.toString(activite.getNombreDePas()));
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
        final Intent intentionNaviguerVueAjoutUtilisateur = new Intent(this, AjoutUtilisateur.class);
        final Intent intentionNaviguerVueStatistiques = new Intent(this, VueStatistique.class);
        final Intent intentionNaviguerVueCarte = new Intent(this, VueCarte.class);

        Button bouton = findViewById(R.id.button);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentionNaviguerVueAjoutUtilisateur);
            }
        });

        ConstraintLayout monLayout = (ConstraintLayout) findViewById(R.id.layout);

        monLayout.setOnTouchListener(new ListenerSwipe(Accueil.this) {
            public void onSwipeRight() {
                startActivity(intentionNaviguerVueCarte);
            }
            public void onSwipeLeft() {
                startActivity(intentionNaviguerVueConfiguration);
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
                premiereOuverture = false;
            }
            nbPas.setText(String.valueOf((sensorEvent.values[0] - etatSensorDemarage) + activite.getNombreDePas()));
            etatSensor = sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accesseurActivite.enregistrerNombreDePas((etatSensor - etatSensorDemarage) + activite.getNombreDePas());
    }

    @SuppressLint("SetTextI18n")
    protected void afficherUtilisateur() {
        vueAccueilNomUtilisateur = (TextView) findViewById(R.id.vue_score_label_nom_joueur);
        vueAccueilNomUtilisateur.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
    }
}
