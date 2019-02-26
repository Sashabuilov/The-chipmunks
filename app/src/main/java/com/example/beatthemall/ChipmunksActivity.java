package com.example.beatthemall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ChipmunksActivity extends Activity {
    Random random = new Random();
    ImageView[] imagesArray;
    TextView gameTime, tv_result,tv_bonus;
    int out_champ_number;
    Timer timer;
    int resultat = 0;
    int bonus_time=0;
    int time=30000;
    CountDownTimer superTimer;


    //sounds
    private SoundPool mSoundPool;
    private SoundPool back_music;
    private int mSoundId = 1;
    private int mStreamId;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSoundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,100);
        mSoundPool.load(this,R.raw.scratch,1);


        gameTime = findViewById(R.id.tv_time);
        gameTime.setText("  ");

        tv_result = findViewById(R.id.tv_resultat);
        tv_result.setText("0");

        tv_bonus=findViewById(R.id.tv_bouns);
        tv_bonus.setText("0");

        //массив с картинками, соответствующий каждой кнопке\картинке
        imagesArray = new ImageView[9];
        imagesArray[0] = findViewById(R.id.topleft);
        imagesArray[1] = findViewById(R.id.topcenter);
        imagesArray[2] = findViewById(R.id.topright);
        imagesArray[3] = findViewById(R.id.centerLeft);
        imagesArray[4] = findViewById(R.id.centerCenter);
        imagesArray[5] = findViewById(R.id.centerRight);
        imagesArray[6] = findViewById(R.id.bottomLeft);
        imagesArray[7] = findViewById(R.id.bottomCenter);
        imagesArray[8] = findViewById(R.id.bottomRight);
        startTimer();
//первичный запуск таймера

        superTimer=new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setBonus_time_plus(millisUntilFinished);
                gameTime.setText("Время: " + millisUntilFinished / 1000);
            }
            @Override
            public void onFinish() {
                tv_result.setText(" ");
                timer.cancel();
                all_in();
                out_champ_number = 9;
                gameTime.setText("Игра завершена, счет: " + Integer.toString(resultat));
            }
        }.start();

    }
    //Функция запуска TimerTask
    public void startTimer() {
        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        gameBody();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 700, 1000);
    }

    //Основное тело игры + перезапуск таймера после его остановки в timerTask

    public void gameBody() {
        int r = random.nextInt((9));
        out_champ_number = r;
        all_in();
        change_to_out(imagesArray[r]);
        press_to_image(r);
        startTimer();
    }

    //обработчик нажатия на кнопки/картинки
    public void press_to_image(final int x) {
        imagesArray[x].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out_champ_number == x) {
                    //вывод звука нажатия на хомячка
                    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float leftVolume = curVolume / maxVolume;
                    float rightVolume = curVolume / maxVolume;
                    int priority = 1;
                    int no_loop = 0;
                    float normal_playback_rate = 1f;
                    mStreamId = mSoundPool.play(mSoundId, leftVolume, rightVolume, priority, no_loop,
                            normal_playback_rate);

                    bonus_time=bonus_time+1;

                    //подсчет результата нажатия на хомячков
                    tv_result.setText((Integer.toString(resultat = resultat + 1)));

                    all_in();
                } else bonus_time=0;
            }
        });
    }

    //прячем всех хомяков при помощи функции change_to_in
    public void all_in() {
        for (int i = 0; i < 9; i++) {
            change_to_in(imagesArray[i]);
        }
    }

    //обращение к ресурсу картинок
    public void img_change(ImageView iv_change, @DrawableRes int image) {
        iv_change.setImageResource(image);
    }

    //Функция убирает хомяка
    public void change_to_in(ImageView imageView) {
        img_change(imageView, R.drawable.in);
    }

    //функция достает хомяка
    public void change_to_out(ImageView imageView) {
        img_change(imageView, R.drawable.out);
    }

    // Обработчик выхода по кнопке home
    @Override
    protected void onUserLeaveHint() {
        stopService(new Intent(this, BackGroundMusicActivity.class));
        finish();
        super.onUserLeaveHint();
    }

    // запрос на выход из приложения по нажатию кнопки "назад"
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Выйти из приложения?")
                .setMessage("Вы действительно хотите выйти?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        permamently_exit();
                    }
                }).create().show();
    }
    public void permamently_exit() {
        stopService(new Intent(this, BackGroundMusicActivity.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
    }

    //Бунусное время + перезапуск времени в случае получения бонуса
    public long setBonus_time_plus(long t){
        tv_bonus.setText(Long.toString(t));
        if (bonus_time==5){
            bonus_time=0;
            superTimer.cancel();
            t=t+10000;
            tv_bonus.setText("Очков до бонуса:" + Integer.toString(bonus_time-1));
            superTimer=new CountDownTimer(t,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setBonus_time_plus(millisUntilFinished);
                    gameTime.setText("Время: " + millisUntilFinished / 1000);
                }
                @Override
                public void onFinish() {
                    tv_result.setText(" ");
                    timer.cancel();
                    all_in();
                    out_champ_number = 9;
                    gameTime.setText("Игра завершена, счет: " + Integer.toString(resultat));
                }
            }.start();

        } else tv_bonus.setText("");

        return time;
    }
}





