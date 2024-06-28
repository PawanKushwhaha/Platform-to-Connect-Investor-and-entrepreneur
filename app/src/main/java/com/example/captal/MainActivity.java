package com.example.captal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.captal.databinding.ActivityMainBinding;
import com.example.captal.investor.investor_edit_class;
import com.example.captal.investor.investor_profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    static int unseen=0;

    private String TAG = "Mainactivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private ActivityMainBinding mainBinding;


    private ArrayList<uploads_class>arrayList;
    private Mainactivity_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        login_check();



        arrayList = new ArrayList<>();
        adapter = new Mainactivity_adapter(arrayList,this);

        mainBinding.recyclerViewHomepage.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerViewHomepage.setAdapter(adapter);

        shown_data_on_home_page();











        mainBinding.network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,connections.class));
            }
        });

        mainBinding.premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,premium.class));
            }
        });







        mainBinding.homePagePhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,home_page_image_upload_edit.class));
            }
        });

        mainBinding.homePageProfileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase.getReference().child("investor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(firebaseAuth.getUid()).exists()){
                            startActivity(new Intent(MainActivity.this,investor_profile.class));
                        }else {

                            startActivity(new Intent(MainActivity.this,founder_profile.class));

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });

;

            }
        });


        mainBinding.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,chat_user_shown.class));


            }
        });


    }

    private void shown_data_on_home_page(){

        firebaseDatabase.getReference().child("upload_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                mainBinding.progressBar.setVisibility(View.GONE);

                for (DataSnapshot snapshots:snapshot.getChildren()) {


                    uploads_class uploadsClass = snapshots.getValue(uploads_class.class);
                    arrayList.add(uploadsClass);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }








    private void login_check(){

        if (firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,number_page.class));
            finish();
        }


    }

}