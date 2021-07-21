package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TimeSheet extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    TextView starttime,username;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);
        starttime=findViewById(R.id.starttime);
        username = findViewById(R.id.username);
        fAuth= FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Button start =findViewById(R.id.startwork);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchdata();
                //https://docs.google.com/spreadsheets/d/1inDmLQ1O_lltFPoFE744RcJio4RFjug4sU9v1APFaRg/edit#gid=0
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                String dateTime = simpleDateFormat.format(calendar.getTime());
                starttime.setText(dateTime);

            }
        });
        save = findViewById(R.id.savedata);
        save.setOnClickListener(this);


    }
    private void addtosheet(){
        final ProgressDialog loading =ProgressDialog.show(this,"adding Items", "please wait");
        final String start = starttime.getText().toString().trim();
        final String usr = username.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxUjVDaIgcY5TVXom2fgK-A0qhncDsseboCBwvzu3L_5EZ1AdH8iaqCkLJh53CqQs5rnQ/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(TimeSheet.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        )    {

            protected Map<String,String> getParams(){
                Map<String,String>parmas = new HashMap<>();
                parmas.put("action","addItem");
                parmas.put("itemName",start);
                parmas.put("brand",usr);
                return parmas;
            }

        };

        int socketTimeout =50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


    private void fetchdata() {
        DocumentReference documentReference=fstore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()){
                    username.setText(documentSnapshot.getString("FullName"));
                }
                else{
                    Toast.makeText(TimeSheet.this,"Data not found",Toast.LENGTH_SHORT).show();


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TimeSheet.this,"Failed to fetch data",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==save){
            addtosheet();
        }

    }
}