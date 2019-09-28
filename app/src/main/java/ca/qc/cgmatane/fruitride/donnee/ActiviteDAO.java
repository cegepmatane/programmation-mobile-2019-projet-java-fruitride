package ca.qc.cgmatane.fruitride.donnee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Activite;

public class ActiviteDAO {

    private static ActiviteDAO instance = null;
    private List<Activite> listeActivite;

    public static ActiviteDAO getInstance() {
        if (instance == null)
            instance = new ActiviteDAO();
        return instance;
    }

    public ActiviteDAO() {
        listeActivite = new ArrayList<>();
    }

    public void preparerListeActivite() {
        listeActivite.add(new Activite(Calendar.getInstance(), 1234, 2, 1));
        listeActivite.add(new Activite(Calendar.getInstance(), 9765, 4, 1));
        listeActivite.add(new Activite(Calendar.getInstance(), 1002, 0, 1));
    }

    public List<Activite> recupererListeActivite() {
        return listeActivite;
    }

    public void ajouterActivite(Activite activite) {
        listeActivite.add(activite);
    }

    public Activite chercherActiviteParIdActivite(int idActivite) {
        for (Activite activiteRecherche :
                this.listeActivite) {
            if (activiteRecherche.getIdActivite() == idActivite) return activiteRecherche;
        }
        return null;
    }
}
