package com.example.thirdwayv_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class TrackingService extends Service implements LocationListener {

    private static final float MIN_DISTANCE = 10;           // 10 meters
    private static final long MIN_TIME = 10000;             // 10 seconds
    public static final String UPDATE_UI = "update_ui";

    private Location mLastLocation;
    public static float distanceCovered;
    private boolean isServiceRunning;
    LocationManager locationManager;
    boolean recordLocations;
    ArrayList<Location> locations;





    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        recordLocations = false;


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isServiceRunning) {
            isServiceRunning = true;
            trackLocation();
        }
        return START_STICKY;
    }

    public void stopUsingGPS(){
        if( locationManager!= null){
            locationManager.removeUpdates(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void trackLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Criteria criteria = new Criteria();
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        locationManager.requestLocationUpdates(MIN_TIME, MIN_DISTANCE, criteria, this, getMainLooper());
    }

    @Override
    public void onLocationChanged(Location location) {

        if(location!=null) {
            if (mLastLocation == null) {
                mLastLocation = location;
            } else {
                float[] distanceResults = new float[1];
                Location.distanceBetween(mLastLocation.getLatitude(),
                        mLastLocation.getLongitude(), location.getLatitude(), location.getLongitude(), distanceResults);
                distanceCovered += distanceResults[0]/1000;
                Toast.makeText(this, "Distance covered : " + distanceResults[0], Toast.LENGTH_SHORT).show();

                mLastLocation = location;
                Intent intent = new Intent(UPDATE_UI);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(this, "Distance speed : " + location.getSpeed(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
