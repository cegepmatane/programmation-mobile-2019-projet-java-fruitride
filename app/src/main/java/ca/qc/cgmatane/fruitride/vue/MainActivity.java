package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import ca.qc.cgmatane.fruitride.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView barDeNiveau = (ImageView) findViewById(R.id.barDeNiveau);

        int imageRessource = getResources().getIdentifier("@drawable/medium", null, this.getPackageName());
        barDeNiveau.setImageResource(imageRessource);
    }
}
