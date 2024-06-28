package com.example.captal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.captal.investor.investor_edit_class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Mainactivity_adapter extends RecyclerView.Adapter<Mainactivity_adapter.viewholder> {

    private ArrayList<uploads_class>arrayList;
    Context context;


    private static investor_edit_class investorEditClass = new investor_edit_class();

    public Mainactivity_adapter(ArrayList<uploads_class> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.home_page_data_shown,parent,false);


        return  new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        uploads_class uploadsClass = arrayList.get(position);

        holder.compliment.setText(uploadsClass.getCompliments());

        if (uploadsClass.getImages_link()!=null) {

            Glide.with(context).load(uploadsClass.getImages_link()).into(holder.home_page_images);
        }
        else {
            holder.home_page_images.setVisibility(View.GONE);
        }


        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("investor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(FirebaseAuth.getInstance().getUid()).exists()){
                    for (DataSnapshot f: snapshot.getChildren()) {

                        investorEditClass = f.getValue(investor_edit_class.class);

                    }
                }


                if (snapshot.child(uploadsClass.getUploader_id()).exists()){

                    holder.name.setText(snapshot.child(uploadsClass.getUploader_id()).child("first_name").getValue().toString());
                    holder.self_intro.setText(snapshot.child(uploadsClass.getUploader_id()).child("about").getValue().toString());


                    if (snapshot.child(uploadsClass.getUploader_id()).child("photo_link").exists()){
                        Glide.with(context).load(snapshot.child(uploadsClass.getUploader_id()).child("photo_link").getValue().toString()).circleCrop().into(holder.profile_photo);
                    }


                    holder.home_chat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context,chat_shown.class);
                            intent.putExtra("receiver_id",uploadsClass.getUploader_id());


                            for (DataSnapshot snaps:snapshot.getChildren()) {

                                investor_edit_class edit_class = snaps.getValue(investor_edit_class.class);
                                if (Objects.equals(edit_class.getInvestor_id(), uploadsClass.getUploader_id())) {

                                    firebaseDatabase.getReference().child("chat_user_shown").child(FirebaseAuth.getInstance().getUid()).child(uploadsClass.getUploader_id()).setValue(edit_class).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {




                                                firebaseDatabase.getReference().child("chat_user_shown").child(uploadsClass.getUploader_id()).child(FirebaseAuth.getInstance().getUid()).setValue(investorEditClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {


                                                        if (task.isSuccessful()){

                                                            holder.home_chat.setVisibility(View.GONE);
                                                            Toast.makeText(context, "Now you can find him on chat section", Toast.LENGTH_SHORT).show();
                                                            context.startActivity(intent);
                                                        }

                                                    }
                                                });

                                            }
                                        }
                                    });
                                }
                            }





                        }
                    });



                }else{

                    firebaseDatabase.getReference().child("founder").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.child(FirebaseAuth.getInstance().getUid()).exists()){

                                for (DataSnapshot f: snapshot.getChildren()) {

                                   investorEditClass = f.getValue(investor_edit_class.class);

                                }

                            }


                            if (snapshot.child(uploadsClass.getUploader_id()).exists()){



                                    holder.name.setText(snapshot.child(uploadsClass.getUploader_id()).child("first_name").getValue().toString());
                                    holder.self_intro.setText(snapshot.child(uploadsClass.getUploader_id()).child("about").getValue().toString());


                                    if (snapshot.child(uploadsClass.getUploader_id()).child("photo_link").exists()){
                                        Glide.with(context).load(snapshot.child(uploadsClass.getUploader_id()).child("photo_link").getValue().toString()).circleCrop().into(holder.profile_photo);
                                    }



                                holder.home_chat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(context,chat_shown.class);
                                        intent.putExtra("receiver_id",uploadsClass.getUploader_id());


                                        for (DataSnapshot snaps:snapshot.getChildren()) {

                                            investor_edit_class edit_class = snaps.getValue(investor_edit_class.class);
                                            if (Objects.equals(edit_class.getInvestor_id(), uploadsClass.getUploader_id())) {

                                                firebaseDatabase.getReference().child("chat_user_shown").child(FirebaseAuth.getInstance().getUid()).child(uploadsClass.getUploader_id()).setValue(edit_class).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {





                                                            firebaseDatabase.getReference().child("chat_user_shown").child(uploadsClass.getUploader_id()).child(FirebaseAuth.getInstance().getUid()).setValue(investorEditClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {


                                                                    if (task.isSuccessful()){

                                                                        holder.home_chat.setVisibility(View.GONE);
                                                                        Toast.makeText(context, "Now you can find him on chat section", Toast.LENGTH_SHORT).show();
                                                                        context.startActivity(intent);
                                                                    }

                                                                }
                                                            });





                                                        }
                                                    }
                                                });
                                            }
                                        }





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


        TextView compliment,name,self_intro;
        ImageView home_page_images,profile_photo,home_chat;


        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.home_user_name);
            self_intro = itemView.findViewById(R.id.home_user_about);
            profile_photo = itemView.findViewById(R.id.home_profile_image);
            home_chat = itemView.findViewById(R.id.home_chat);





            compliment = itemView.findViewById(R.id.compliments);
            home_page_images = itemView.findViewById(R.id.home_page_photos);




        }
    }

}
