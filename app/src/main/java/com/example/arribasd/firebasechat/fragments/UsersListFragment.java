package com.example.arribasd.firebasechat.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.arribasd.firebasechat.R;
import com.example.arribasd.firebasechat.activitys.MainActivity;
import com.example.arribasd.firebasechat.adapters.UsersListAdapter;
import com.example.arribasd.firebasechat.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class UsersListFragment extends Fragment {

    private ChatsFragment.OnFragmentInteractionListener mListener;

    UsersListAdapter usersListAdapter;
    FirebaseDatabase firebaseDatabase;

    public UsersListFragment() {
        // Required empty public constructor
    }

    public static UsersListFragment newInstance(String param1, String param2) {
        UsersListFragment fragment = new UsersListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();

        final ArrayList<User> userList = new ArrayList();

        RecyclerView rvChatList = view.findViewById(R.id.rvChatsList);
        rvChatList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        final DatabaseReference databaseReference = firebaseDatabase.getReference("users");


        ChildEventListener chatsList = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userList.add(new User(dataSnapshot.getKey().toString(), dataSnapshot.child("name").getValue().toString(),"0"));
                usersListAdapter.setUsers(userList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        FirebaseStorage storage  = FirebaseStorage.getInstance("gs://fir-chat-78c59.appspot.com/");

        databaseReference.addChildEventListener(chatsList);


        View.OnClickListener myClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Log.d("Position", ""+position);
                ((MainActivity) getActivity()).changeChatFragment(userList.get(position).getId());
            }
        };

        usersListAdapter = new UsersListAdapter(userList, myClickListener);
        rvChatList.setAdapter(usersListAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChatsFragment.OnFragmentInteractionListener) {
            mListener = (ChatsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListenerChat {
        void changeChatFragment(String idWith);
    }
}
