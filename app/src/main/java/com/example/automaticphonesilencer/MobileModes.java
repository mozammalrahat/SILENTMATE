package com.example.automaticphonesilencer;


import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

public class MobileModes extends AppCompatActivity {

    CardView silentMode, vibrateMode, ringerMode;
    Button home;
    private AudioManager audioManager;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_modes);
        silentMode = (CardView) findViewById(R.id.silentmodeid);
        vibrateMode = (CardView) findViewById(R.id.vibratemodeid);
        ringerMode = (CardView) findViewById(R.id.ringermodeid);
        audioManager = (AudioManager) getSystemService(getApplication().AUDIO_SERVICE);
        home = (Button)findViewById(R.id.homeid);
        title = (TextView)findViewById(R.id.titleid);
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        title.setTypeface(MLight);

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
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileModes.this,MainActivity.class));
                finish();
            }
        });
    }
}
