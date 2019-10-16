package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.ControleurAffichagePhotoPrise;
import ca.qc.cgmatane.fruitride.controleur.ControleurCarte;
import ca.qc.cgmatane.fruitride.reseau.CreateurRequeteImage;

public class AffichagePhotoPrise extends AppCompatActivity implements VueAffichagePhotoPrise{

    protected ControleurAffichagePhotoPrise controleurAffichagePhotoPrise =
            new ControleurAffichagePhotoPrise(this);
    protected ImageView imageView;

    public static final String URL_ENVOI = "https://test-qr-response.real-it.duckdns.org/upload_image.php";
    public static final String CLE_ENVOI = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_photo_prise);
        Bundle bundle = getIntent().getExtras();
        controleurAffichagePhotoPrise.setCheminImage(
                bundle.getString(ControleurCarte.ID_CHEMIN_IMAGE));
        imageView = findViewById(R.id.affichage_photo_prise_image);
        afficherImage();

        Button affichagePhotoPriseBoutonGallerie = findViewById(R.id.affichage_photo_prise_bouton_gallerie);
        affichagePhotoPriseBoutonGallerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleurAffichagePhotoPrise.ajouterImageALaGallerie();
            }
        });

        Button affichagePhotoPriseBoutonPartager = findViewById(R.id.affichage_photo_prise_bouton_partager);
        affichagePhotoPriseBoutonPartager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partagerImage();
            }
        });
    }

    @Override
    public void ajouterImageALaGallerie() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(controleurAffichagePhotoPrise.getCheminImage());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "Image enregistrée dans la gallerie", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void partagerImage() {
        class PartageImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog avancementEnvoi;
            CreateurRequeteImage requeteEnvoi = new CreateurRequeteImage();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                avancementEnvoi = ProgressDialog.show(AffichagePhotoPrise.this, "Partage de l'image", "Veuillez attendre ...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                avancementEnvoi.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String imageEncodee = encoderImageEnString(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(CLE_ENVOI, imageEncodee);
                data.put("name", avoirNomImage(controleurAffichagePhotoPrise.getCheminImage()));

                String result = requeteEnvoi.postRequest(URL_ENVOI,data);
                return result;
            }
        }

        PartageImage partageImage = new PartageImage();
        partageImage.execute(BitmapFactory.decodeFile(controleurAffichagePhotoPrise.getCheminImage()));
    }

    String avoirNomImage(String chemin) {
        String[] tab = chemin.split("/");
        return tab[tab.length-1];
    }

    public String encoderImageEnString(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void afficherImage() {
        // Get the dimensions of the View
        int targetW = imageView.getMaxWidth();
        int targetH = imageView.getMaxHeight();

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

        Bitmap bitmap = BitmapFactory.decodeFile(controleurAffichagePhotoPrise.getCheminImage(), bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}
