package ca.qc.cgmatane.fruitride.AccesTelephone;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LectureEcriture {

    private String nomDuFichier;

    private String theme = "clair";
    private String notifications = "false";
    private String lieux = "false";

    //construsteur (test)
    public LectureEcriture(Context contexte){
        nomDuFichier = "configuration.xml";

        //cette ligne ne sert que pour mon cas, utiliser le if sinon
        //miseAjourXML(contexte);

        if (fichierPret(contexte)) {
            extraireInformationsDepuisXML(lireDepuisLeFichier(contexte));
        } else {
            miseAjourXML(contexte);
        }
    }

    /**
     * Méthode publique de mise à jour du fichier XML
     * @param contexte
     */
    public void miseAjourXML(Context contexte) {
        ecrireDansLeFichier(importerInformationsDansXML(), contexte);
    }

    /**
     * Méthode pour ÉCRIRE dans le fichier
     * @param donneesAecrire
     * @param context
     */
    private void ecrireDansLeFichier(String donneesAecrire, Context context) {
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
    private String lireDepuisLeFichier(Context context) {

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
     * Méthode pour extraire les infos depuis l'XML vers des variables
     *
     */
    private void extraireInformationsDepuisXML(String donneesLues) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            // création d'un parseur pour le XML
            final DocumentBuilder builder = factory.newDocumentBuilder();

            // création d'un Document à partir de la String récupérée
            final Document document = builder.parse(new InputSource(new StringReader(donneesLues)));

            // récupération de l'élement <configuration>
            final Element configuration = document.getDocumentElement();

            // Récupération des réglages
            final NodeList racineNoeuds = configuration.getChildNodes();
            final int nbRacineNoeuds = racineNoeuds.getLength();

            for (int i = 0; i < nbRacineNoeuds; i++) { //sans la boucle for, on n'a rien dans config…
                if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element reglages = (Element) racineNoeuds.item(i);

                    // Récupération du theme, notifs et lieux
                    final Element theme = (Element) reglages.getElementsByTagName("theme").item(0);
                    final Element notifications = (Element) reglages.getElementsByTagName("notifications").item(0);
                    final Element lieux = (Element) reglages.getElementsByTagName("lieux").item(0);

                    //Passage de la choucroute dans des variables locales
                    this.theme = theme.getTextContent();
                    this.notifications = notifications.getTextContent();
                    this.lieux = lieux.getTextContent();
                }
            }
        } catch (final ParserConfigurationException e) {
            Log.e("LECTURE/ÉCRITURE", "Erreur de récupération de informations contenues dans le XML : " + e.toString());
            //e.printStackTrace();
        } catch (final SAXException e) {
            Log.e("LECTURE/ÉCRITURE", "Erreur de récupération de informations contenues dans le XML : " + e.toString());
            //e.printStackTrace();
        } catch (final IOException e) {
            Log.e("LECTURE/ÉCRITURE", "Erreur de récupération de informations contenues dans le XML : " + e.toString());
            //e.printStackTrace();
        }
    }

    /**
     * Méthode pour copier les valeurs locales dans le fichier XML
     */
    private String importerInformationsDansXML(){

        String string = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                "<configuration>\n" +
                "    <reglages>\n" +
                "        <theme>"+this.theme+"</theme>\n" +
                "        <notifications>"+this.notifications+"</notifications>\n" +
                "        <lieux>"+this.lieux+"</lieux>\n" +
                "    </reglages>\n" +
                "</configuration>";

        return string;
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

    // getters et setters

    public boolean themeClair() {
        return(theme.equals("clair"));
    }

    public boolean notificationsActives() {
        return(notifications.equals("true"));
    }

    public boolean lieuxAproximiteActifs() {
        return(lieux.equals("true"));
    }

}



