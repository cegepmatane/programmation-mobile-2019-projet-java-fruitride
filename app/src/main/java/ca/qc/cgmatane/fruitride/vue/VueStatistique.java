package ca.qc.cgmatane.fruitride.vue;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.donnee.ActiviteDAO;
import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;


public class VueStatistique extends AppCompatActivity {
    private AnyChartView columnChart;
    protected ActiviteDAO accesseurActivite;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_statistique);
        BaseDeDonnee.getInstance(getApplicationContext());
        accesseurActivite = ActiviteDAO.getInstance();

        ArrayList<String> listeJour = recupererListeJourDeLaSemainePasser();
        columnChart = (AnyChartView)findViewById(R.id.columnChart);
        columnChart.setProgressBar(findViewById(R.id.progress_bar));
        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(getData(listeJour));
        column.tooltip()
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM);
        cartesian.animation(true);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.title("Vos pas cette semaine");
        columnChart.setChart(cartesian);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList getData(ArrayList<String> listeJour){
        ArrayList<DataEntry> entries = new ArrayList<>();
        for (int i=0; i<7;i++){
            System.out.println("OKOKOKOKOKOKKKOKOK  " + i);
            int nbPas=accesseurActivite.recupererNombrePas(6-i);
            entries.add(new ValueDataEntry(listeJour.get(i), nbPas));
        }
        return entries;
    }

    private ArrayList<String> recupererListeJourDeLaSemainePasser(){
        ArrayList<String> listeJour = new ArrayList<>();
        GregorianCalendar calendar =new GregorianCalendar();
        calendar.setTime(new Date());
        int today =calendar.get(calendar.DAY_OF_WEEK);
        switch (today) {
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
}