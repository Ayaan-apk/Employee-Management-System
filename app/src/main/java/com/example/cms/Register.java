package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText fullName,email,password,phone,basic,Hra,Bonus,Gross;
    Button registerBtn,goToLogin;
    boolean valid = true;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fauth=FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();


        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        basic = findViewById(R.id.registerBasic);
        Hra = findViewById(R.id.registerHRA);
        Bonus = findViewById(R.id.registerBonus);
        Gross = findViewById(R.id.registerGross);

        registerBtn = findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullName);
                checkField(email);
                checkField(password);
                checkField(phone);
                checkField(basic);
                checkField(Hra);
                checkField(Bonus);
                checkField(Gross);

                if(valid){
                    fauth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fauth.getCurrentUser();
                            Toast.makeText(Register.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = fstore.collection("Users").document(user.getUid());
                            Map<String,Object> userinfo = new HashMap<>();
                            userinfo.put("FullName",fullName.getText().toString());
                            userinfo.put("UserEmail",email.getText().toString());
                            userinfo.put("PhoneNumber",phone.getText().toString());
                            userinfo.put("Basic",basic.getText().toString());
                            userinfo.put("HRA",Hra.getText().toString());
                            userinfo.put("Bonus",Bonus.getText().toString());
                            userinfo.put("Gross",Gross.getText().toString());
                            userinfo.put("isuser","1");
                            documentReference.set(userinfo);

                            /*startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();*/
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this,"Failed to create account",Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        /*goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });*/
    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}
