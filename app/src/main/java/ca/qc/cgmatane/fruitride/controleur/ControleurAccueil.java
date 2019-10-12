package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;
import android.content.Intent;

import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.vue.VueAccueil;

public class ControleurAccueil implements Controleur{

    private VueAccueil vue;
    private UtilisateurDAO accesseurUtilisateur;

    public ControleurAccueil(VueAccueil vue) {
        this.vue = vue;
    }

    public void actionNaviguerConfiguration() {
        vue.naviguerConfiguration();
    }

    public void actionNaviguerStatistique() {
        vue.naviguerStatistique();
    }

    public void actionNaviguerCarte() {
        vue.naviguerCarte();
    }

    @Override
    public void onCreate(Context applicationContext) {

        BaseDeDonnee.getInstance(applicationContext);

        vue.initialiserUtilisateur();
        vue.initialiserActivite();

        vue.initialiserBarreDeNiveau();
        vue.afficherUtilisateur();
        vue.setListener();
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
