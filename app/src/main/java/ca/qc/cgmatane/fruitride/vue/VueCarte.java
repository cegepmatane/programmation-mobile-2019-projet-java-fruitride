package ca.qc.cgmatane.fruitride.vue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Fruit;

public interface VueCarte {
    void naviguerAccueil();
    void recupererLocalisationEtDemanderAutorisationSiBesoin();
    void ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
    File creerImage() throws IOException;
    void ajouterImageALaGallerie();
    void afficherImage();
    void setListeFruit(List<Fruit> listeFruit);
    void chargerIconesFruitPourMarkers();
    void accederLocalisation();
}
