package com.example.captal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.captal.databinding.ActivityFounderEditPageBinding;
import com.example.captal.investor.investor_edit_class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class founder_edit_page extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private static Uri Image_uri;

    private static int Request_code =12;
    private ActivityFounderEditPageBinding founderEditPageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        founderEditPageBinding = ActivityFounderEditPageBinding.inflate(getLayoutInflater());


        setContentView(founderEditPageBinding.getRoot());

        founderEditPageBinding.founderProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_code);


            }
        });



        founderEditPageBinding.founderSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                founder_edit();

            }
        });




    }

    private void founder_edit(){

        String first_name = founderEditPageBinding.founderFirstname.getText().toString();
        String last_name = founderEditPageBinding.founderLastname.getText().toString();
        String about = founderEditPageBinding.founderAbout.getText().toString();

        String company_name = founderEditPageBinding.companyName.getText().toString();
        String company_about = founderEditPageBinding.companyDetails.getText().toString();

        String phone = founderEditPageBinding.founderContact.getText().toString();
        String website_link = founderEditPageBinding.founderWebsiteLink.getText().toString();


        if (first_name.isEmpty()){
            founderEditPageBinding.founderFirstname.setError("Please Enter Your First name");
            return;
        }
        if (last_name.isEmpty())
        {
            founderEditPageBinding.founderLastname.setError("Please enter your lastname");
            return;
        }

        if (about.isEmpty()){
            founderEditPageBinding.founderAbout.setError("Please Enter about yourself");
            return;
        }


        StorageReference storageReference = firebaseStorage.getReference().child("founder_image").child(firebaseAuth.getUid());

        if (Image_uri!=null) {

            storageReference.putFile(Image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()){

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String url = uri.toString();

                                investor_edit_class investorEditClass = new investor_edit_class(first_name, last_name, about, company_name, company_about, phone, website_link, url, firebaseAuth.getUid());


                                firebaseDatabase.getReference().child("founder").child(firebaseAuth.getUid()).setValue(investorEditClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){

                                            startActivity(new Intent(founder_edit_page.this,MainActivity.class));

                                            finish();

                                        }

                                    }
                                });



                            }
                        });



                    }

                }
            });











        }else {
            investor_edit_class investorEditClass = new investor_edit_class(first_name, last_name, about, company_name, company_about, phone, website_link, null, firebaseAuth.getUid());


            firebaseDatabase.getReference().child("founder").child(firebaseAuth.getUid()).setValue(investorEditClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(founder_edit_page.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Request_code&& resultCode==RESULT_OK){
            Uri uri = data.getData();

            Image_uri = uri;
            Glide.with(this).load(Image_uri).circleCrop().into(founderEditPageBinding.founderProfileImage);


        }

    }


    private void user_exist(){

        firebaseDatabase.getReference().child("founder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(firebaseAuth.getUid()).exists()){
                    startActivity(new Intent(founder_edit_page.this,MainActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}