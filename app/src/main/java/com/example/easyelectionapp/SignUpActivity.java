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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {

    EditText email,password;
    TextView tvsignIn;
    Button btnsignUp;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email=findViewById(R.id.email);
        password= findViewById(R.id.password);
        tvsignIn=findViewById(R.id.sigIn);
        btnsignUp=findViewById(R.id.signUp);

        firebaseAuth=FirebaseAuth.getInstance();

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();

                }
                if(Password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();

                }

                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            startActivity(new Intent(getApplicationContext(),profile_setup.class));
                            finish();
                        }
                        if(!task.isSuccessful()){
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"User Already exists ,try to Login",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        tvsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }


}
