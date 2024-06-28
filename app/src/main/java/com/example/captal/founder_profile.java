package com.example.captal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;

import com.example.captal.databinding.ActivityFounderProfileBinding;
import com.example.captal.investor.investor_edit_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class founder_profile extends AppCompatActivity {



    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;


    private ActivityFounderProfileBinding founderProfileImage;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        founderProfileImage = ActivityFounderProfileBinding.inflate(getLayoutInflater());


        setContentView(founderProfileImage.getRoot());

        founder_profile_load();





    }

    private void founder_profile_load(){
        firebaseDatabase.getReference().child("founder").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                founderProfileImage.founderFirstName.setText(snapshot.child(("first_name")).getValue().toString());
                founderProfileImage.founderLastName.setText(snapshot.child("last_name").getValue().toString());

                founderProfileImage.founderAbout.setText(snapshot.child("about").getValue().toString());
                founderProfileImage.founderWebsiteLink.setText(snapshot.child("contact_link").getValue().toString());
                founderProfileImage.companyDetails.setText(snapshot.child("company_name").getValue().toString());

                founderProfileImage.companyName.setText(snapshot.child("company_about").getValue().toString());



                if (snapshot.child("photo_link").getValue()!=null) {

                    Glide.with(founder_profile.this).load(snapshot.child("photo_link").getValue().toString()).into(founderProfileImage.founderProfileImage);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}