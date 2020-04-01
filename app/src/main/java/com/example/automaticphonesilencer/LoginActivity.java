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

    Button loginid;
    EditText email,password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginid = (Button)findViewById(R.id.loginid);
        email = (EditText)findViewById(R.id.loginemailid);
        password = (EditText)findViewById(R.id.loginpasswordid);

        auth = FirebaseAuth.getInstance();

        loginid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt,password_txt;
                email_txt = email.getText().toString();
                password_txt = password.getText().toString();
                loginUser(email_txt,password_txt);

                
            }
        });




    }

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
