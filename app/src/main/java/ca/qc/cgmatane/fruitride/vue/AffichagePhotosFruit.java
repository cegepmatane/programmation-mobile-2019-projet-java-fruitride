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

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.modele.Fruit;

public class AffichagePhotosFruit extends AppCompatActivity {
    protected int idFruitParametres;
    String URL = "https://test-qr-response.real-it.duckdns.org/upload/JPEG_20191012_222154_1001476978457843130.jpg";
    ImageView image;
    Button button;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_photos_fruit);
        Bundle parametres = getIntent().getExtras();
        idFruitParametres = (int) parametres.get(Fruit.CLE_ID_FRUIT);

        Log.d("TOUDOUM", idFruitParametres + "--");

        image = (ImageView) findViewById(R.id.imagedl);

        // Locate the Button in activity_main.xml
        button = (Button) findViewById(R.id.dlimg);

        // Capture button click
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Execute DownloadImage AsyncTask
                new DownloadImage().execute(URL);
            }
        });
    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AffichagePhotosFruit.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
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
            image.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }
}
