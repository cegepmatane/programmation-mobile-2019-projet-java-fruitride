package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ca.qc.cgmatane.fruitride.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView barreDeNiveau = (ImageView) findViewById(R.id.barre_de_niveau);

        int imageRessource = getResources().getIdentifier("@drawable/medium", null, this.getPackageName());
        barreDeNiveau.setImageResource(imageRessource);

        Button bouton = findViewById(R.id.button);

        final Intent testCarte = new Intent(this, VueCarte.class);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(testCarte);
            }
        });
    }
}
