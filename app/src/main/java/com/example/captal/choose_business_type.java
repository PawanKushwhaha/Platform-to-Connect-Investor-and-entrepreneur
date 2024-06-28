package com.example.captal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.captal.databinding.ActivityChooseBusinessTypeBinding;
import com.example.captal.databinding.ActivityNumberPageBinding;
import com.example.captal.investor.investor_edit_page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class choose_business_type extends AppCompatActivity {

    private ActivityChooseBusinessTypeBinding chooseBusinessTypeBinding;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseBusinessTypeBinding = ActivityChooseBusinessTypeBinding.inflate(getLayoutInflater());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        setContentView(chooseBusinessTypeBinding.getRoot());

        transfer();


        chooseBusinessTypeBinding.startupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(choose_business_type.this,founder_edit_page.class));
                finish();



            }
        });

        chooseBusinessTypeBinding.investorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(choose_business_type.this, investor_edit_page.class));
                finish();


            }
        });



    }

    private void transfer(){
        firebaseDatabase.getReference().child("investor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(firebaseAuth.getUid()).exists()){
                    startActivity(new Intent(choose_business_type.this,MainActivity.class));
                    finish();
                }else {
                    firebaseDatabase.getReference().child("founder").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.child(firebaseAuth.getUid()).exists()){


                                startActivity(new Intent(choose_business_type.this,MainActivity.class));
                                finish();



                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}