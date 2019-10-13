package ca.qc.cgmatane.fruitride.modele;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.qc.cgmatane.fruitride.R;

public class Fruit {

    public static final String CLE_ID_FRUIT = "id_fruit";

    protected int idFruit;
    protected double latitude;
    protected double longitude;
    protected Bitmap logo;
    protected String typeDuFruit;
    protected int idResourceLogo;

    protected static final int REDUCTION = 20;

    private static final String APPLE = "apple";

    public Fruit(double latitude, double longitude, String typeDuFruit) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeDuFruit = typeDuFruit;
        switch (typeDuFruit) {
            case "apple":
                this.idResourceLogo = R.drawable.apple;
                break;
            case "pear":
                this.idResourceLogo = R.drawable.pear;
                break;
            case "pineapple":
                this.idResourceLogo = R.drawable.pineapple;
                break;
            default:
                this.idResourceLogo = R.drawable.orange;
        }

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getIdFruit() {
        return idFruit;
    }

    public void setIdFruit(int idFruit) {
        this.idFruit = idFruit;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = Bitmap.createScaledBitmap(logo, logo.getWidth() / REDUCTION,
                logo.getHeight() / REDUCTION, false);
    }

    public int getIdResourceLogo() {
        return idResourceLogo;
    }

    public void setIdResourceLogo(int idResourceLogo) {
        this.idResourceLogo = idResourceLogo;
    }

    public MarkerOptions getMarkerFruit() {
        return new MarkerOptions().position(
                new LatLng(this.getLatitude(), this.getLongitude())
                            ).icon(BitmapDescriptorFactory.fromBitmap(this.getLogo()));
    }
}
