package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import ca.qc.cgmatane.fruitride.R;

public class AffichagePhotoPrise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_photo_prise);
        Bundle b = getIntent().getExtras();
        Log.d("LALA", b.getString("imagePath"));
    }
}
