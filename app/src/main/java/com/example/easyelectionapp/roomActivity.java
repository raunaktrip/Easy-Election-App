package com.example.easyelectionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.easyelectionapp.R.layout.itemlist_room;
import static com.example.easyelectionapp.R.layout.list_item_layout;

public class roomActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ElectionAdapter firestoreRecyclerAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button nav_room;
    ImageView btnadd_room;
    String email,Election,roomId,roomName;
    TextView roomname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        roomId =getIntent().getStringExtra("roomid");


        recyclerView = findViewById(R.id.rec_room);
        nav_room = findViewById(R.id.navigation_room);
        btnadd_room = findViewById(R.id.btnadd_room);
        roomname=findViewById(R.id.roomName_room);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        roomName =getIntent().getStringExtra("roomname");
        roomname.setText(roomName);


        email = fAuth.getCurrentUser().getEmail();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Query
        Query query =fStore.collection("users").document(email).collection("rooms").document(roomId).collection("elections");
        FirestoreRecyclerOptions<ListItem_room> options=new FirestoreRecyclerOptions.Builder<ListItem_room>()
                .setQuery(query,ListItem_room.class)
                .build();

                firestoreRecyclerAdapter= new ElectionAdapter(options);
        recyclerView.setAdapter(firestoreRecyclerAdapter);


        btnadd_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(roomActivity.this);
                dialog.setTitle("Add Election");


                LinearLayout linearLayout= new LinearLayout(roomActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText election =new EditText(roomActivity.this);
                election.setHint("Election Name");


                linearLayout.addView(election);


                dialog.setView(linearLayout);


                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        Election = election.getText().toString();


                        // adding new room
                        final DocumentReference documentReference= fStore.collection("users").document(email).collection("rooms").document(roomId).collection("elections").document(Election);
                        Map<String,Object> user= new HashMap<>();
                        user.put("electionName",Election);

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




}
