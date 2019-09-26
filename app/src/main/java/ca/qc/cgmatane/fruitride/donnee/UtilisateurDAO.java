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

    public void preparerListeUtilisateur() {
        listeUtilisateur.add(new Utilisateur("Chateau", "Lucas", 15, 1550, 1));
        listeUtilisateur.add(new Utilisateur("Barcon", "Lucien", 16, 1650, 2));
        listeUtilisateur.add(new Utilisateur("Cousson", "Théo", 13, 1300, 3));
        listeUtilisateur.add(new Utilisateur("Hug", "Loik", 14, 1450, 4));
    }

    public static UtilisateurDAO getInstance() {
        if (null == instance)
            instance = new UtilisateurDAO();
        return instance;
    }
}
