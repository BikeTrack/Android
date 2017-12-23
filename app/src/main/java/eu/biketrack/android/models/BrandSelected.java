package eu.biketrack.android.models;

/**
 * Created by 42900 on 01/07/2017 for BikeTrack_Android.
 */

public class BrandSelected {
    private static final BrandSelected ourInstance = new BrandSelected();

    static BrandSelected getInstance() {
        return ourInstance;
    }

    private String brand;

    private BrandSelected() {
    }

    public static BrandSelected getOurInstance() {
        return ourInstance;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void clear(){
        this.brand = null;
    }
}
