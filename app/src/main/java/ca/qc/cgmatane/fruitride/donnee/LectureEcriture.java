package ca.qc.cgmatane.fruitride.donnee;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LectureEcriture {

    private String nomDuFichier;

    //construsteur (test)
    public LectureEcriture(){
        nomDuFichier = "configuration.xml";
    }

    /**
     * Méthode pour ÉCRIRE dans le fichier
     * @param donneesAecrire
     * @param context
     */
    public void ecrireDansLeFichier(String donneesAecrire, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(nomDuFichier, Context.MODE_PRIVATE));
            outputStreamWriter.write(donneesAecrire);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("LECTURE/ÉCRITURE", "Échec de l'écriture dans le fichier : " + e.toString());
        }
    }

    /**
     * Méthode pour LIRE depuis le fichier
     * @param context
     * @return
     */
    public String lireDepuisLeFichier(Context context) {

        String donneesLues = "";

        try {
            InputStream inputStream = context.openFileInput(nomDuFichier);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String stringRecue = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (stringRecue = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(stringRecue);
                }

                inputStream.close();
                donneesLues = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("LECTURE/ÉCRITURE", "Fichier introuvable pour la lecture : " + e.toString());
        } catch (IOException e) {
            Log.e("LECTURE/ÉCRITURE", "Fichier illisible pour la lecture : " + e.toString());
        }

        return donneesLues;
    }

    /**
     * Méthode pour VÉRIFIER l'existance et/ou l'accessibilité du fichier.
     * @param context
     * @return
     */
    public boolean fichierPret(Context context) {

        boolean resultat = false;

        try {
            InputStream inputStream = context.openFileInput(nomDuFichier);

            if ( inputStream != null ) {
                resultat = true;
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e("LECTURE/ÉCRITURE", "Fichier introuvable pour la lecture : " + e.toString());
            resultat = false;
        } catch (IOException e) {
            //Log.e("LECTURE/ÉCRITURE", "Fichier illisible pour la lecture : " + e.toString());
            resultat = false;
        }

        return resultat;
    }

}



