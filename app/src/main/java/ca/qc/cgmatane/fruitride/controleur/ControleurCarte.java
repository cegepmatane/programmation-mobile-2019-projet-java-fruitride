package ca.qc.cgmatane.fruitride.controleur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.FruitDAO;
import ca.qc.cgmatane.fruitride.vue.VueCarte;

public class ControleurCarte implements Controleur {

    private VueCarte vue;
    private FruitDAO accesseurFruit;

    public static final int CODE_REQUETE_AUTORISATION_LOCALISATION = 101;
    public static final int CODE_REQUETE_AUTORISATION_CAMERA = 102;
    public static final int CODE_REQUETE_AUTORISATION_STOCKAGE = 103;
    public static final int CODE_REQUETE_CAPTURE_IMAGE = 1;

    public Location localisationActuelle;
    public FusedLocationProviderClient fusedLocationProviderClient;

    private static final int ZOOM_PAR_DEFAUT = 15;
    private static final int ZOOM_MINIMUM = 12;

    public ControleurCarte(VueCarte vue) {
        this.vue = vue;
    }

    public void actionNaviguerAccueil() {
        vue.naviguerAccueil();
    }

    public void naviguerVersAppareilPhoto() {
        vue.ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
    }

    @Override
    public void onCreate(Context applicationContext) {

        BaseDeDonnee.getInstance(applicationContext);
        accesseurFruit = FruitDAO.getInstance();
        vue.setListeFruit(accesseurFruit.recupererListeFruit());
        vue.chargerIconesFruitPourMarkers();
        vue.accederLocalisation();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_REQUETE_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            vue.ajouterImageALaGallerie();
            vue.afficherImage();
//            Toast.makeText(this, "Photo enregistrée dans la galerie", Toast.LENGTH_SHORT).show();
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.imageView.setImageBitmap(imageBitmap);*/
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CODE_REQUETE_AUTORISATION_LOCALISATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vue.recupererLocalisationEtDemanderAutorisationSiBesoin();
            }
        } else if (requestCode == CODE_REQUETE_AUTORISATION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vue.ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        } else if (requestCode == CODE_REQUETE_AUTORISATION_STOCKAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vue.ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(ZOOM_MINIMUM);
        LatLng localisationActuelleLatLng =
                new LatLng(localisationActuelle.getLatitude(), localisationActuelle.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(localisationActuelleLatLng));
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(localisationActuelleLatLng, ZOOM_PAR_DEFAUT));
        googleMap.setMyLocationEnabled(true);

        vue.afficherFruits(googleMap);
    }
}
