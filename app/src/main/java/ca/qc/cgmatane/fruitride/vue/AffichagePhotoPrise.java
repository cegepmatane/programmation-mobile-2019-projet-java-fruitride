package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.ControleurAffichagePhotoPrise;
import ca.qc.cgmatane.fruitride.controleur.ControleurCarte;

public class AffichagePhotoPrise extends AppCompatActivity implements VueAffichagePhotoPrise{

    protected ControleurAffichagePhotoPrise controleurAffichagePhotoPrise =
            new ControleurAffichagePhotoPrise(this);
    protected ImageView imageView;

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
