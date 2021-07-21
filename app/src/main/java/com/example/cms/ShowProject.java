package com.example.cms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;

public class ShowProject extends AppCompatActivity {

    RecyclerView recyclerView;
    pdfadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);
        recyclerView = (RecyclerView)findViewById(R.id.pdfrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<pdfmodel> options =
                new FirebaseRecyclerOptions.Builder<pdfmodel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("mydocuments"),pdfmodel.class).build();
        adapter=new pdfadapter(options);
        recyclerView.setAdapter(adapter);





    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}