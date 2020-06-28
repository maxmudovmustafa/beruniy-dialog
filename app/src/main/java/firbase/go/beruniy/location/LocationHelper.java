package firbase.go.beruniy.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresPermission;

@SuppressWarnings("MissingPermission")
public class LocationHelper {

    public static final int DEFAULT_RESTART_LOCATION = 5 * 1000;// 5 second

    public static LocationHelper getOneLocation(Context context, LocationResult listener) {
        return new LocationHelper(context, listener, false, DEFAULT_RESTART_LOCATION);
    }

    public static LocationHelper getOneLocation(Context context, LocationResult listener, int restartLocation) {
        return new LocationHelper(context, listener, false, restartLocation);
    }

    public static LocationHelper getTrackingLocation(Context context, LocationResult listener) {
        return new LocationHelper(context, listener, true, DEFAULT_RESTART_LOCATION);
    }

    public static LocationHelper getTrackingLocation(Context context, LocationResult listener, int restartLocation) {
        return new LocationHelper(context, listener, true, restartLocation);
    }

    private final Context context;
    private final LocationResult listener;
    private final boolean tracking;
    private final int restartLocation;

    private final LocationManager lm;
    private final LocationListener satellite;
    private final LocationListener network;

    private final Handler handler;

    private LocationHelper(Context context, LocationResult listener, boolean tracking, int restartLocation) {

        this.context = context;
        this.listener = listener;
        this.tracking = tracking;
        this.restartLocation = restartLocation;

        this.lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.satellite = getLocationListener();
        this.network = getLocationListener();
        this.handler = new Handler();
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    public boolean startListener() {
        stopListener();
        handler.postDelayed(RUNNABLE, restartLocation);
        return startLocationListener();
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    public void stopListener() {
        handler.removeCallbacks(RUNNABLE);
        lm.removeUpdates(satellite);
        lm.removeUpdates(network);
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    private boolean startLocationListener() {
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gps_enabled && !network_enabled) {
            return false;
        }

        if (gps_enabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, satellite);
        }

        if (network_enabled) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, network);
        }

        return true;
    }

    public LocationListener getLocationListener() {
        return new LocationListener() {

            @SuppressWarnings("MissingPermission")
            public void onLocationChanged(Location location) {
                if (!tracking) {
                    stopListener();
                }
                listener.onLocationChanged(location);// вызываем callback метод,
            }

            public void onProviderDisabled(String provider) {
                listener.onProviderDisabled(provider);
            }

            public void onProviderEnabled(String provider) {
                listener.onProviderEnabled(provider);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                listener.onStatusChanged(provider, status, extras);
            }
        };
    }

    private final Runnable RUNNABLE = new Runnable() {
        @Override
        @SuppressWarnings("MissingPermission")
        public void run() {
            startListener();
        }
    };
}
