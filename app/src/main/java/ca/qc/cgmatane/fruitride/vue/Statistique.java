package ca.qc.cgmatane.fruitride.vue;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.ControleurStatistique;


public class Statistique extends AppCompatActivity implements VueStatistique {

    private AnyChartView columnChart;

    protected ControleurStatistique controleurStatistique = new ControleurStatistique(this);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_statistique);

        Button boutonAccueil = findViewById(R.id.boutonAccueil);
        boutonAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleurStatistique.actionNaviguerAccueil();
            }
        });

        Button boutonImageAccueil = findViewById(R.id.boutonLogoAccueil);
        boutonImageAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleurStatistique.actionNaviguerAccueil();
            }
        });

        controleurStatistique.onCreate(getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initialiserGraphique() {

        ArrayList<String> listeJour = controleurStatistique.recupererListeJourDeLaSemainePasser();

        columnChart = (AnyChartView)findViewById(R.id.columnChart);
        columnChart.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesien = AnyChart.column();

        Column colonne = cartesien.column(controleurStatistique.getData(listeJour));
        colonne.tooltip()
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .background("#006400")
        .title(true)
        ;

        cartesien.animation(true);
        cartesien.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesien.interactivity().hoverMode(HoverMode.BY_X);
        cartesien.title("Vos pas cette semaine");
        cartesien.lineMarker("#006400");

        columnChart.setChart(cartesien);
    }

    public void naviguerAccueil() {
        Intent retourAccueil = new Intent(this, Accueil.class);
        startActivity(retourAccueil);
        this.finish();
    }
}