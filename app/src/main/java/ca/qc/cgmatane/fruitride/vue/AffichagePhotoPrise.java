package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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
