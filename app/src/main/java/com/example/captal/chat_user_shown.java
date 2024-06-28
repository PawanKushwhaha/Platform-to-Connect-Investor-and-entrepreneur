package com.example.captal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.captal.databinding.ActivityChatUserShownBinding;
import com.example.captal.investor.investor_edit_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat_user_shown extends AppCompatActivity {


    private ArrayList<investor_edit_class> arrayList;
    private user_chat_adapter adapter;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    private ActivityChatUserShownBinding chatUserShownBinding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        chatUserShownBinding = ActivityChatUserShownBinding.inflate(getLayoutInflater());




        setContentView(chatUserShownBinding.getRoot());


        arrayList = new ArrayList<>();
        adapter = new user_chat_adapter(arrayList,this);
        chatUserShownBinding.recyclerviewChatUserShown.setLayoutManager(new LinearLayoutManager(this));

        chatUserShownBinding.recyclerviewChatUserShown.setAdapter(adapter);







        firebaseDatabase.getReference().child("chat_user_shown").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot snaps:snapshot.getChildren()) {

                    investor_edit_class investorEditClass = snaps.getValue(investor_edit_class.class);
                    arrayList.add(investorEditClass);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(chat_user_shown.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}