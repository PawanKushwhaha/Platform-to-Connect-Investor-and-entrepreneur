package com.example.captal;

import android.content.Context;
import static com.example.captal.MainActivity.unseen;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.example.captal.investor.investor_edit_class;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_chat_adapter extends RecyclerView.Adapter<user_chat_adapter.viewholder> {


    private ArrayList<investor_edit_class>arrayList;
    private Context context;


    public user_chat_adapter (ArrayList<investor_edit_class>arrayList,Context context){

        this.arrayList = arrayList;
        this.context= context;

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (context==null){
            context = parent.getContext();
        }

        View v = LayoutInflater.from(context).inflate(R.layout.chat_user_shadow_here,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        investor_edit_class editClass = arrayList.get(position);

        holder.user_chat_name.setText(editClass.getFirst_name());
        if (editClass.getPhoto_link()!=null){
            Glide.with(context).load(editClass.getPhoto_link()).circleCrop().into(holder.user_chat_profile);
        }


        holder.chat_user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,chat_shown.class);
                intent.putExtra("receiver_id",editClass.getInvestor_id());
                 unseen = 1;
                context.startActivity(intent);
            }
        });

        String receiver_id = editClass.getInvestor_id();



        String sender_id = FirebaseAuth.getInstance().getUid();

        String sender_room = sender_id+receiver_id;

        FirebaseDatabase.getInstance().getReference().child("chat").child(sender_room).child("massage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnap : snapshot.getChildren()) {

                    chat_data_class dataClass = datasnap.getValue(chat_data_class.class);



                    if (!dataClass.getType()) {

                        holder.floatingActionButton.setVisibility(View.VISIBLE);





                    }else {



                        holder.floatingActionButton.setVisibility(View.INVISIBLE);



                    }






                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        LinearLayout chat_user_layout;

        ImageView user_chat_profile;
        TextView user_chat_name ;

        FloatingActionButton floatingActionButton;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            chat_user_layout = itemView.findViewById(R.id.chat_user_layout);


            floatingActionButton = itemView.findViewById(R.id.massage_seen_point);

            user_chat_name  = itemView.findViewById(R.id.chatuser_name);
            user_chat_profile = itemView.findViewById(R.id.usershown_chat_profile_image);


        }
    }

}
