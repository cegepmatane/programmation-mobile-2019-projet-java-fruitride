package ca.qc.cgmatane.fruitride.vue;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.ControleurCarte;
import ca.qc.cgmatane.fruitride.modele.Fruit;
import ca.qc.cgmatane.fruitride.gesture.ListenerSwipe;


public class Carte extends FragmentActivity implements OnMapReadyCallback, VueCarte {

    private List<Fruit> listeFruit;

    protected ControleurCarte controleurCarte = new ControleurCarte(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_carte);

        findViewById(R.id.layout).setOnTouchListener(new ListenerSwipe(Carte.this) {
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {
                controleurCarte.actionNaviguerAccueil();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        controleurCarte.onCreate(getApplicationContext());
    }

    @Override
    public void chargerIconesFruitPourMarkers() {
        // chargement des markers personnalisés pour les fruits
        // pour l'instant obligé dans une classe Activity
        for (Fruit fruit : listeFruit) {
            fruit.setLogo(BitmapFactory.decodeResource(getResources(), fruit.getIdResourceLogo()));
        }
    }

    @Override
    public void naviguerAccueil() {
        this.finish();
    }

    @Override
    public void accederLocalisation() {
        controleurCarte.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        recupererLocalisationEtDemanderAutorisationSiBesoin();
    }

    @Override
    public void afficherFruits(GoogleMap googleMap) {
        for (Fruit fruit : listeFruit) {
            googleMap.addMarker(fruit.getMarkerFruit());
        }
        final Intent naviguerVersPhotos = new Intent(this, AffichagePhotosFruit.class);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Fruit fruit : listeFruit) {
                    if (marker.getPosition().equals(fruit.getMarkerFruit().getPosition())){
                        naviguerVersPhotos.putExtra(Fruit.CLE_ID_FRUIT, fruit.getIdFruit());
                        startActivityForResult(naviguerVersPhotos, 13);
                        Log.d("MARKER", fruit.getIdFruit()+"");
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void recupererLocalisationEtDemanderAutorisationSiBesoin() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION }
                    , ControleurCarte.CODE_REQUETE_AUTORISATION_LOCALISATION );
        }
        Task<Location> task = controleurCarte.fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    controleurCarte.localisationActuelle = location;
                    /*Toast.makeText(getApplicationContext(), localisationActuelle.getChemin()
                            + " " + localisationActuelle.getId_fruit(), Toast.LENGTH_SHORT).show();*/
                    SupportMapFragment supportMapFragment =
                            (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.vue_carte_element_map_view);
                    supportMapFragment.getMapAsync(Carte.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        controleurCarte.onMapReady(googleMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        controleurCarte.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setListeFruit(List<Fruit> listeFruit) {
        this.listeFruit = listeFruit;
    }


}
