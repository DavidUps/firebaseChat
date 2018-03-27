package com.example.arribasd.firebasechat;

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

import com.example.arribasd.firebasechat.adapters.ChatsAdapter;
import com.example.arribasd.firebasechat.models.Chat;
import com.example.arribasd.firebasechat.models.Message;

import java.util.ArrayList;

import me.rishabhkhanna.recyclerswipedrag.OnSwipeListener;
import me.rishabhkhanna.recyclerswipedrag.RecyclerHelper;

public class ChatsFragment extends Fragment {



    private OnFragmentInteractionListener mListener;

    public ChatsFragment() {
    }


    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvChats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        ArrayList<Chat>chats = new ArrayList<>();

        ArrayList<Message>message = new ArrayList();

        chats.add(new Chat("1234231",message,"david","miguel"));
        chats.add(new Chat("1234231",message,"david","miguel"));
        chats.add(new Chat("1234231",message,"david","miguel"));
        chats.add(new Chat("1234231",message,"david","miguel"));

        ChatsAdapter chatsAdapter = new ChatsAdapter(chats);
        recyclerView.setAdapter(chatsAdapter);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
