package com.example.easyelectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.HashMap;
import java.util.Map;


public class profile_setup extends AppCompatActivity {


    public static final String TAG = "TAG ";
    EditText FullName,UserName,Phone;
    Button btn_submit;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String userId;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);


        
        FullName=(EditText) findViewById(R.id.fullName);
        UserName=(EditText) findViewById(R.id.userName);
        Phone=(EditText)findViewById(R.id.phone);
        btn_submit=(Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname,username,phone;
                fullname =FullName.getText().toString();
                username =UserName.getText().toString();
                phone =Phone.getText().toString();
                fAuth=FirebaseAuth.getInstance();
                fstore=FirebaseFirestore.getInstance();

                userId= fAuth.getCurrentUser().getUid();

                DocumentReference documentReference= fstore.collection("users").document(userId);
                Map<String,Object> user= new HashMap<>();
                user.put("fullName",fullname);
                user.put("userName",username);
                user.put("phone",phone);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user profile is created");

                        startActivity(new Intent(getApplicationContext(),profileSection.class));
                        finish();
                    }
                });
            }
        });









    }
}
