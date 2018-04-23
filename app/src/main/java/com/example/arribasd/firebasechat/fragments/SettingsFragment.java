package com.example.arribasd.firebasechat.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.arribasd.firebasechat.activitys.MainActivity;
import com.example.arribasd.firebasechat.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;

public class SettingsFragment extends Fragment {

    ImageView imgProfile;
    private final int GALLERY = 1;
    private final String IMAGE_DIRECTORY = "/sdcard/DCIM";
    private final int CAMERA = 1;
    FirebaseStorage storage;
    FirebaseDatabase database;
    EditText name, email;
    FloatingActionButton button;

    public SettingsFragment() {}

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        imgProfile   = view.findViewById(R.id.ivProfile);
        name         = view.findViewById(R.id.txtName);
        email        = view.findViewById(R.id.txtEmail);
        button       = view.findViewById(R.id.fabSave);

        //Referencia de la base de datos.
        storage  = FirebaseStorage.getInstance("gs://fir-chat-78c59.appspot.com/");
        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("name").getValue(String.class);
                name.setText(value);
                value = dataSnapshot.child("email").getValue(String.class);
                email.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        StorageReference downloadImg = storage.getReference("profileImage/" + FirebaseAuth.getInstance().getUid() + ".jpg");

        //Poner Imagen desde Firebase.
        Glide.with(getActivity())
                .using(new FirebaseImageLoader())
                .load(downloadImg)
                .error(R.drawable.profile)
                .into(imgProfile);

        //Cambiar Imagen.
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent;
                Intent cameraIntent;

                Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                chooser.putExtra(Intent.EXTRA_INTENT, cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
                chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent = new Intent(Intent.ACTION_PICK,null));
                chooser.putExtra(Intent.EXTRA_TITLE, "Choose an image or take it");

                Intent[] intentArray =  {cameraIntent, galleryIntent};
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooser, 1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("name").setValue(name.getText().toString());
                myRef.child("email").setValue(email.getText().toString());
                ((MainActivity) getActivity()).popFragment();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            return;
        }
        StorageReference uploadImg = storage.getReference("profileImage");

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentUri = data.getData();
                try {
                    //Subir la imagen a Firebase.
                    //Se crea un bitmap, un ByteArrayOutputStream, se hace un compress y en el UploadTask se pone la ruta y se castea.
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentUri);
                    imgProfile.setImageBitmap(bitmap);
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bout);
                    UploadTask uploadTask = uploadImg.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg").putBytes(bout.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public interface OnFragmentInteractionListenerSettings {
        void popFragment();

    }

}
