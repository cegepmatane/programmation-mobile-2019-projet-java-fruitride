package ca.qc.cgmatane.fruitride.controleur;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import ca.qc.cgmatane.fruitride.donnee.ActiviteDAO;
import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.vue.VueStatistique;

public class ControleurStatistique implements Controleur {

    private VueStatistique vue;
    private ActiviteDAO accesseurActivite;

    public ControleurStatistique(VueStatistique vue) {
        this.vue = vue;
    }

    public ArrayList<String> recupererListeJourDeLaSemainePasser(){
        ArrayList<String> listeJour = new ArrayList<>();
        GregorianCalendar calendrier = new GregorianCalendar();
        calendrier.setTime(new Date());
        int dateDuJour = calendrier.get(calendrier.DAY_OF_WEEK);
        switch (dateDuJour) {
            case GregorianCalendar.MONDAY:
                listeJour.add("mar");
                listeJour.add("mer");
                listeJour.add("jeu");
                listeJour.add("ven");
                listeJour.add("sam");
                listeJour.add("dim");
                listeJour.add("auj");
                break;
            case GregorianCalendar.TUESDAY:
                listeJour.add("mer");
                listeJour.add("jeu");
                listeJour.add("ven");
                listeJour.add("sam");
                listeJour.add("dim");
                listeJour.add("lun");
                listeJour.add("auj");
                break;

            case GregorianCalendar.WEDNESDAY:
                listeJour.add("jeu");
                listeJour.add("ven");
                listeJour.add("sam");
                listeJour.add("dim");
                listeJour.add("lun");
                listeJour.add("mar");
                listeJour.add("auj");
                break;
            case GregorianCalendar.THURSDAY:
                listeJour.add("ven");
                listeJour.add("sam");
                listeJour.add("dim");
                listeJour.add("lun");
                listeJour.add("mar");
                listeJour.add("mer");
                listeJour.add("auj");
                break;
            case GregorianCalendar.FRIDAY:
                listeJour.add("sam");
                listeJour.add("dim");
                listeJour.add("lun");
                listeJour.add("mar");
                listeJour.add("mer");
                listeJour.add("jeu");
                listeJour.add("auj");
                break;
            case GregorianCalendar.SATURDAY:
                listeJour.add("dim");
                listeJour.add("lun");
                listeJour.add("mar");
                listeJour.add("mer");
                listeJour.add("jeu");
                listeJour.add("ven");
                listeJour.add("auj");
                break;
            case GregorianCalendar.SUNDAY:
                listeJour.add("lun");
                listeJour.add("mar");
                listeJour.add("mer");
                listeJour.add("jeu");
                listeJour.add("ven");
                listeJour.add("sam");
                listeJour.add("auj");
                break;

            ///etc etc
            default:
                //ça devrait pas erreur
                listeJour.add("lun");
                listeJour.add("mar");
                listeJour.add("mer");
                listeJour.add("jeu");
                listeJour.add("ven");
                listeJour.add("sam");
                listeJour.add("dim");
                break;
        }
        return listeJour;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList getData(ArrayList<String> listeJour){
        ArrayList<DataEntry> entrees = new ArrayList<>();
        for (int i=0; i<7;i++){
            int nombreDePas = accesseurActivite.recupererNombrePas(6-i);
            entrees.add(new ValueDataEntry(listeJour.get(i), nombreDePas));
        }
        return entrees;
    }

    public void actionNaviguerAccueil() {
        vue.naviguerAccueil();
    }

    @Override
    public void onCreate(Context applicationContext) {

        BaseDeDonnee.getInstance(applicationContext);
        accesseurActivite = ActiviteDAO.getInstance();

        vue.initialiserGraphique();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int activite) {
    }
}
