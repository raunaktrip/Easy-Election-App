package com.example.easyelectionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.easyelectionapp.R.layout.list_item_layout;

public class homeActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

   private ListAdapter adapter;
    FirebaseAuth firebaseAuth;
    String myText ,pwd,roomId;
    ImageView btnplus;
    private Button btnnav;
    FirebaseFirestore firebaseFirestore;
    String userId,eml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.rec1);
        btnplus = findViewById(R.id.btnadd);
        btnnav = findViewById(R.id.navigation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        userId= firebaseAuth.getCurrentUser().getUid();

        firebaseAuth=FirebaseAuth.getInstance();
        eml=firebaseAuth.getCurrentUser().getEmail();






        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //query
        Query query= firebaseFirestore.collection("users").document(eml).collection("rooms");

        FirestoreRecyclerOptions<ListItem> options= new FirestoreRecyclerOptions.Builder<ListItem>()
                .setQuery(query,ListItem.class)
                .build();
        adapter= new ListAdapter(options);


        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                String roomname=documentSnapshot.getData().get("roomName").toString();



                Intent intent = new Intent(getApplicationContext(),roomActivity.class);
                intent.putExtra("roomid",id);
                intent.putExtra("roomname",roomname);
                startActivity(intent);

            }
        });



        //onclick for logout button
        btnnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(homeActivity.this,profileSection.class);

                intent.putExtra("keyeml",eml);


                startActivity(intent);




            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(homeActivity.this);
                dialog.setTitle("Add Room");


              LinearLayout linearLayout= new LinearLayout(homeActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText roomname = new EditText(homeActivity.this);
                roomname.setHint("Room Name");
                final EditText roomid = new EditText(homeActivity.this);
                roomid.setHint("RoomId");
                final EditText password = new EditText(homeActivity.this);
                password.setHint("Password");

               linearLayout.addView(roomname);
               linearLayout.addView(password);
               linearLayout.addView(roomid);

               dialog.setView(linearLayout);


                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        myText = roomname.getText().toString();
                        pwd=password.getText().toString();
                        roomId=roomid.getText().toString();

                        // adding new room
                       final DocumentReference documentReference= firebaseFirestore.collection("users").document(eml).collection("rooms").document(roomId);
                        Map<String,Object> user= new HashMap<>();
                        user.put("roomName",myText);
                        user.put("memberCount","1");
                        user.put("electionCount","1");
                        user.put("password",pwd);
                        user.put("roomId",roomId);
                        documentReference.set(user);






                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                });
                dialog.show();


            }


        });




    }




    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}