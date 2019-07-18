package com.example.todolist_ramkumartextiles.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleAPIClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderApi mLocationProvider;
    private int UPDATE_INTERVAL = 10000;
    private int FASTEST_INTERVAL = 2000;
    private Location mLocation;
    private LocationManager mLocationManager;
    private com.google.android.gms.location.LocationListener listener;



    final class LocationThread implements Runnable{
        int service_id;

        public LocationThread(int service_id) {
            this.service_id = service_id;
        }


        @Override
        public void run() {
            mGoogleAPIClient.connect();
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleAPIClient.isConnected()) {
            mGoogleAPIClient.disconnect();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mGoogleAPIClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new LocationThread(startId));
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleAPIClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.d("Background Location ", "::::***********Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        DatabaseReference refLocation = FirebaseDatabase.getInstance().getReference("Users").child(username);
        refLocation.child("latitude").setValue(String.valueOf(location.getLatitude()));
        refLocation.child("longitude").setValue(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleAPIClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleAPIClient,
                mLocationRequest, this);

    }
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }
}