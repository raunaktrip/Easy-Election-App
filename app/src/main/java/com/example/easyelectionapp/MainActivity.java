package com.example.easyelectionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    EditText email,password;
    TextView tvfpwd,tvsignUp;
    Button btnlogin;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.email);
        password= findViewById(R.id.password);
        tvfpwd= findViewById(R.id.fpwd);
        tvsignUp=findViewById(R.id.signUp);
        btnlogin=findViewById(R.id.login);
        firebaseAuth=FirebaseAuth.getInstance();

        tvsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });

       tvfpwd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),passwordReset.class));
           }
       });

       btnlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loginUserAccount();

           }
       });
       if(firebaseAuth.getCurrentUser()!=null){
           startActivity(new Intent(getApplicationContext(),homeActivity.class));
           finish();
       }


        }


        private void loginUserAccount(){

        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if(TextUtils.isEmpty(Email)){
            Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
           return;
        }
            if(TextUtils.isEmpty(Password)){
                Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                  return;
            }

            firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),homeActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Login failed!,Please try again",Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }


}
