package com.example.arribasd.firebasechat.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arribasd.firebasechat.R;
import com.example.arribasd.firebasechat.models.User;

import java.util.ArrayList;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolderDatos> {

    private ArrayList<User> users;
    private View.OnClickListener myClickListener;

    public UsersListAdapter(ArrayList<User> users, View.OnClickListener myClickListener){
        this.users = users;
        this.myClickListener = myClickListener;
    }

    @Override
    public UsersListAdapter.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UsersListAdapter.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(UsersListAdapter.ViewHolderDatos holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolderDatos extends  RecyclerView.ViewHolder{

        TextView name, status;
        //ImageView image;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            name   = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(myClickListener);
            //image   = itemView.findViewById(R.id.imgChat);
        }

    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}

