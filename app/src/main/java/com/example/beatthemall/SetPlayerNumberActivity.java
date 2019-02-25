package com.example.beatthemall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SetPlayerNumberActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this,BackGroundMusicActivity.class));


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_begin_game_screen);

        Button one = findViewById(R.id.btn_one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intnet_one = new Intent(SetPlayerNumberActivity.this, OnePlayerActivity.class);
                SetPlayerNumberActivity.this.startActivity(intnet_one);
            }
        });
    }
    public void onBackPressed(){
        stopService(new Intent(this, BackGroundMusicActivity.class));
        finish();
    }

}
