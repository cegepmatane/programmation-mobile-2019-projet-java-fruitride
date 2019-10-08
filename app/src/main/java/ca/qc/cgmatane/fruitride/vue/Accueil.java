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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.NotificationService;
import ca.qc.cgmatane.fruitride.donnee.ActiviteDAO;
import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.LectureEcriture;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.Activite;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class Accueil extends AppCompatActivity implements SensorEventListener {

    protected final float EPAISSEUR_BARRE_DE_NIVEAU = 5f;
    protected final int NOMBRE_DE_PAS = 0;

    protected boolean doubleTouche = false;
    protected boolean premiereOuverture = true;
    protected boolean activationPodometre;

    protected SensorManager capteurNombreDePas;
    protected Sensor capteur;

    protected TextView vueAccueilNombreDePas;
    protected TextView vueAccueilNomUtilisateur;
    protected TextView vueAccueilNiveauUtilisateur;

    protected ProgressBar vueAccueilBarreDeNiveau;

    protected float etatPodometre;
    protected float etatPodometreAuDemarrage;

    protected UtilisateurDAO accesseurUtilisateur;
    protected Utilisateur utilisateur;

    protected ActiviteDAO accesseurActivite;
    protected Activite activite;

    // POUR LE TEST UNIQUEMENT
    private LectureEcriture lectureEcriture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        BaseDeDonnee.getInstance(getApplicationContext());

        //DESACTIVER L'ENVOI DE NOTIFICATION
        stopService(new Intent( this, NotificationService. class));

        //DESACTIVER LA ROTATION D'ECRAN
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //// ZONE DE TEST UNIQUEMENT ////
        lectureEcriture = new LectureEcriture(this);

        Log.d("TEST EXISTANCE DU FICHIER (nég au premier lauch, pos ap. : ","" + lectureEcriture.fichierPret(this));

        //On écrit qqch dans le fichier
        lectureEcriture.miseAjourXML(this);

        Log.d("TEST I/O","Thème clair : "+lectureEcriture.themeClair());
        Log.d("TEST I/O", "Notifs : "+lectureEcriture.notificationsActives());
        Log.d("TEST I/O", "Lieux à prox. : "+lectureEcriture.lieuxAproximiteActifs());

        //// FIN ////

        initialiserUtilisateur();
        initialiserActivite();

        initialiserBarreDeNiveau();

        afficherUtilisateur();

        setListener();

        vueAccueilNombreDePas = (TextView)findViewById(R.id.vue_score_label_nombre_de_pas);
        vueAccueilNombreDePas.setText(Float.toString(activite.getNombreDePas()));

        vueAccueilNiveauUtilisateur = (TextView)findViewById(R.id.vue_score_label_niveau_joueur);

        capteurNombreDePas = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }

    public void initialiserBarreDeNiveau() {

        vueAccueilBarreDeNiveau = (ProgressBar)findViewById(R.id.vue_score_barre_de_niveau);
        vueAccueilBarreDeNiveau.setProgress(utilisateur.getExperience() % 100);
        vueAccueilBarreDeNiveau.setScaleY(EPAISSEUR_BARRE_DE_NIVEAU);
        vueAccueilBarreDeNiveau.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    }

    public void initialiserUtilisateur() {

        accesseurUtilisateur = UtilisateurDAO.getInstance();
        utilisateur = accesseurUtilisateur.recupererUtilisateur();
    }

    public void initialiserActivite() {

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
                if (doubleTouche) {
                    startActivity(intentionNaviguerVueStatistiques);
                } else {
                    doubleTouche = true;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleTouche = false;
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
        activationPodometre = true;
        capteur = capteurNombreDePas.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (capteur != null) {
            capteurNombreDePas.registerListener(this, capteur, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Sensor non trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        activationPodometre = false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (activationPodometre) {

            float nombreDePasPodometre = sensorEvent.values[NOMBRE_DE_PAS];

            if (premiereOuverture) {
                etatPodometreAuDemarrage = nombreDePasPodometre;
                etatPodometre = nombreDePasPodometre;
                premiereOuverture = false;
            }
            float nombreDePas = (nombreDePasPodometre - etatPodometreAuDemarrage) + activite.getNombreDePas();
            int experience = Math.round(utilisateur.getExperience() + (nombreDePasPodometre - etatPodometre));

            vueAccueilNombreDePas.setText(String.valueOf(nombreDePas));

            utilisateur.setExperience(experience);
            utilisateur.setNiveau(experience / 100);

            vueAccueilNiveauUtilisateur.setText("Niveau : " + utilisateur.getNiveau());
            vueAccueilBarreDeNiveau.setProgress(utilisateur.getExperience() % 100);

            etatPodometre = nombreDePasPodometre;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onDestroy() {

        super.onDestroy();
        startService( new Intent( this, NotificationService. class ));
        accesseurActivite.enregistrerNombreDePas((etatPodometre - etatPodometreAuDemarrage) + activite.getNombreDePas());
        accesseurUtilisateur.enregistrerExperience(utilisateur);
    }

    @SuppressLint("SetTextI18n")
    protected void afficherUtilisateur() {

        vueAccueilNomUtilisateur = (TextView) findViewById(R.id.vue_score_label_nom_joueur);
        vueAccueilNomUtilisateur.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
    }
}
