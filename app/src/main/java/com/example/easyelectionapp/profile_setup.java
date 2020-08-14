package com.example.easyelectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private static final String KEY_FULLNAME ="fullName";
    private static final String KEY_USERNAME ="userName";
    private static final String KEY_PHONE ="phone";
    String email,fullName,userName,PHONE;









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
                email=fAuth.getCurrentUser().getEmail();

                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }



                final DocumentReference documentReference= fstore.collection("users").document(email);
                Map<String,Object> user= new HashMap<>();
                user.put(KEY_FULLNAME,fullname);
                user.put(KEY_USERNAME,username);
                user.put(KEY_PHONE,phone);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user profile is created");



                    }
                });
                documentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                      fullName= documentSnapshot.getString(KEY_FULLNAME);
                                      userName= documentSnapshot.getString(KEY_USERNAME);
                                      PHONE= documentSnapshot.getString(KEY_PHONE);

                                }
                            }
                        });

               Intent intent =new Intent(profile_setup.this,homeActivity.class);

               startActivity(intent);
                finish();

            }
        });









    }
}
