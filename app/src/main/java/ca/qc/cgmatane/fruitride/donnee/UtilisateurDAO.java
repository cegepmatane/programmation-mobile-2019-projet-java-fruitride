package ca.qc.cgmatane.fruitride.donnee;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class UtilisateurDAO {

    private static UtilisateurDAO instance = null;
    protected List<Utilisateur> listeUtilisateur;

    public UtilisateurDAO() {
        listeUtilisateur = new ArrayList<>();
    }

    public List<HashMap<String,String>> recupererListeUtilisateurPourAdapteur() {

        List<HashMap<String,String>> listeUtilisateurPourAdapteur =
                new ArrayList<HashMap<String, String>>();

        for (Utilisateur utilisateur :
                listeUtilisateur) {
            listeUtilisateurPourAdapteur.add(utilisateur.obtenirUtilisateurPourAdapteur());
        }
        return listeUtilisateurPourAdapteur;
    }

    public Utilisateur chercherUtilisateurParId(int id_utilisateur) {
        for (Utilisateur utilisateurRecherche :
                this.listeUtilisateur) {
            if (utilisateurRecherche.getId_utilisateur() == id_utilisateur)
                return utilisateurRecherche;
        }
        return null;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        listeUtilisateur.add(utilisateur);
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        for (Utilisateur utilisateurRecherche :
                this.listeUtilisateur) {
            if (utilisateurRecherche.getId_utilisateur() == utilisateur.getId_utilisateur()) {
                utilisateurRecherche = utilisateur;
            }
        }
    }

    public List<Utilisateur> recupererListeUtilisateur() {
        return listeUtilisateur;
    }
}
