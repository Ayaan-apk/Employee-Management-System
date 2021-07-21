package com.example.cms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class UploadActivity extends AppCompatActivity {
    ImageView imageBro,filelogo,cancel;
    Uri filepath;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    EditText filetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        filetitle =findViewById(R.id.filetitle);
        filelogo = findViewById(R.id.imageView2);
        cancel = findViewById(R.id.imageView3);
        imageBro = findViewById(R.id.imageBro);
        filelogo.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        Button Imageupload = findViewById(R.id.uploadbtn);
        Imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processupload(filepath);
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("mydocuments");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filelogo.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                imageBro.setVisibility(View.VISIBLE);
            }
        });
        imageBro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        Intent intent = new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Set PDF files"),101);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode == RESULT_OK){

            filepath =data.getData();
            filelogo.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            imageBro.setVisibility(View.INVISIBLE);
        }
    }

    public void processupload(Uri filepath){

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploading");
        pd.show();
        StorageReference reference = storageReference.child("uploads/"+ System.currentTimeMillis()+".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fileinfomodel obj = new fileinfomodel(filetitle.getText().toString(),uri.toString());
                                databaseReference.child(databaseReference.push().getKey()).setValue(obj);
                                pd.dismiss();
                                Toast.makeText(UploadActivity.this,"File uploaded",Toast.LENGTH_SHORT).show();
                                filelogo.setVisibility(View.INVISIBLE);
                                cancel.setVisibility(View.INVISIBLE);
                                imageBro.setVisibility(View.VISIBLE);
                                filetitle.setText("");

                            }
                        });

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent= (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Uploaded"+(int)percent+"%");

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


}