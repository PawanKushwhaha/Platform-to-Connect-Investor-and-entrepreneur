package com.example.captal.investor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.captal.MainActivity;
import com.example.captal.databinding.ActivityInvestorEditPageBinding;
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

public class investor_edit_page extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private ActivityInvestorEditPageBinding investorEditPageBinding;

    private static int Response_code =11;
    private Uri img_uri ;
    private String image_Url;
    private FirebaseDatabase  firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        investorEditPageBinding = ActivityInvestorEditPageBinding.inflate(getLayoutInflater());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        setContentView(investorEditPageBinding.getRoot());

        user_exist();


        investorEditPageBinding.investorProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Response_code);


            }
        });



        investorEditPageBinding.investorSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(investor_edit_page.this, "you clicked on it", Toast.LENGTH_SHORT).show();
                edit_page_check();


            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==Response_code&& resultCode==RESULT_OK){


            Uri uri = data.getData();
            img_uri = uri;

            Glide.with(investor_edit_page.this).load(img_uri).circleCrop().into(investorEditPageBinding.investorProfileImage);

        }

    }

    private void edit_page_check(){

        String first_name = investorEditPageBinding.investorFirstname.getText().toString();
        String last_name = investorEditPageBinding.investorLastname.getText().toString();
        String about = investorEditPageBinding.investorAbout.getText().toString();
        String company_name = investorEditPageBinding.companyName.getText().toString();
        String company_details  = investorEditPageBinding.companyDetails.getText().toString();
        String phone_number = investorEditPageBinding.investorContact.getText().toString();
        String website_link = investorEditPageBinding.investorWebsiteLink.getText().toString();
        String photo_link ="";

        if (first_name.isEmpty()){

            investorEditPageBinding.investorFirstname.setError("Please Enter Your First Name");
            return;

        }
        if (last_name.isEmpty()){
            investorEditPageBinding.investorFirstname.setError("Please Enter Your Last Name");
            return;
        }

        if (about.isEmpty()){

            investorEditPageBinding.investorAbout.setError("Please Enter Your some intro");
            return;
        }

     StorageReference reference = firebaseStorage.getReference().child("profile_images").child(firebaseAuth.getUid());


        if (img_uri!=null) {



            reference.putFile(img_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                    if (task.isSuccessful()){


                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                image_Url = uri.toString();


                                investor_edit_class editClass = new investor_edit_class(first_name, last_name, about, company_name, company_details, phone_number, website_link, image_Url, firebaseAuth.getUid());


                                firebaseDatabase.getReference().child("investor").child(firebaseAuth.getUid()).setValue(editClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(investor_edit_page.this, MainActivity.class));
                                            finish();
                                        }


                                    }
                                });


                            }
                        });

                    }

                }
            });






        }else{

            investor_edit_class investorEditClass = new investor_edit_class(first_name,last_name,about,company_name,company_details,phone_number,website_link,null,firebaseAuth.getUid());

            firebaseDatabase.getReference().child("investor").child(firebaseAuth.getUid()).setValue(investorEditClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {




                }
            });
        }
    }


    private void user_exist(){

        firebaseDatabase.getReference().child("investor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(firebaseAuth.getUid()).exists()){
                    startActivity(new Intent(investor_edit_page.this,MainActivity.class) );
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



}