package ca.qc.cgmatane.fruitride.vue;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.Controleur;
import ca.qc.cgmatane.fruitride.controleur.ControleurCarte;
import ca.qc.cgmatane.fruitride.modele.Fruit;
import ca.qc.cgmatane.fruitride.gesture.ListenerSwipe;


public class Carte extends FragmentActivity implements OnMapReadyCallback, VueCarte {

    private List<Fruit> listeFruit;
    private ImageView imageView;

    protected ControleurCarte controleurCarte = new ControleurCarte(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_carte);

        imageView = findViewById(R.id.imageView);

        Button bouton = findViewById(R.id.button2);

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleurCarte.naviguerVersAppareilPhoto();
            }
        });

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
                    /*Toast.makeText(getApplicationContext(), localisationActuelle.getLatitude()
                            + " " + localisationActuelle.getLongitude(), Toast.LENGTH_SHORT).show();*/
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


    public void ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.CAMERA }
                    , ControleurCarte.CODE_REQUETE_AUTORISATION_CAMERA );
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = creerImage();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, controleurCarte.CODE_REQUETE_CAPTURE_IMAGE);
                }
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , ControleurCarte.CODE_REQUETE_AUTORISATION_STOCKAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        controleurCarte.onActivityResult(requestCode, resultCode, data);
    }

    String currentPhotoPath;

    @Override
    public File creerImage() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/FruitRide");
        if (!storageDir.exists()) {
            boolean resultat = storageDir.mkdir();
            Log.d("TRUC", resultat + "");
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void ajouterImageALaGallerie() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
//        System.out.println("ENREGISTREMENT PHOTO " + contentUri.toString());
    }

    public void afficherImage() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void setListeFruit(List<Fruit> listeFruit) {
        this.listeFruit = listeFruit;
    }


}
