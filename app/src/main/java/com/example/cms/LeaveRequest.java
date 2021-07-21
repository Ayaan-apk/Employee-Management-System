package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class LeaveRequest extends AppCompatActivity{
    TextView user,textView,status;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    EditText leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaverequest);
        user=findViewById(R.id.userinfo);
        leave = findViewById(R.id.leavereason);
        status=findViewById(R.id.Status);
        fAuth= FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        /*Button checkstatus= findViewById(R.id.check);
        checkstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkstatus();

            }
        });*/
        Button fetchbtn=findViewById(R.id.fetch);
        textView =findViewById(R.id.textView);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");
        MaterialDatePicker materialDatePicker = builder.build();
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchdata();
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                textView.setText(""+materialDatePicker.getHeaderText());
            }
        });

        fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = user.getText().toString().trim();
                String Date = textView.getText().toString().trim();
                String Reason = leave.getText().toString().trim();
                String Status =status.getText().toString().trim();
                CollectionReference collectionReference =fstore.collection("Leaves");
                model model= new model(
                        Date,
                        Reason,
                        Username,
                        Status
                );

                collectionReference.add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(LeaveRequest.this,"Added",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LeaveRequest.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /*fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference documentReference = fstore.collection("Leave Request").document();
                Map<String,Object> userinfo = new HashMap<>();
                userinfo.put("Username",user.getText().toString());
                userinfo.put("Date",textView.getText().toString());
                userinfo.put("Reason",leave.getText().toString());
                documentReference.set(userinfo);
            }
        });*/


    }

    private void fetchdata() {
        DocumentReference documentReference=fstore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()){
                    user.setText(documentSnapshot.getString("FullName"));
                }
                else{
                    Toast.makeText(LeaveRequest.this,"Data not found",Toast.LENGTH_SHORT).show();


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LeaveRequest.this,"Failed to fetch data",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void checkstatus() {
        DocumentReference documentReference=fstore.collection("Leaves").document("Pu0fbR2W7CRhORqytgR3");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()){
                    status.setText(documentSnapshot.getString("status"));
                }
                else{
                    Toast.makeText(LeaveRequest.this,"Data not found",Toast.LENGTH_SHORT).show();


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LeaveRequest.this,"Failed to fetch data",Toast.LENGTH_SHORT).show();

            }
        });
    }


}