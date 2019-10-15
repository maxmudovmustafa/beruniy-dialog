package firbase.go.beruniy.location;

import android.location.LocationListener;
import android.os.Bundle;

public abstract class LocationResult implements LocationListener {

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}