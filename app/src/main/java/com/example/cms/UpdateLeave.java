package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateLeave extends AppCompatActivity implements View.OnClickListener {

    EditText t1, t2, t3,t4;
    FirebaseFirestore fstore;
    private model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_leave);
        t1 = findViewById(R.id.usernameupdate);
        t2 = findViewById(R.id.dateupdate);
        t3 = findViewById(R.id.reasonupdate);
        t4 = findViewById(R.id.statusupdate);
        model = (model) getIntent().getSerializableExtra("product");
        fstore=FirebaseFirestore.getInstance();
        findViewById(R.id.update).setOnClickListener(this);

        t1.setText(model.getUsername());
        t2.setText(model.getDate());
        t3.setText(model.getReason());
        t4.setText(model.getStatus());


        /*t1.setText(getIntent().getStringExtra("uname").toString());
        t2.setText(getIntent().getStringExtra("date").toString());
        t3.setText(getIntent().getStringExtra("reason").toString());*/


    }

    private void updateProduct() {
        String Username = t1.getText().toString().trim();
        String Date = t2.getText().toString().trim();
        String Reason = t3.getText().toString().trim();
        String Status = t4.getText().toString().trim();
        model model1 = new model(
                Date,
                Reason,
                Username,
                Status
        );
        fstore.collection("Leaves").document(model.getId())
                .set(model1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateLeave.this, "Reply Updated", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateLeave.this, "Cant update", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                updateProduct();
                break;

        }
    }
}