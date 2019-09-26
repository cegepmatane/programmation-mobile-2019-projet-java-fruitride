package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    protected boolean doubleTap = false;
    protected SensorManager sensorManager;
    protected boolean running = false;
    protected TextView nbPas;

    protected UtilisateurDAO accesseurUtilisateur;

    protected TextView vueAccueilNomUtilisateur;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setProgressBar();

        afficherUtilisateur();

        Button bouton = findViewById(R.id.button);
        final Intent intent = new Intent(this, VueCarte.class);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        final Intent intent2 = new Intent(this, VueStatistique.class);

        ConstraintLayout monLayout = (ConstraintLayout) findViewById(R.id.layout);

        monLayout.setOnTouchListener(new ListenerSwipe(MainActivity.this) {
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {
                startActivity(intent);
            }
        });

        monLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (doubleTap) {
                    startActivity(intent2);
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

        nbPas = (TextView)findViewById(R.id.vue_score_label_nombre_de_pas);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }

    public void setProgressBar() {
        ProgressBar barre = (ProgressBar)findViewById(R.id.vue_score_barre_de_niveau);
        barre.setProgress(50);
        barre.setScaleY(5f);
        barre.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor non trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {
            nbPas.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @SuppressLint("SetTextI18n")
    protected void afficherUtilisateur() {
        vueAccueilNomUtilisateur = (TextView) findViewById(R.id.vue_score_label_nom_joueur);

        accesseurUtilisateur = UtilisateurDAO.getInstance();
        accesseurUtilisateur.preparerListeUtilisateur();

        List<Utilisateur> listeUtilisateurs = accesseurUtilisateur.recupererListeUtilisateur();

        vueAccueilNomUtilisateur.setText(listeUtilisateurs.get(2).getNom() + " " + listeUtilisateurs.get(2).getPrenom());
    }
}
