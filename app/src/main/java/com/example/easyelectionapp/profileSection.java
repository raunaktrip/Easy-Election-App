package com.example.easyelectionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class profileSection extends AppCompatActivity {
    Button btnlogout;
    TextView fullName,userName,Phone ,email;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String eml;
    String fnm,usnm,phnm;
    ListenerRegistration listenerRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_section);
        btnlogout= findViewById(R.id.btnLogout);
        fullName=findViewById(R.id.fullName_ps);
        userName=findViewById(R.id.userName_ps);
        Phone=findViewById(R.id.phone_ps);
        email=findViewById(R.id.email_ps);

         fstore=FirebaseFirestore.getInstance();

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        fstore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e !=null){

                }
                for (DocumentChange documentChange :queryDocumentSnapshots.getDocumentChanges()){
                    fnm= documentChange.getDocument().getData().get("fullName").toString();
                    usnm= documentChange.getDocument().getData().get("userName").toString();
                    phnm= documentChange.getDocument().getData().get("phone").toString();




                    firebaseAuth=FirebaseAuth.getInstance();
                    eml=firebaseAuth.getCurrentUser().getEmail();

                    email.setText(eml);

                    fullName.setText(fnm);


                    userName.setText(usnm);


                    Phone.setText(phnm);

                    detachListener();


    }




    }
});

    }
    public void detachListener() {
        // [START detach_listener]
        Query query = fstore.collection("users");
        ListenerRegistration registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    // [START_EXCLUDE]
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        // ...
                    }
                    // [END_EXCLUDE]
                });

        // ...

        // Stop listening to changes
        registration.remove();
        // [END detach_listener]
    }
}
