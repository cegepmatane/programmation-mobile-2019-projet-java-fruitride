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
    public static final String ID_CHEMIN_IMAGE = "cheminImage";

    public Location localisationActuelle;
    public FusedLocationProviderClient fusedLocationProviderClient;

    public String emplacementPhoto;

    private static final int ZOOM_PAR_DEFAUT = 15;
    private static final int ZOOM_MINIMUM = 12;

    public ControleurCarte(VueCarte vue) {
        this.vue = vue;
    }

    public void actionNaviguerAccueil() {
        vue.naviguerAccueil();
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CODE_REQUETE_AUTORISATION_LOCALISATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vue.recupererLocalisationEtDemanderAutorisationSiBesoin();
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
