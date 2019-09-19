package ca.qc.cgmatane.fruitride.donnee;

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
}
