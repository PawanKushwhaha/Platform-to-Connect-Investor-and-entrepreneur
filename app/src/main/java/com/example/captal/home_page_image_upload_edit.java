package com.example.captal;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.captal.databinding.ActivityHomePageImageUploadEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class home_page_image_upload_edit extends AppCompatActivity {



    private ActivityHomePageImageUploadEditBinding homePageImageUploadEditBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private int Result_found =11;
    private Uri upload_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homePageImageUploadEditBinding = ActivityHomePageImageUploadEditBinding.inflate(getLayoutInflater());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        setContentView(homePageImageUploadEditBinding.getRoot());


        homePageImageUploadEditBinding.uploadsImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType("image/8");

                startActivityForResult(intent,Result_found);





            }
        });

        homePageImageUploadEditBinding.uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_profile_uploads();
            }
        });


    }


    private void user_profile_uploads(){

        String compliments = homePageImageUploadEditBinding.compliments.getText().toString();


        if (upload_uri==null && compliments.isEmpty()){

            Toast.makeText(this, "Please Select Compliments Option ", Toast.LENGTH_SHORT).show();
            return;

        }


        Date time = Calendar.getInstance().getTime();
        String unique_photo_id = time.toString();


        StorageReference storageReference = firebaseStorage.getReference().child("home_page_images").child(firebaseAuth.getUid()+unique_photo_id);

        if (upload_uri!=null){


            storageReference.putFile(upload_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()){

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                String url = uri.toString();


                                uploads_class uploadsClass = new uploads_class(url,compliments,firebaseAuth.getUid());

                                firebaseDatabase.getReference().child("upload_image").child(firebaseAuth.getUid()+unique_photo_id).setValue(uploadsClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        if (task.isSuccessful()){
                                            startActivity(new Intent(home_page_image_upload_edit.this,MainActivity.class));
                                            finish();
                                        }


                                    }
                                });

                            }
                        });


                    }


                }
            });


        } else {


            uploads_class uploadsClass = new uploads_class(null,compliments,firebaseAuth.getUid());



            firebaseDatabase.getReference().child("upload_image").child(firebaseAuth.getUid()+unique_photo_id).setValue(uploadsClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        startActivity(new Intent(home_page_image_upload_edit.this,MainActivity.class));
                        finish();
                    }

                }
            });



        }






    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Result_found&&resultCode==RESULT_OK){

            Uri uri = data.getData();

            upload_uri =uri;

            Glide.with(this).load(uri).into(homePageImageUploadEditBinding.uploadsImages);



        }


    }
}