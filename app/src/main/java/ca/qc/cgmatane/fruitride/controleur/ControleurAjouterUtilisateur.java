package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;

import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;
import ca.qc.cgmatane.fruitride.vue.VueAjoutUtilisateur;

public class ControleurAjouterUtilisateur implements Controleur {

    private VueAjoutUtilisateur vue;
    private UtilisateurDAO accesseurUtilisateur;

    public ControleurAjouterUtilisateur(VueAjoutUtilisateur vue) {
        this.vue = vue;
    }

    public void actionEnregistrerUtilisateur(Utilisateur utilisateur) {
        accesseurUtilisateur = UtilisateurDAO.getInstance();
        accesseurUtilisateur.ajouterUtilisateur(utilisateur);
        vue.naviguerRetourClub();
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
