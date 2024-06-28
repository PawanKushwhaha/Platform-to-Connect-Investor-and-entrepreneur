package com.example.captal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chat_data_adapter extends RecyclerView.Adapter {


    ArrayList<chat_data_class>arrayList;
    Context context;

    public chat_data_adapter(ArrayList<chat_data_class> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


  int  sender_count =1;
    int receiver_count =2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (context==null){
            context = parent.getContext();
        }


        if (viewType==sender_count){


            View sender = LayoutInflater.from(context).inflate(R.layout.sender_massage_shown,parent,false);
            return new sender_viewholder(sender);


        }else{
            View receiver = LayoutInflater.from(context).inflate(R.layout.receiver_massage_shown,parent,false);
            return new receiver_viewholder(receiver);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        chat_data_class chatDataClass = arrayList.get(position);

        if (holder.getClass()==sender_viewholder.class){


            sender_viewholder senderViewholder = (sender_viewholder)holder;

            senderViewholder.sender_massage.setText(chatDataClass.getMassage());



        }else{

            receiver_viewholder receiverViewholder =(receiver_viewholder) holder;

            receiverViewholder.Receiver_massage.setText(chatDataClass.getMassage());


        }




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    @Override
    public int getItemViewType(int position) {

        chat_data_class chat_data_classes = arrayList.get(position);

        if (chat_data_classes.getSender_id().equals(FirebaseAuth.getInstance().getUid())){
            return sender_count;
        }else{
            return receiver_count;
        }



    }




    class receiver_viewholder extends RecyclerView.ViewHolder{

        TextView Receiver_massage;

        public receiver_viewholder(@NonNull View itemView) {
            super(itemView);


            Receiver_massage = itemView.findViewById(R.id.receiver_massage);



        }
    }



    class sender_viewholder extends RecyclerView.ViewHolder{


        TextView sender_massage;


        public sender_viewholder(@NonNull View itemView) {
            super(itemView);

            sender_massage = itemView.findViewById(R.id.sender_massage);




        }
    }



}
