package com.example.epamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity2 extends AppCompatActivity {


    private Button mUploadbtn;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 1;
    private ImageView mImageview;
    private ProgressDialog mProgressDialog;
    Button ant;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mStorage = FirebaseStorage.getInstance().getReference();
        mUploadbtn = (Button) findViewById(R.id.btnSubir2);
        mImageview = (ImageView) findViewById(R.id.SubirImagen33);
        mProgressDialog = new ProgressDialog(this);
        ant = (Button) findViewById(R.id.btnAnt);




        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ant = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(ant);
                Toast.makeText(MainActivity2.this, "Product uploaded!", Toast.LENGTH_LONG).show();
            }
        });











        mUploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setTitle("Uploading...");
            mProgressDialog.setMessage("Uploading photo to Firebase");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("photos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgressDialog.dismiss();

                    Task<Uri> descargarFoto = taskSnapshot.getStorage().getDownloadUrl();
                    while (!descargarFoto.isComplete());
                    Uri url = descargarFoto.getResult();
                    Glide.with(MainActivity2.this)
                            .load(url)
                            .into(mImageview);




                    Toast.makeText(MainActivity2.this, "Uploaded photo", Toast.LENGTH_LONG).show();
                }
            });
        }
    }










}