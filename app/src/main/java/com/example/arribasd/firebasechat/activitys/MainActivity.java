package com.example.arribasd.firebasechat.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arribasd.firebasechat.R;
import com.example.arribasd.firebasechat.fragments.ChatsFragment;
import com.example.arribasd.firebasechat.fragments.SettingsFragment;
import com.example.arribasd.firebasechat.fragments.UsersListFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends BaseActivity implements ChatsFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListenerSettings{

    FloatingActionButton fabNewChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabNewChat = findViewById(R.id.fabNewChat);
        fabNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new UsersListFragment()).addToBackStack("ChatsList").commit();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatsFragment()).addToBackStack("Chats").commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.actionSettings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).addToBackStack("Settings").commit();
                break;
            case R.id.logout:
                Toast.makeText(MainActivity.this, "Ha salido correctamente", Toast.LENGTH_SHORT).show();
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Has salido correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
