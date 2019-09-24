package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;

public class MainActivity extends AppCompatActivity {

    boolean doubleTap = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test
        ProgressBar barre = (ProgressBar)findViewById(R.id.vue_score_barre_de_niveau);
        barre.setProgress(50);
        barre.setScaleY(5f);
        barre.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

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

        findViewById(R.id.layout).setOnTouchListener(new ListenerSwipe(MainActivity.this) {
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {
                startActivity(intent);
            }
        });
    }
}
