package com.example.arribasd.firebasechat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arribasd.firebasechat.R;
import com.example.arribasd.firebasechat.models.Message;
import com.example.arribasd.firebasechat.models.MessageChatList;
import com.example.arribasd.firebasechat.models.User;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolderDatos> {

    private ArrayList<MessageChatList> messageChat;

    public MessageListAdapter(ArrayList<MessageChatList> messageChat) {
        this.messageChat = messageChat;
    }

    @Override
    public MessageListAdapter.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_chat_list, parent, false);
        return new MessageListAdapter.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(MessageListAdapter.ViewHolderDatos holder, int position) {
        MessageChatList chatList = messageChat.get(position);
        holder.message.setText(chatList.getMessage());
        holder.timestamp.setText(chatList.getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return messageChat.size();
    }

    public class ViewHolderDatos extends  RecyclerView.ViewHolder{

        TextView message, timestamp;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            message   = itemView.findViewById(R.id.tvMessageChat);
            timestamp = itemView.findViewById(R.id.tvTimeStampChat);
        }

    }

    public void setMessages(ArrayList<MessageChatList> messageChat) {
        this.messageChat = messageChat;
    }
}

