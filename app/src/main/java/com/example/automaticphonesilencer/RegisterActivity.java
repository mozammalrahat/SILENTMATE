package com.example.automaticphonesilencer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
                                                                                                    //DECLARING VARIABLES
       private EditText email,password,username,confirm_password;                                   //EDIT TEXT VARIABLE FOR TAKING INPUT OF USER NAME EMAIL PASSWORD AND CONFIRM PASSWORD
       private Button register,menuButtonId;
       private FirebaseAuth auth;                                                                  //FIREBASE AUTH VARIABLE FOR CREATING USER ACCOUNT WITH EMAIL AND PASSWORD
       private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();                                                          //GETTING INSTANCES FROM FIREBASE

        username = (EditText)findViewById(R.id.usernameid);                                         //ACCESSING ALL THE BUTTON , EDIT TEXT AND TEXT VIEW OF XML FILE
        email = (EditText)findViewById(R.id.emailid);
        password= (EditText)findViewById(R.id.passwordid);
        confirm_password = (EditText)findViewById(R.id.confirm_passwordid);
        menuButtonId = findViewById(R.id.registermenubuttonid);
        register = (Button)(findViewById(R.id.registerid2));
        title = (TextView)findViewById(R.id.titleid);

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        title.setTypeface(MLight);


                                                                                                    //REGISTERING INTO DATABASE WITH EMAIL AND PASSWORD CALLING THE REGISTER_USER METHOD
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email,txt_password;
                txt_email = email.getText().toString();
                txt_password = password.getText().toString();
                String txt_confirm_password = confirm_password.getText().toString();
                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)||TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(confirm_password.getText().toString()))
                    Toast.makeText(RegisterActivity.this,"Empty credentials",Toast.LENGTH_LONG).show();
                else if(txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else if(!txt_confirm_password.equals(txt_password)){
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    registerUser(txt_email,txt_password);
                }

            }
        });

                                                                                                    //FOR GO BACK TO START MENU
        menuButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,StartActivity.class));
                finish();
            }
        });


    }
                                                                                                    //REGISTERING INT0 DATABASE
    private void registerUser(String txt_email, String txt_password) {

        auth.createUserWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}
