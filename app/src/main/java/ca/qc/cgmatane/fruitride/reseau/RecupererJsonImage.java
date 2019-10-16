package ca.qc.cgmatane.fruitride.reseau;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RecupererJsonImage extends AsyncTask<String,Void,Void> {

    String data ="";
    List<String> id_image;
    List<String> chemin;
    List<String> id_fruit;
    boolean finish = false;
    final static String URL = "https://fruitride.real-it.duckdns.org/images-fruit-";

    public RecupererJsonImage() {
        id_image = new ArrayList<>();
        chemin = new ArrayList<>();
        id_fruit = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            String id = params[0];
            String urlService = URL + id;
            URL url = new URL(urlService);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONObject JA = new JSONObject(data);

            for(int i =0 ;i < JA.length(); i++){
                this.id_image.add(JA.getJSONObject(String.valueOf(i)).getString("id"));
                this.chemin.add(JA.getJSONObject(String.valueOf(i)).getString("chemin"));
                this.id_fruit.add(JA.getJSONObject(String.valueOf(i)).getString("id_fruit"));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        finish = true;
    }

    public List<String> getId_image() {
        if (finish)
            return id_image;
        return id_image;
    }

    public List<String> getChemin() {
        return chemin;
    }

    public List<String> getId_fruit() {
        return id_fruit;
    }

}
