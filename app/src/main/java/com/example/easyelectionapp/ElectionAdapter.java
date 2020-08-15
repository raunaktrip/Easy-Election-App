package com.example.easyelectionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import static com.example.easyelectionapp.R.layout.itemlist_room;
import static com.example.easyelectionapp.R.layout.list_item_layout;

public class ElectionAdapter extends FirestoreRecyclerAdapter<ListItem_room,ElectionAdapter.ListHolder> {

    public ElectionAdapter(@NonNull FirestoreRecyclerOptions<ListItem_room> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ListHolder listHolder, int i, @NonNull ListItem_room listItem_room) {
        listHolder.electionName.setText(listItem_room.getElectionName());
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemlist_room, viewGroup, false);
        return new ListHolder(view);

    }

    public class ListHolder extends RecyclerView.ViewHolder{
        private TextView electionName;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            electionName= itemView.findViewById(R.id.electionName);
        }
    }
}
