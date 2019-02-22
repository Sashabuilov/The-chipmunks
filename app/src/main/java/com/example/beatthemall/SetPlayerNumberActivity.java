package com.example.beatthemall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class SetPlayerNumberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_player_number);
        Button one = findViewById(R.id.btn_one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intnet_one = new Intent(SetPlayerNumberActivity.this, OnePlayerActivity.class);
                SetPlayerNumberActivity.this.startActivity(intnet_one);
            }
        });
    }


}
