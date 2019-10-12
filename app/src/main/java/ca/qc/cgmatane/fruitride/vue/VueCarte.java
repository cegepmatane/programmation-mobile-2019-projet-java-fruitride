package ca.qc.cgmatane.fruitride.vue;

import java.io.File;
import java.io.IOException;

public interface VueCarte {
    void intentionNaviguerVuePrincipale();
    void recupererLocalisationEtDemanderAutorisationSiBesoin();
    void ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
    File createImageFile() throws IOException;
    void galleryAddPic();
    void setPic();
}
