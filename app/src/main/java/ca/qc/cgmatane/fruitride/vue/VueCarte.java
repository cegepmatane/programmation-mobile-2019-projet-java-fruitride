package ca.qc.cgmatane.fruitride.vue;

import com.google.android.gms.maps.GoogleMap;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Fruit;

public interface VueCarte {
    void naviguerAccueil();
    void recupererLocalisationEtDemanderAutorisationSiBesoin();
    void setListeFruit(List<Fruit> listeFruit);
    void chargerIconesFruitPourMarkers();
    void accederLocalisation();
    void afficherFruits(GoogleMap googleMap);
}
