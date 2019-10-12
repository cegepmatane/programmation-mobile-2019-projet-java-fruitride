package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;

import ca.qc.cgmatane.fruitride.vue.VueAffichagePhotoPrise;

public class ControleurAffichagePhotoPrise implements Controleur {

    private VueAffichagePhotoPrise vue;

    private String cheminImage;

    public ControleurAffichagePhotoPrise(VueAffichagePhotoPrise vue) {
        this.vue = vue;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }

    @Override
    public void onCreate(Context applicationContext) {

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
