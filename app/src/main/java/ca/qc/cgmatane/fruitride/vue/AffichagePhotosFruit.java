package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.donnee.FruitDAO;
import ca.qc.cgmatane.fruitride.modele.Fruit;

public class AffichagePhotosFruit extends AppCompatActivity {
    protected int idFruitParametres;
    String URL = "https://test-qr-response.real-it.duckdns.org/upload/";
    ImageView affichagePhotoFruitImage1;
    ImageView affichagePhotoFruitImage2;
    Button button;
    ProgressDialog mProgressDialog;
    List<String> listeImages;

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
        button = (Button) findViewById(R.id.dlimg);

        FruitDAO lala =  FruitDAO.getInstance();
         listeImages = lala.recupererImageFruitParId(idFruitParametres);
        switch (listeImages.size()) {
            case 1:
                new TelechargerImage1().execute(URL + listeImages.get(0));
                break;
            case 2:
                new TelechargerImage1().execute(URL + listeImages.get(0));
                new TelechargerImage2().execute(URL + listeImages.get(1));
                break;

        }

        // Capture button click
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Execute TelechargerImage2 AsyncTask
                new TelechargerImage2().execute(URL);
            }
        });
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
