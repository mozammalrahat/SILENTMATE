package com.example.automaticphonesilencer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    Button register,login;                                                                          //DECLARING BUTTON FOR REGISTER ACTIVITY AND LOGIN ACTIVITY
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

                                                                                                    //FINDING ALL BUTTONS FROM XML FILE
        register = (Button) findViewById(R.id.registerid);
        login  = (Button)findViewById(R.id.login);
        title =(TextView)findViewById(R.id.titleid);

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        title.setTypeface(MLight);
                                                                                                    //GOING TO USER REGISTER PAGE
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
                finish();
            }
        });
                                                                                                    //GOING T0 USER LOGIN PAGE
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
                finish();
            }
        });










    }
}
