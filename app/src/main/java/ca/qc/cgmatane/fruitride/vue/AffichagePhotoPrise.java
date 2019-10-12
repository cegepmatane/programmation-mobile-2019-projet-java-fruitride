package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.ControleurAffichagePhotoPrise;
import ca.qc.cgmatane.fruitride.controleur.ControleurCarte;

public class AffichagePhotoPrise extends AppCompatActivity implements VueAffichagePhotoPrise{

    protected ControleurAffichagePhotoPrise controleurAffichagePhotoPrise = new ControleurAffichagePhotoPrise(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_photo_prise);
        Bundle bundle = getIntent().getExtras();
        controleurAffichagePhotoPrise.setCheminImage(
                bundle.getString(ControleurCarte.ID_CHEMIN_IMAGE));
    }
}
