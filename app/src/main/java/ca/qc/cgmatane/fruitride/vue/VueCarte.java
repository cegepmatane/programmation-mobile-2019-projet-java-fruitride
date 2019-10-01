package ca.qc.cgmatane.fruitride.vue;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.R.anim.*;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.donnee.FruitDAO;
import ca.qc.cgmatane.fruitride.modele.Fruit;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;


public class VueCarte extends FragmentActivity implements OnMapReadyCallback {

    private FruitDAO accesseurFruit;
    private List<Fruit> listeFruit;
    private ImageView imageView;

    Location localisationActuelle;
    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int CODE_REQUETE_AUTORISATION_LOCALISATION = 101;
    private static final int CODE_REQUETE_AUTORISATION_CAMERA = 102;
    private static final int CODE_REQUETE_AUTORISATION_STOCKAGE = 103;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int ZOOM_PAR_DEFAUT = 15;
    private static final int ZOOM_MINIMUM = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_carte);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        recupererLocalisationEtDemanderAutorisationSiBesoin();

        accesseurFruit = FruitDAO.getInstance();
        listeFruit = accesseurFruit.recupererListeFruit();

        // chargement des markers personnalisés pour les fruits
        // pour l'instant obligé dans une classe Activity
        for (Fruit fruit : listeFruit) {
            fruit.setLogo(BitmapFactory.decodeResource(getResources(), fruit.getIdResourceLogo()));
        }

        //final Intent intentionNaviguerVuePrincipale = new Intent(this, Accueil.class);

        imageView = findViewById(R.id.imageView);

        Button bouton = findViewById(R.id.button2);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        });

        findViewById(R.id.layout).setOnTouchListener(new ListenerSwipe(VueCarte.this) {
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {
                intentionNaviguerVuePrincipale();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
    }

    protected void intentionNaviguerVuePrincipale() {
        this.finish();
    }

    private void recupererLocalisationEtDemanderAutorisationSiBesoin() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION }
                    , CODE_REQUETE_AUTORISATION_LOCALISATION );
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    localisationActuelle = location;
                    /*Toast.makeText(getApplicationContext(), localisationActuelle.getLatitude()
                            + " " + localisationActuelle.getLongitude(), Toast.LENGTH_SHORT).show();*/
                    SupportMapFragment supportMapFragment =
                            (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.vue_carte_element_map_view);
                    supportMapFragment.getMapAsync(VueCarte.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(ZOOM_MINIMUM);
        LatLng localisationActuelleLatLng =
                new LatLng(localisationActuelle.getLatitude(), localisationActuelle.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(localisationActuelleLatLng));
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(localisationActuelleLatLng, ZOOM_PAR_DEFAUT));
        googleMap.setMyLocationEnabled(true);

        for (Fruit fruit : listeFruit) {
            googleMap.addMarker(fruit.getMarkerFruit());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CODE_REQUETE_AUTORISATION_LOCALISATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recupererLocalisationEtDemanderAutorisationSiBesoin();
            }
        } else if (requestCode == CODE_REQUETE_AUTORISATION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        } else if (requestCode == CODE_REQUETE_AUTORISATION_STOCKAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        }
    }


    private void ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.CAMERA }
                    , CODE_REQUETE_AUTORISATION_CAMERA );
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE }
                    , CODE_REQUETE_AUTORISATION_STOCKAGE );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.imageView.setImageBitmap(imageBitmap);
        }
    }


}
