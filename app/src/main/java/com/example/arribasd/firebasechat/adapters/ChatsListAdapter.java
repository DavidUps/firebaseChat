package com.example.arribasd.firebasechat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arribasd.firebasechat.R;
import com.example.arribasd.firebasechat.models.User;

import java.util.ArrayList;

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ViewHolderDatos> {

    private ArrayList<User> chats;

    public ChatsListAdapter (ArrayList<User> chats){
        this.chats = chats;
    }

    @Override
    public ChatsListAdapter.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);
        return new ChatsListAdapter.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ChatsListAdapter.ViewHolderDatos holder, int position) {
        User chat = chats.get(position);
        holder.name.setText(chat.getName());
        holder.status.setText(chat.getStatus());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolderDatos extends  RecyclerView.ViewHolder{

        TextView name, status;
        //ImageView image;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            name   = itemView.findViewById(R.id.tvName);
            status = itemView.findViewById(R.id.tvMessage);
            //image   = itemView.findViewById(R.id.imgChat);
        }
    }
}

