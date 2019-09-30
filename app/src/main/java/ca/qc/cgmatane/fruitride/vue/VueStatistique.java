package ca.qc.cgmatane.fruitride.vue;

import android.os.Bundle;

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


public class VueStatistique extends AppCompatActivity {
    private AnyChartView columnChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_statistique);
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

    private ArrayList getData(ArrayList<String> listeJour){
        ArrayList<DataEntry> entries = new ArrayList<>();
        entries.add(new ValueDataEntry(listeJour.get(0), 500));
        entries.add(new ValueDataEntry(listeJour.get(1), 10562));
        entries.add(new ValueDataEntry(listeJour.get(2), 7802));
        entries.add(new ValueDataEntry(listeJour.get(3), 2155));
        entries.add(new ValueDataEntry(listeJour.get(4), 21502));
        entries.add(new ValueDataEntry(listeJour.get(5), 650));
        entries.add(new ValueDataEntry(listeJour.get(6), 8421));
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