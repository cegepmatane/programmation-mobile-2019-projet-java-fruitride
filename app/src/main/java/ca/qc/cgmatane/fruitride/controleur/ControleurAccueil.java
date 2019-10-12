package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import ca.qc.cgmatane.fruitride.donnee.ActiviteDAO;
import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.Activite;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;
import ca.qc.cgmatane.fruitride.vue.VueAccueil;

public class ControleurAccueil implements Controleur{

    private VueAccueil vue;

    private UtilisateurDAO accesseurUtilisateur;
    private Utilisateur utilisateur;

    private ActiviteDAO accesseurActivite;
    private Activite activite;

    public ControleurAccueil(VueAccueil vue) {
        this.vue = vue;
    }

    public void actionEnregistrerUtilisateur(Utilisateur utilisateur) {

        accesseurUtilisateur.enregistrerExperience(utilisateur);
    }

    public void actionEnregistrerActivite(Activite activite) {

        accesseurActivite.enregistrerNombreDePas(activite);
    }

    public float actionRecupererNombreDePasActivite() {

        return activite.getNombreDePas();
    }

    public int actionRecupererExperienceUtilisateur() {

        return utilisateur.getExperience();
    }

    public int actionRecupererNiveauUtilisateur() {

        return utilisateur.getNiveau();
    }

    public void actionDefinirExperienceUtilisateur(int experience) {

        utilisateur.setExperience(experience);
    }

    public void actionDefinirNiveauUtilisateur(int niveau) {

        utilisateur.setNiveau(niveau);
    }

    public String actionRecupererNomUtilisateur() {

        return utilisateur.getNom();
    }

    public String actionRecupererPrenomUtilisateur() {

        return utilisateur.getPrenom();
    }

    public int actionRecupererIdUtilisateur() {

        return utilisateur.getId_utilisateur();
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

        accesseurUtilisateur = UtilisateurDAO.getInstance();
        utilisateur = accesseurUtilisateur.recupererUtilisateur();

        accesseurActivite = ActiviteDAO.getInstance();
        accesseurActivite.isActiviteAjourdhui(new Activite(Calendar.getInstance(),0,
                0,utilisateur.getId_utilisateur()));
        activite = accesseurActivite.recupererActivite();

        vue.initialiserBarreDeNiveau();
        vue.afficherUtilisateur();
        vue.setListener();
        vue.initialiserTexteNombreDePas();
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
