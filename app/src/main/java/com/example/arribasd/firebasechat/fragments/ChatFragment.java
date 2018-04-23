package com.example.arribasd.firebasechat.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arribasd.firebasechat.R;
import com.example.arribasd.firebasechat.adapters.MessageListAdapter;
import com.example.arribasd.firebasechat.models.MessageChatList;
import com.example.arribasd.firebasechat.models.UserChat;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment {

    private String idWith, idFrom;

    final ArrayList<UserChat> userChats = new ArrayList<>();
    ArrayList<MessageChatList> messageChatLists = new ArrayList<>();

    String key;
    boolean chatExists;
    boolean chatLoaded = false;

    FloatingActionButton fabSend;
    EditText etMessage;
    TextView tvProfileName, tvProfileStatus;
    ImageView imgProfile;
    FirebaseDatabase firebaseDatabase;
    Message message;
    UserChat userChat;
    MessageListAdapter messageListAdapter;
    RecyclerView rvMessages;
    FirebaseStorage storage;


    public ChatFragment newInstance(String idWith) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("idWith", idWith);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = new Message();
        firebaseDatabase = FirebaseDatabase.getInstance();

        if (getArguments() != null) {
            idWith = getArguments().getString("idWith");
            idFrom = FirebaseAuth.getInstance().getUid().toString();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        messageChatLists.clear();

        fabSend = view.findViewById(R.id.fabSend);
        etMessage = view.findViewById(R.id.etMessage);
        rvMessages = view.findViewById(R.id.rvMessageList);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileStatus = view.findViewById(R.id.tvProfileStatus);
        imgProfile = view.findViewById(R.id.imgProfile);

        rvMessages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        messageListAdapter = new MessageListAdapter(messageChatLists);

        profileDetails();

        //Mete el message mandado y mira si el chat está creado o no
        openChat();

        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatExists){
                    //si el chat esta ya creado
                    updateChat();
                }else{
                    //Si el chat no esta creado
                    createChat();
                }
                etMessage.setText("");
            }
        });

        return view;
    }

    private void profileDetails() {
        final DatabaseReference myRef = firebaseDatabase.getReference("users").child(idWith);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("name").getValue(String.class).toString();
                tvProfileName.setText(value);
                if (dataSnapshot.child("status").getValue(int.class).toString().equals("0")){
                    tvProfileStatus.setText("Offline");
                }else{
                    tvProfileStatus.setText("Online");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setImgProfile();
    }

    private void setImgProfile() {
        storage  = FirebaseStorage.getInstance("gs://fir-chat-78c59.appspot.com/");

        StorageReference downloadImg = storage.getReference("profileImage/" + idWith + ".jpg");

        //Poner Imagen desde Firebase.
        Glide.with(getActivity())
                .using(new FirebaseImageLoader())
                .load(downloadImg)
                .error(R.drawable.profile)
                .into(imgProfile);

    }

    private void openChat() {
        DatabaseReference userChatsRef = firebaseDatabase.getReference("users/"+FirebaseAuth.getInstance().getUid().toString()).child("chats");

        final ChildEventListener chatsList = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userChats.add(new UserChat(dataSnapshot.getKey().toString(), dataSnapshot.child("with").getValue().toString()));
                chatExists = checkChatExists();
                if (chatExists) {
                    updateChat();
                }
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

        userChatsRef.addChildEventListener(chatsList);
    }

    private void getMessagesFromExistingChat() {
        final DatabaseReference messageReference = firebaseDatabase.getReference("chats/" + userChat.getId()).child("messages");

        ChildEventListener messageList = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                messageChatLists.add(new MessageChatList(dataSnapshot.child("message").getValue().toString(),dataSnapshot.child("timestamp").getValue().toString()));
                messageListAdapter.setMessages(messageChatLists);
                messageListAdapter.notifyDataSetChanged();
                rvMessages.setAdapter(messageListAdapter);
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

        messageReference.addChildEventListener(messageList);
    }

    private void createChat() {
        DatabaseReference chatRef = firebaseDatabase.getReference();

        String chatKey = chatRef.child("chats").push().getKey();
        DatabaseReference messagesRef = chatRef.child(chatKey).child("messages");

        String messageKey = messagesRef.push().getKey();

        Long lTimestamp = System.currentTimeMillis()/1000;
        String sTimestamp = lTimestamp.toString();
        com.example.arribasd.firebasechat.models.Message message = new com.example.arribasd.firebasechat.models.Message("",
                idFrom,
                idWith,
                etMessage.getText().toString(),
                sTimestamp);
        Map<String, Object> messageValues = message.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/chats/"+chatKey+"/messages/" + messageKey, messageValues);

        UserChat userChat = new UserChat(chatKey ,idWith);
        Map<String, Object> userValues = userChat.toMap();
        childUpdates.put("/users/"+ FirebaseAuth.getInstance().getUid() +"/chats/" + chatKey, userValues);

        chatRef.updateChildren(childUpdates);

        this.userChat = userChat;
        openChat();
    }

    private void updateChat() {
        if (!etMessage.getText().toString().equals("")){
            DatabaseReference chatRef = firebaseDatabase.getReference("chats/"+userChat.getId());
            key = chatRef.child("messages").push().getKey();
            Long lTimestamp = System.currentTimeMillis()/1000;
            String sTimestamp = lTimestamp.toString();
            com.example.arribasd.firebasechat.models.Message message = new com.example.arribasd.firebasechat.models.Message("",
                    idFrom,
                    idWith,
                    etMessage.getText().toString(),
                    sTimestamp);
            Map<String, Object> messageValues = message.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/messages/" + key, messageValues);
            chatRef.updateChildren(childUpdates);

        }

        if(!chatLoaded) {
            chatLoaded = true;
            getMessagesFromExistingChat();
        }
    }

    public boolean checkChatExists() {
        for (int i = 0; i < userChats.size(); i++){
            UserChat user = userChats.get(i);
            if (user.getWith().equalsIgnoreCase(idWith)) {
                userChat = user;
                return true;
            }else if (!user.getWith().equalsIgnoreCase(idWith) && i==userChats.size()-1) {
                return false;
            }
        }
        return false;
    }

}
