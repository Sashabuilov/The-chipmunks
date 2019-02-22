package com.example.beatthemall;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class OnePlayerActivity extends Activity {

    Timer timer;
    ImageView[] imagesArray;
    Integer speed=100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);
        imagesArray=new  ImageView[9];
        imagesArray[0]=findViewById(R.id.topleft);
        imagesArray[1]=findViewById(R.id.topcenter);
        imagesArray[2]=findViewById(R.id.topright);
        imagesArray[3]=findViewById(R.id.centerLeft);
        imagesArray[4]=findViewById(R.id.centerCenter);
        imagesArray[5]=findViewById(R.id.centerRight);
        imagesArray[6]=findViewById(R.id.bottomLeft);
        imagesArray[7]=findViewById(R.id.bottomCenter);
        imagesArray[8]=findViewById(R.id.bottomRight);
        startTimer();
    }

    public void startTimer(){
        timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speed=speed+100;
                        Random random=new Random();
                        int r=random.nextInt((9));
                        all_in();
                        change_to_out(imagesArray[r]);

                    }
                });
            }
        };

        
        timer.scheduleAtFixedRate(timerTask,speed,speed);
    }
    public void all_in (){
        for (int i=0; i<9; i++){
            change_to_in(imagesArray[i]);
        }
    }
    public void img_change(ImageView iv_change, @DrawableRes int image){
        iv_change.setImageResource(image);
    }
    public void change_to_in(ImageView imageView){
        img_change(imageView, R.drawable.in);
    }
    public void change_to_out(ImageView imageView){
        img_change(imageView, R.drawable.out);
    }
}






