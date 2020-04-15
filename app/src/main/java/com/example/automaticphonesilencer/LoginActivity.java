package com.example.automaticphonesilencer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button loginid,menuButtonId;                                                                    //DECLARING BUTTON VARIABLE FOR ACCESSING LOGIN AND GO BACK MENU
    EditText email,password;                                                                        //EDIT TEXT FOR TAKING EMAIL AND PASSWORD FROM USER FOR LOGIN
    private FirebaseAuth auth;                                                                      //FIREBASE AUTH VARIABLE FOR GETTING AUTHENTICATION SERVICES FROM FIREBASE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
                                                                                                    //ACCESSING ALL THE BUTTON , EDIT TEXT AND TEXT VIEW OF XML FILE

        loginid = (Button)findViewById(R.id.loginid);
        email = (EditText)findViewById(R.id.loginemailid);
        password = (EditText)findViewById(R.id.loginpasswordid);
        menuButtonId = (Button) findViewById(R.id.menubuttonid);

        auth = FirebaseAuth.getInstance();

        loginid.setOnClickListener(new View.OnClickListener() {                                     //FOR LOGIN INTO USER ACCOUNT
            @Override
            public void onClick(View v) {
                String email_txt,password_txt;
                email_txt = email.getText().toString();
                password_txt = password.getText().toString();
                if(password_txt.length()<6){
                    Toast.makeText(LoginActivity.this, "Please give a valid password", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(email_txt,password_txt);
                }


                
            }
        });


        menuButtonId.setOnClickListener(new View.OnClickListener() {                                //FOR GO BACK TO START MENU
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,StartActivity.class));
                finish();
            }
        });



    }
                                                                                                    //TRYING TO SIGN IN VIA EMAIL AND PASSWORD BY FIREBASE AUTHENTICATION SERVICE
    private void loginUser(String email_txt, String password_txt) {
        auth.signInWithEmailAndPassword(email_txt,password_txt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();

            }
        });
    }
}
