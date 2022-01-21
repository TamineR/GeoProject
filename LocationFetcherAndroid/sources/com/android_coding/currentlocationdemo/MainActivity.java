package com.android_coding.currentlocationdemo;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    Button btLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView tvLatitude;
    TextView tvLongitude;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0290R.layout.activity_main);
        this.btLocation = (Button) findViewById(C0290R.C0292id.bt_location);
        this.tvLatitude = (TextView) findViewById(C0290R.C0292id.tv_latitude);
        this.tvLongitude = (TextView) findViewById(C0290R.C0292id.tv_longitude);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        this.btLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(MainActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                    MainActivity.this.getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 100);
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] + grantResults[1] == 0) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied.", 0).show();
        }
    }

    /* access modifiers changed from: private */
    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService("location");
        if (locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network")) {
            this.fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                public void onComplete(Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        MainActivity.this.tvLatitude.setText(String.valueOf(location.getLatitude()));
                        MainActivity.this.tvLongitude.setText(String.valueOf(location.getLongitude()));
                        return;
                    }
                    MainActivity.this.fusedLocationProviderClient.requestLocationUpdates(new LocationRequest().setPriority(100).setInterval(10000).setFastestInterval(1000).setNumUpdates(1), new LocationCallback() {
                        public void onLocationResult(LocationResult locationResult) {
                            Location location1 = locationResult.getLastLocation();
                            MainActivity.this.tvLatitude.setText(String.valueOf(location1.getLatitude()));
                            MainActivity.this.tvLongitude.setText(String.valueOf(location1.getLongitude()));
                        }
                    }, Looper.myLooper());
                }
            });
        } else {
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS").setFlags(268435456));
        }
    }
}
