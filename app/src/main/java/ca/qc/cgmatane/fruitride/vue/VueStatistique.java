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

import ca.qc.cgmatane.fruitride.R;

public class VueStatistique extends AppCompatActivity {
    private AnyChartView columnChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_statistique);
        columnChart = (AnyChartView)findViewById(R.id.columnChart);
        columnChart.setProgressBar(findViewById(R.id.progress_bar));
        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(getData());
        column.tooltip()
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM);
        cartesian.animation(true);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.title("Votre nombre de pas cette semaine");
        columnChart.setChart(cartesian);
    }

    private ArrayList getData(){
        ArrayList<DataEntry> entries = new ArrayList<>();
        entries.add(new ValueDataEntry("lun", 500));
        entries.add(new ValueDataEntry("mar", 10562));
        entries.add(new ValueDataEntry("mer", 7802));
        entries.add(new ValueDataEntry("jeu", 2155));
        entries.add(new ValueDataEntry("ven", 21502));
        entries.add(new ValueDataEntry("sam", 650));
        entries.add(new ValueDataEntry("dim", 8421));
        return entries;
    }
}