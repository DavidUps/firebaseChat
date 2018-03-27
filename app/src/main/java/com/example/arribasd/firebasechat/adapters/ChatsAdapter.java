package com.example.arribasd.firebasechat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arribasd.firebasechat.R;
import com.example.arribasd.firebasechat.models.Chat;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolderDatos> {
    private ArrayList<Chat> chats;

    public ChatsAdapter (ArrayList<Chat> chats){
        this.chats = chats;
    }

    @Override
    public ChatsAdapter.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatsAdapter.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ChatsAdapter.ViewHolderDatos holder, int position) {
        Chat chat = chats.get(position);
        holder.title.setText(chat.getWith());
        holder.message.setText(chat.getLastMessage());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolderDatos extends  RecyclerView.ViewHolder{

        TextView title, message;
        ImageView image;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            title   = itemView.findViewById(R.id.tvName);
            message = itemView.findViewById(R.id.tvMessage);
            image   = itemView.findViewById(R.id.imgChat);
        }
    }
}
