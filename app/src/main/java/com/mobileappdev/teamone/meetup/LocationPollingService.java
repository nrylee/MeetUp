package com.mobileappdev.teamone.meetup;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mobileappdev.teamone.meetup.DbRepository.Repository;

import java.util.concurrent.Executor;

public class LocationPollingService extends Service implements Executor {
    public LocationPollingService() {
    }

    private Handler mHandler;
    private FusedLocationProviderClient mFusedLocationClient;

    public static final long DEFAULT_SYNC_INTERVAL = 30 * 1000;

    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            syncLocation();
            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mHandler.post(runnableService);

        return START_STICKY;
    }

    private synchronized void syncLocation() {
        // call your rest service here
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int user_id = defaultSharedPreferences.getInt("user_id", -1);
        if (user_id > -1) {
            Task<Location> lastLocation = mFusedLocationClient.getLastLocation();

            lastLocation.addOnSuccessListener(
                    this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                            int user_id = defaultSharedPreferences.getInt("user_id", -1);
                            if (user_id > -1) {
                                (new Repository()).UpdateUserPosition(user_id, location.getLatitude(), location.getLongitude());
                            }
                        }
                    }
            );
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void execute(@NonNull Runnable command) {
        command.run();
    }
}
