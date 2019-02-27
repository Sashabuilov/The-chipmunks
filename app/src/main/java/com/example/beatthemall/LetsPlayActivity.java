package com.example.beatthemall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LetsPlayActivity extends Activity {

    int exit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, BackGroundMusicActivity.class));


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_begin_game_screen);

        Button one = findViewById(R.id.btn_one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit = 1;
                Intent intnet_one = new Intent(LetsPlayActivity.this, ChipmunksActivity.class);
                LetsPlayActivity.this.startActivity(intnet_one);
            }
        });
    }

    @Override
    protected void onPause() {
        if (exit == 0) {
            stopService(new Intent(this, BackGroundMusicActivity.class));
            finish();
        }
        super.onPause();

    }

    @Override
    protected void onUserLeaveHint() {
        if (exit == 0) {
            stopService(new Intent(this, BackGroundMusicActivity.class));
            finish();
        }
        super.onUserLeaveHint();

    }

    public void onBackPressed() {
        stopService(new Intent(this, BackGroundMusicActivity.class));
        finish();
    }

}
