package com.example.cms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Reqlist extends AppCompatActivity {
    RecyclerView ReqList;
    ArrayList<reqmodel>datalist;
    FirebaseFirestore db;
    reqadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqlist);
        ReqList = (RecyclerView)findViewById(R.id.RecList);
        ReqList.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        adapter=new reqadapter(datalist);
        ReqList.setAdapter(adapter);

        db=FirebaseFirestore.getInstance();
        db.collection("HelpDesk Request").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list){

                    reqmodel obj =d.toObject(reqmodel.class);
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}