package com.example.automaticphonesilencer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

        EditText email,password;
        Button register;
        private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.emailid);
        password= (EditText)findViewById(R.id.passwordid);
        register = (Button)(findViewById(R.id.registerid2));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email,txt_password;
                txt_email = email.getText().toString();
                txt_password = password.getText().toString();
                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password))
                    Toast.makeText(RegisterActivity.this,"Empty credentials",Toast.LENGTH_LONG).show();
                else if(txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    registerUser(txt_email,txt_password);
                }

            }
        });


    }

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
