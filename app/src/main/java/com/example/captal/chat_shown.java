package com.example.captal;

import static com.example.captal.MainActivity.unseen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.captal.databinding.ActivityChatShownBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class chat_shown extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;


    private String receiver_id;
    private String sender_id;


    private String receiver_room;
    private String sender_room;




    private chat_data_adapter adapter;
    private ArrayList<chat_data_class>arrayList;



    private ActivityChatShownBinding chatShownBinding;


    @Override
    public void onBackPressed() {
        unseen=0;
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatShownBinding = ActivityChatShownBinding.inflate(getLayoutInflater());

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        setContentView(chatShownBinding.getRoot());


        receiver_id = getIntent().getStringExtra("receiver_id");

        sender_id = firebaseAuth.getUid().toString();


        sender_room = sender_id+receiver_id;
        receiver_room = receiver_id+sender_id;

        arrayList = new ArrayList<>();
        adapter = new chat_data_adapter(arrayList,this);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatShownBinding.chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatShownBinding.chatRecyclerView.setAdapter(adapter);


        firebaseDatabase.getReference().child("chat").child(sender_room).child("massage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {

                    chat_data_class chatDataClass = data.getValue(chat_data_class.class);

                    if (unseen==1){
                        if (chatDataClass!=null){
                            if (!chatDataClass.getType()){
                                chatDataClass.setType(true);
                                data.child("type").getRef().setValue(chatDataClass.getType());
                            }

                        }

                    }
                    arrayList.add(chatDataClass);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











        chatShownBinding.massageSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chat_send();

            }
        });




    }

    private void chat_send(){


        String massage = chatShownBinding.massageSendText.getText().toString();

        if (massage.isEmpty()){

            return;
        }

        chat_data_class chatDataClass = new chat_data_class(massage,sender_id,false);


        firebaseDatabase.getReference().child("chat").child(sender_room).child("massage").push().setValue(chatDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    firebaseDatabase.getReference().child("chat").child(receiver_room).child("massage").push().setValue(chatDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            chatShownBinding.massageSendText.setText("");


                        }
                    });
                }

            }
        });







    }





}