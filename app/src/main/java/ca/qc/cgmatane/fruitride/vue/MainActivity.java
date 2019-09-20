package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ca.qc.cgmatane.fruitride.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Button boutonStatistique = findViewById(R.id.boutonStatistique);
        final Intent intent2 = new Intent(this, VueStatistique.class);
        boutonStatistique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });
    }
}
