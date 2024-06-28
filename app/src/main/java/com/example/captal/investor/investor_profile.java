package com.example.captal.investor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.captal.databinding.ActivityInvestorProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class investor_profile extends AppCompatActivity {


    private ActivityInvestorProfileBinding profileBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = ActivityInvestorProfileBinding.inflate(getLayoutInflater());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        setContentView(profileBinding.getRoot());



        firebaseDatabase.getReference().child("investor").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                profileBinding.investorFirstName.setText(snapshot.child(("first_name")).getValue().toString());
                profileBinding.investorLastName.setText(snapshot.child("last_name").getValue().toString());

                profileBinding.investorAbout.setText(snapshot.child("about").getValue().toString());
                profileBinding.investorWebsiteLink.setText(snapshot.child("contact_link").getValue().toString());
                profileBinding.companyDetails.setText(snapshot.child("company_name").getValue().toString());

                profileBinding.companyName.setText(snapshot.child("company_about").getValue().toString());


                if (snapshot.child("photo_link").getValue()!=null) {

                    Glide.with(investor_profile.this).load(snapshot.child("photo_link").getValue().toString()).into(profileBinding.investorProfileImage);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }
}