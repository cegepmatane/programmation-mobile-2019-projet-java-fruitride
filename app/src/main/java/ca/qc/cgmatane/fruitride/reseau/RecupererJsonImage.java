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


public class RecupererJsonImage extends AsyncTask<Void,Void,Void> {

    String data ="";
    List<String> id;
    List<String> latitude;
    List<String> longitude;
    List<String> type;
    boolean finish = false;

    public RecupererJsonImage() {
        id = new ArrayList<>();
        latitude = new ArrayList<>();
        longitude = new ArrayList<>();
        type = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://fruitride.real-it.duckdns.org/fruits");
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
                this.id.add(JA.getJSONObject(String.valueOf(i)).getString("id_fruit"));
                this.latitude.add(JA.getJSONObject(String.valueOf(i)).getString("latitude"));
                this.longitude.add(JA.getJSONObject(String.valueOf(i)).getString("longitude"));
                this.type.add(JA.getJSONObject(String.valueOf(i)).getString("type_fruit"));
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

    public List<String> getId() {
        if (finish)
            return id;
        return id;
    }

    public List<String> getLatitude() {
        return latitude;
    }

    public List<String> getLongitude() {
        return longitude;
    }

    public List<String> getType() {
        return type;
    }
}
