package ca.qc.cgmatane.fruitride.vue;

import java.util.ArrayList;

public interface VueStatistique {
    ArrayList getData(ArrayList<String> listeJour);
    ArrayList<String> recupererListeJourDeLaSemainePasser();
}
