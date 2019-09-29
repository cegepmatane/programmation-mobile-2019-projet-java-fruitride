package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.donnee.FruitDAO;
import ca.qc.cgmatane.fruitride.modele.Fruit;
import ca.qc.cgmatane.fruitride.modele.ListenerSwipe;


public class VueCarte extends AppCompatActivity implements OnMapReadyCallback {

    private MapView vueCarteElementMapView;
    private GoogleMap googleMap;
    private FruitDAO accesseurFruit;
    private List<Fruit> listeFruit;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_carte);

        accesseurFruit = FruitDAO.getInstance();
        listeFruit = accesseurFruit.recupererListeFruit();

        // chargement des markers personnalisés pour les fruits
        // pour l'instant obligé dans une classe Activity
        for (Fruit fruit : listeFruit) {
            fruit.setLogo(BitmapFactory.decodeResource(getResources(), fruit.getIdResourceLogo()));
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        vueCarteElementMapView = findViewById(R.id.vue_carte_element_map_view);
        vueCarteElementMapView.onCreate(mapViewBundle);
        vueCarteElementMapView.getMapAsync(this);

        Button bouton = findViewById(R.id.button2);

        final Intent intentionNaviguerVuePrincipale = new Intent(this, Accueil.class);

        findViewById(R.id.layout).setOnTouchListener(new ListenerSwipe(VueCarte.this) {
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {
                startActivity(intentionNaviguerVuePrincipale);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(48.840981, -67.497192);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));

        for (Fruit fruit : listeFruit) {
            this.googleMap.addMarker(fruit.getMarkerFruit());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        vueCarteElementMapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        vueCarteElementMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        vueCarteElementMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vueCarteElementMapView.onStop();
    }
    @Override
    protected void onPause() {
        vueCarteElementMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        vueCarteElementMapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        vueCarteElementMapView.onLowMemory();
    }
}
