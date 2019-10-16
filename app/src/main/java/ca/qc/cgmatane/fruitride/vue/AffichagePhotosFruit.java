package ca.qc.cgmatane.fruitride.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.ControleurCarte;
import ca.qc.cgmatane.fruitride.donnee.FruitDAO;
import ca.qc.cgmatane.fruitride.modele.Fruit;

public class AffichagePhotosFruit extends AppCompatActivity {
    protected int idFruitParametres;
    String URL = "https://test-qr-response.real-it.duckdns.org/upload/";
    ImageView affichagePhotoFruitImage1;
    ImageView affichagePhotoFruitImage2;
    Button affichagePhotoFruitBoutonPrendrePhoto;
    ProgressDialog mProgressDialog;
    List<String> listeImages;
    String emplacementPhoto;
    public static final int CODE_REQUETE_AUTORISATION_CAMERA = 102;
    public static final int CODE_REQUETE_AUTORISATION_STOCKAGE = 103;
    public static final int CODE_REQUETE_CAPTURE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_photos_fruit);
        Bundle parametres = getIntent().getExtras();
        idFruitParametres = (int) parametres.get(Fruit.CLE_ID_FRUIT);

        Log.d("TOUDOUM", idFruitParametres + "--");

        affichagePhotoFruitImage1 = (ImageView) findViewById(R.id.affichage_photo_fruit_image1);
        affichagePhotoFruitImage2 = (ImageView) findViewById(R.id.affichage_photo_fruit_image2);

        // Locate the Button in activity_main.xml
        affichagePhotoFruitBoutonPrendrePhoto = (Button) findViewById(R.id.affichage_photo_fruit_bouton_prendre_photo);

        FruitDAO lala =  FruitDAO.getInstance();
         listeImages = lala.recupererImageFruitParId(idFruitParametres);
        /*switch (listeImages.size()) {
            case 1:
                new TelechargerImage1().execute(URL + listeImages.get(0));
                break;
            case 2:
                new TelechargerImage1().execute(URL + listeImages.get(0));
                new TelechargerImage2().execute(URL + listeImages.get(1));
                break;

        }*/

        // Capture affichagePhotoFruitBoutonPrendrePhoto click
        affichagePhotoFruitBoutonPrendrePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Execute TelechargerImage2 AsyncTask
                ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin();
            }
        });

        Button affichagePhotoPriseBoutonRetour
                = findViewById(R.id.affichage_photo_fruit_bouton_retour);
        affichagePhotoPriseBoutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naviguerRetour();
            }
        });
    }

    private void naviguerRetour() {
        this.finish();
    }

    public void ouvrirAppareilPhotoEtDemanderAutorisationSiBesoin() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.CAMERA }
                    , CODE_REQUETE_AUTORISATION_CAMERA );
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = enregistrerImage();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CODE_REQUETE_CAPTURE_IMAGE);
                }
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , CODE_REQUETE_AUTORISATION_STOCKAGE);
            }
        }
    }

    public File enregistrerImage() throws IOException {
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

        emplacementPhoto = image.getAbsolutePath();
        return image;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_REQUETE_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            naviguerVersPhotoPrise();
//            Toast.makeText(this, "Photo enregistrée dans la galerie", Toast.LENGTH_SHORT).show();
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.imageView.setImageBitmap(imageBitmap);*/
        }
    }

    public void naviguerVersPhotoPrise() {
        Intent intent = new Intent(this, AffichagePhotoPrise.class);
        intent.putExtra(ControleurCarte.ID_CHEMIN_IMAGE, emplacementPhoto);
        startActivityForResult(intent, 123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CODE_REQUETE_AUTORISATION_CAMERA) {
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

    // TelechargerImage2 AsyncTask
    private class TelechargerImage1 extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AffichagePhotosFruit.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Téléchargement des images");
            // Set progressdialog message
            mProgressDialog.setMessage("En cours...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            affichagePhotoFruitImage1.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }

    private class TelechargerImage2 extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AffichagePhotosFruit.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Téléchargement des images");
            // Set progressdialog message
            mProgressDialog.setMessage("En cours...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            affichagePhotoFruitImage2.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }
}
