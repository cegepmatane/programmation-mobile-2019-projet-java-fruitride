package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.modele.Fruit;

public class AffichagePhotosFruit extends AppCompatActivity {
    protected int idFruitParametres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_photos_fruit);
        Bundle parametres = getIntent().getExtras();
        idFruitParametres = (int) parametres.get(Fruit.CLE_ID_FRUIT);

        Log.d("TOUDOUM", idFruitParametres + "--");


    }
}
