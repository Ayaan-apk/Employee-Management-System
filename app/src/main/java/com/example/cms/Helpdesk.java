package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Helpdesk extends AppCompatActivity {
    EditText etreq;
    FirebaseFirestore dbroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpdesk);
        etreq =findViewById(R.id.etreq);
        Button btnreq =findViewById(R.id.reqbtn);
        dbroot= FirebaseFirestore.getInstance();
        btnreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
            }
        });
    }
    public void insertdata(){
        Map<String,String> items = new HashMap<>();
        items.put("Request",etreq.getText().toString().trim());
        dbroot.collection("HelpDesk Request").add(items).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                etreq.setText("");
                Toast.makeText(Helpdesk.this,"Request Sent",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
