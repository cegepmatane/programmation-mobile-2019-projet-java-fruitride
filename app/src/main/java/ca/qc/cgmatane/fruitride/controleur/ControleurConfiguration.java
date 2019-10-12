package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;

import ca.qc.cgmatane.fruitride.vue.VueConfiguration;

public class ControleurConfiguration implements Controleur{

    private VueConfiguration vue;

    public ControleurConfiguration(VueConfiguration vue) {
        this.vue = vue;
    }

    public void actionNaviguerAccueil() {
        vue.naviguerAccueil();
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
