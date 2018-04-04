package com.mobileappdev.teamone.meetup;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mobileappdev.teamone.meetup.DbRepository.Repository;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LocationSync extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SYNC_LOCATION = "com.mobileappdev.teamone.meetup.action.SYNC_LOCATION";
    private static final String ACTION_BAZ = "com.mobileappdev.teamone.meetup.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.mobileappdev.teamone.meetup.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.mobileappdev.teamone.meetup.extra.PARAM2";

    public LocationSync() {
        super("LocationSync");
    }


    FusedLocationProviderClient fusedLocationProviderClient;
    Timer t = new Timer();

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSyncLocation(Context context, String param1, String param2) {
        Intent intent = new Intent(context, LocationSync.class);
        intent.setAction(ACTION_SYNC_LOCATION);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, LocationSync.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SYNC_LOCATION.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSyncLocation(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action LocationSync in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncLocation(String param1, String param2) {
        // TODO: Handle action Foo

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();

                lastLocation.addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            SharedPreferences defaultSharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);

                            int user_id = defaultSharedPreferences.getInt("user_id", -1);
                            if (user_id > -1) {
                                (new Repository()).UpdateUserPosition(user_id, location.getLatitude(), location.getLongitude());
                                defaultSharedPreferences.edit().putFloat("user_lat", (float) location.getLatitude()).putFloat("user_lng", (float) location.getLongitude()).apply();
                            }
                        }
                    }
                );
            }
        }, 1000, 30000);
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
