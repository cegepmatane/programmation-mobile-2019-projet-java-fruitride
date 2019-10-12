package ca.qc.cgmatane.fruitride.vue;

public interface VueAccueil {
    void initialiserBarreDeNiveau();
    void initialiserUtilisateur();
    void initialiserActivite();
    void setListener();
    void afficherUtilisateur();
    void naviguerStatistique();
    void naviguerCarte();
    void naviguerConfiguration();
}
