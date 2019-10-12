package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;

import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.FruitDAO;
import ca.qc.cgmatane.fruitride.vue.VueCarte;

public class ControleurCarte implements Controleur {

    private VueCarte vue;
    private FruitDAO accesseurFruit;

    private static final int CODE_REQUETE_AUTORISATION_LOCALISATION = 101;
    private static final int CODE_REQUETE_AUTORISATION_CAMERA = 102;
    private static final int CODE_REQUETE_AUTORISATION_STOCKAGE = 103;

    public ControleurCarte(VueCarte vue) {
        this.vue = vue;
    }

    public void actionNaviguerAccueil() {
        vue.naviguerAccueil();
    }

    public void naviguerVersAppareilPhoto() {
        vue.ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
    }

    @Override
    public void onCreate(Context applicationContext) {

        BaseDeDonnee.getInstance(applicationContext);
        accesseurFruit = FruitDAO.getInstance();
        vue.setListeFruit(accesseurFruit.recupererListeFruit());
        vue.chargerIconesFruitPourMarkers();
        vue.accederLocalisation();
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CODE_REQUETE_AUTORISATION_LOCALISATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vue.recupererLocalisationEtDemanderAutorisationSiBesoin();
            }
        } else if (requestCode == CODE_REQUETE_AUTORISATION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vue.ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        } else if (requestCode == CODE_REQUETE_AUTORISATION_STOCKAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vue.ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        }
    }
}
