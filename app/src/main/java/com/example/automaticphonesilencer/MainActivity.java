package com.example.automaticphonesilencer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    Button scanLocation,saveLocation,mysavedLocations,logOut;
    Button  silentMode, vibrateMode, ringerMode;
    TextView latitudeId;
    TextView longitudeId;
    TextView txtLocation;
    EditText placeNameId;

    Double latitude,longitude,wayLatitude,wayLongitude;


    AudioManager am;

    private FusedLocationProviderClient client, mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);
        latitudeId = (TextView) findViewById(R.id.textViewId1);
        longitudeId = (TextView) findViewById(R.id.textViewId2);
        silentMode = (Button) findViewById(R.id.button1);
        vibrateMode = (Button) findViewById(R.id.button2);
        ringerMode = (Button) findViewById(R.id.button3);
        am = (AudioManager) getSystemService(getApplication().AUDIO_SERVICE);
        scanLocation = (Button) findViewById(R.id.getLocation);
        saveLocation =(Button) findViewById(R.id.saveLocationid);
        placeNameId = (EditText) findViewById(R.id.placeNameId);
        placeNameId.setText(null);
        mysavedLocations = (Button) findViewById(R.id.savedLocationsId);
        logOut = (Button)findViewById(R.id.logoutid) ;

        txtLocation = (TextView)findViewById(R.id.textLocationId);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);




        silentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                catch(Exception e){

                }

            }
        });
        vibrateMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        });
        ringerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        });




        scanLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {

                        if(location!=null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            latitudeId.setText(Double.toString(location.getLatitude()));
                            longitudeId.setText(Double.toString(location.getLongitude()));

                        }

                    }
                });
            }
        });

            saveLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (latitude ==null&&longitude ==null){
                        latitude =120.22789;
                        longitude= 90.2356897;
                    }
                    LocationScan lscan = new LocationScan(latitude,longitude);
                    if (TextUtils.isEmpty(placeNameId.getText().toString())){
                        Toast.makeText(MainActivity.this,"Please provide Place name",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        FirebaseDatabase.getInstance().getReference().child("Places").child(placeNameId.getText().toString()).setValue(lscan);
                    }
                }
            });


            mysavedLocations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this,MySavedLocations.class));
                    finish();
                }
            });

            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,StartActivity.class));
                    finish();
                }
            });


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        txtLocation.setText(String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));


                    }
                }
            }
        };
    }


    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }
}
