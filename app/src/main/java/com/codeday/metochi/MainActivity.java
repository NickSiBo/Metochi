package com.codeday.metochi;
import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.Window;
import android.view.WindowManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends Activity {
    boolean state = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.codeday.metochi.R.layout.activity_main);
        android.widget.ImageButton trigger = (android.widget.ImageButton) findViewById(com.codeday.metochi.R.id.buttonMain);
        com.firebase.client.Firebase.setAndroidContext(this);
        final com.firebase.client.Firebase metochi = new com.firebase.client.Firebase("https://metochi.firebaseio.com");
        ppState(state);
        android.widget.ImageView silence = (android.widget.ImageView) findViewById(com.codeday.metochi.R.id.nameLogo);
        silence.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                state = false;
                ppState(state);
            }
        });
        trigger.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (!state) metochi.child("play").setValue(1);
                else metochi.child("play").setValue(0);
            }
        });
        metochi.child("play").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("1")) state = true;
                else state = false;
                ppState(state);
            }
            @Override
            public void onCancelled(com.firebase.client.FirebaseError firebaseError) { }
        });
    }
    public void ppState(boolean currState) {
        MediaPlayer mP =  android.media.MediaPlayer.create(this, com.codeday.metochi.R.raw.saytechnologic);
        if(currState) mP.start();
        else mP.stop();
    }
}