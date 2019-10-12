package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.vue.VueCarte;

public class ControleurCarte implements Controleur {

    private VueCarte vue;

    public ControleurCarte(VueCarte vue) {
        this.vue = vue;
    }

    public void actionNaviguerAccueil() {
        vue.naviguerAccueil();
    }

    @Override
    public void onCreate(Context applicationContext) {

        BaseDeDonnee.getInstance(applicationContext);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int activite) {
    }
}
