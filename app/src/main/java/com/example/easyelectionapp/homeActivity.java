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
   private FirestoreRecyclerAdapter adapter;
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
        Query query= firebaseFirestore.collection("users").document(userId).collection("rooms");

        FirestoreRecyclerOptions<ListItem> options= new FirestoreRecyclerOptions.Builder<ListItem>()
                .setQuery(query,ListItem.class)
                .build();
        adapter= new FirestoreRecyclerAdapter<ListItem, ListViewHolder>(options) {
            @NonNull
            @Override
            public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(list_item_layout,viewGroup,false);
                return new ListViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i, @NonNull ListItem listItem) {
                listViewHolder.roomName.setText(listItem.getRoomName());
                listViewHolder.electionCount.setText(listItem.getElectionCount());
                listViewHolder.memberCount.setText(listItem.getMemberCount());
            }
        };


        recyclerView.setAdapter(adapter);

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
                       final DocumentReference documentReference= firebaseFirestore.collection("users").document(userId).collection("rooms").document(roomId);
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

    private class ListViewHolder extends RecyclerView.ViewHolder {


        private TextView roomName;
        private TextView electionCount;
        private  TextView memberCount;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName= itemView.findViewById(R.id.roomName);
            electionCount=itemView.findViewById(R.id.electionCount);
            memberCount = itemView.findViewById(R.id.memberCount);

        }
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