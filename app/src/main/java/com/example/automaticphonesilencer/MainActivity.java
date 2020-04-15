package com.example.automaticphonesilencer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    //DECLARING VARIABLES

    private Button scanLocation,saveLocation, mySavedLocations;                                     //DECLARING BUTTON FOR FINDING LOCATION AND SAVE LOCATIONS
    private Button logOut;
    private Button startService,stopService;                                                        //DECLARING BUTTON FOR AUTOMATIC SILENT SERVICE ON AND OFF
    private Button silentMode, vibrateMode, ringerMode;                                             //DECLARING BUTTON FOR ACCESSING DIFFERENT TYPES OF AUDIO MANAGER
    private TextView latitudeId, longitudeId,txtLocation;                                           //DECLARING TEXT VIEW FOR SHOWING SCANNED CURRENT LATITUDE AND LONGITUDE
    private EditText placeNameId;                                                                   //DECLARING EDIT TEXT FOR LOCATION WE WANT TO SAVE
    private Geocoder geocoder;                                                                      //DECLARING GEOCODER REFERENCE VARIABLE
    private TextView addresstext;                                                                   //DECLARING TEXT VIEW FOR SHOWING AUTOMATIC SCAN PLACES
    List<Address> addressList;                                                                      //DELACRING A LIST FOR SAVING THE CURRENT ADDRESS SEQUENTIALLY,EX: IICT->SUST_->SYLHET->BANGLADESH

    private Handler mHandler = new Handler();                                                       //DECLARING HANDLER FOR CONSEQUENTLY CHECK ADDRESS IF IT IS IN THE DATABASE


    private Double latitude=24.917887,longitude=91.830891, wayLatitude=24.917887,wayLongitude=91.830891; //VARIABLE FOR SAVING VALUES OF MANUALLY SACNNED LATITUDE AND LONGITUDE AND AUTOMATIC SCANNED LATITUDE AND LONGITUDE


    private AudioManager audioManager;                                                              //REFERENCE VARIABLE FOR ACCESSING SERVICES OF AUDIO MANAGER


    private FusedLocationProviderClient client, mFusedLocationClient;                               //FOR GETTING CLIENT LAST LOCATION
    private LocationRequest locationRequest;                                                        //REQUESTING SERVER FOR LAST LOCATION
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                                                                                                    //ASKING PERMISSION FOR LOCATION ACCESSING

        requestPermission();

                                                                                                    //ACCESSING ALL THE BUTTON , EDIT TEXT AND TEXT VIEW OF XML FILE

        client = LocationServices.getFusedLocationProviderClient(this);
        latitudeId = (TextView) findViewById(R.id.textViewId1);
        longitudeId = (TextView) findViewById(R.id.textViewId2);
        silentMode = (Button) findViewById(R.id.button1);
        vibrateMode = (Button) findViewById(R.id.button2);
        ringerMode = (Button) findViewById(R.id.button3);
        audioManager = (AudioManager) getSystemService(getApplication().AUDIO_SERVICE);
        scanLocation = (Button) findViewById(R.id.getLocation);
        saveLocation =(Button) findViewById(R.id.saveLocationid);
        placeNameId = (EditText) findViewById(R.id.placeNameId);
        addresstext = (TextView)findViewById(R.id.addressid);
        placeNameId.setText(null);
        mySavedLocations = (Button) findViewById(R.id.savedLocationsId);
        logOut = (Button)findViewById(R.id.logoutid) ;
        startService = (Button) findViewById(R.id.startserviceid);
        stopService = (Button)findViewById(R.id.stopserviceid);
        txtLocation = (TextView)findViewById(R.id.textLocationId);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



                                                                                                    // ACCESSING SILENT MODE
        silentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                catch(Exception e){

                }

            }
        });

                                                                                                    //ACCESSING VIBRATE MODE
        vibrateMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        });
                                                                                                    //ACCESSING RINGER MODE
        ringerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        });



                                                                                                    //SCANNING LOCATION FOR SAVING INTO DATABASE
        scanLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {

                        if(location!=null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            latitudeId.setText(Double.toString(latitude));
                            longitudeId.setText(Double.toString(longitude));

                        }

                    }
                });
            }
        });


                                                                                                    //SAVING THE LOCATION INTO DATABASE WITH PLACE NAME

            saveLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (latitude ==null&&longitude ==null){
                        latitude =24.917887;
                        longitude= 91.830891;
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


                                                                                                    //FOR ACCESSING  OUR PREVIOUS SAVED LOCATIONS

            mySavedLocations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this,MySavedLocations.class));
                    finish();
                }
            });


                                                                                                    //LOGOUT BUTTON FOR USER LOGOUT

            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,StartActivity.class));
                    finish();
                }
            });


                                                                                                     //FOR CONTINUOUSLY UPDATING OUR CURRENT LOCATIONS AFTER 20 SEC CONSECUTIVELY


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


        geocoder = new Geocoder(this,Locale.getDefault());                                   //DECLARING GEOCODER OBJECT FOR ACCESSING LOCATION NAME

                                                                                                     //ACCESSING CURRENT LOCATION ADDRESS
        try {
            addressList = geocoder.getFromLocation(wayLatitude,wayLongitude,1);
            String add = addressList.get(0).getAddressLine(0);
            String area = addressList.get(0).getLocality();
            String city = addressList.get(0).getAdminArea();
            String country = addressList.get(0).getCountryName();
            String full= add+" "+area+" "+city+" "+country;
            addresstext.setText(full);

        } catch (Exception e) {
            Toast.makeText(this, "Current Address is Unavailable", Toast.LENGTH_SHORT).show();
        }




                                                                                                    //START AUTOMATIC PHONE SILENCING SERVICE BASED ON YOUR CURRENT LOCATION

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRepeating(v);
            }
        });

                                                                                                    //STOP AUTOMATIC PHONE SILENCING SERVICE

        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRepeating(v);
            }
        });


    }


                                                                                                    //ACCESSING PERMISSION FOR CURRENT LOCATION

    private void requestPermission(){

        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }


                                                                                                    //FOR REPEATING THE AUTOMATIC SCAN AFTER 15 SEC CONSECUTIVELY
    public void startRepeating(View v){
            //mhandler.postDelayed(mToastRunnable,5000);
        mToastRunnable.run();
    }


                                                                                                    //FOR STOPPING THE AUTOMATIC SCAN AFTER 15 SEC CONSECUTIVELY
    public void stopRepeating(View v){
        mHandler.removeCallbacks(mToastRunnable);
    }

                                                                                                    //DECLARING RUNNABLE OBJECT AND OVERRIDE THE RUN METHOD
    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MainActivity.this, "Activity has been started", Toast.LENGTH_SHORT).show();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Places");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        LocationScan locationScan =snapshot.getValue(LocationScan.class);
                       if(locationScan.getLatitude()==wayLatitude&&locationScan.getLongitude()==wayLongitude){
                           Toast.makeText(MainActivity.this, "Silent Mode is On", Toast.LENGTH_SHORT).show();
                           try {
                               audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                           }
                           catch(Exception e){

                           }
                           Intent intent = new Intent(MainActivity.this, AlarmBroadcastReceiver.class);
                           PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this.getApplicationContext(),0,intent,0);
                            AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
                           Toast.makeText(MainActivity.this, "Alarm is on", Toast.LENGTH_SHORT).show();

                       }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mHandler.postDelayed(this,15000);                                           //DECLARING TIME INTERVAL BETWEEN TWO CONSECUTIVE SCAN
        }
    };



}
