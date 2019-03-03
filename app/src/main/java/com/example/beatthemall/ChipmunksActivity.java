package com.example.beatthemall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ChipmunksActivity extends Activity {

//Описание переменных
    Random random = new Random();
    ImageView[] imagesArray;
    TextView gameTime, tv_result;
    int out_champ_number;
    Timer timerChipMunks;
    int resultat = 0;
    int bonus_count = 10;
    int bouns_countX = bonus_count;
    long timerSeconds = 5000;
    int out_chipmunks_speed = 1000;
    CountDownTimer basicTimer;

    //sounds
    private SoundPool mSoundPool;
    //private SoundPool back_music;
    private int mSoundId = 1;
    private int mStreamId;

    //Диалоги
    DialogFragment about_dialog;
    DialogFragment settings_dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        about_dialog = new Dialog_About();
        settings_dialog = new Dialog_Settings();


        startService(new Intent(this, BackGroundMusicActivity.class));
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        mSoundPool.load(this, R.raw.scratch, 1);
        gameTime = findViewById(R.id.tv_time);
        gameTime.setText("  ");
        tv_result = findViewById(R.id.tv_resultat);
        tv_result.setText("0");
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
        Button btnNewGame = findViewById(R.id.btn_newGame);
        Button btnAbout = findViewById(R.id.btn_about);
        Button btnSettings=findViewById(R.id.btn_Settings);
        
//Кнопка AboutUS
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about_dialog.show(getFragmentManager(), "dlg1");
            }
        });
        
//Кнопка Settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_dialog.show(getFragmentManager(), "dlg2");
            }
        });
// кнопка новая игра
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int play=0;
                if (play==0){
                    startTimer();
                    startBasicTimer(timerSeconds);
                }
            }
        });
    }
    
    //Основной таймер
    public void startBasicTimer(long time) {
        basicTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long timeTick) {
                timerSeconds = timeTick;
                refreshTimerText();
            }

            @Override
            public void onFinish() {
                tv_result.setText(" ");
                timerChipMunks.cancel();
                all_in();
                out_champ_number = 9;
                gameTime.setText("Игра завершена, счет: " + Integer.toString(resultat));
                wantToPlayAgain();
            }
        }.start();
    }
    //функция изменения времени таймера на каждом шаге
    private void refreshTimerText() {
        gameTime.setText("Время: " + timerSeconds / 1000);
    }
    //Функция запуска TimerTask
    public void startTimer() {
        timerChipMunks = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerChipMunks.cancel();
                        gameBody();
                    }
                });
            }
        };
        launch_timerChipMunks(timerTask, out_chipmunks_speed);

    }
    private void launch_timerChipMunks(TimerTask timerTask, Integer speed) {
        timerChipMunks.scheduleAtFixedRate(timerTask, out_chipmunks_speed, 1000);
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
                    sound_scrach();
                    bonus_count = bonus_count - 1;
                    if (bonus_count == 0) {
                        bonus_count = bouns_countX;
                        basicTimer.cancel();
                        timerChipMunks.cancel();
                        startTimer();
                        setNewTime(timerSeconds);
                        out_chipmunks_speed = out_chipmunks_speed - 50;
                        startBasicTimer(setNewTime(timerSeconds));
                    } else
                    //подсчет результата нажатия на хомячков
                    tv_result.setText((Integer.toString(resultat = resultat + 1)));
                    all_in();
                }
            }
        });
    }
    public long setNewTime(long newtime) {
        newtime = timerSeconds + 10000;
        return newtime;
    }
    //вывод звука нажатия на хомячка
    private void sound_scrach() {

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
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        wantToPlayAgain();
                    }
                })
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
    // запрос новой игры после истечения таймера
    public void wantToPlayAgain() {
        new AlertDialog.Builder(this)
                .setTitle("Хотите сыграть еще раз?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        restartAvtivity();
                    }
                }).create().show();
    }
    public void restartAvtivity(){
        Intent restartIntent=getIntent();
        finish();
        startActivity(restartIntent);

    }
    public void mute_background_music(){
        stopService(new Intent(this, BackGroundMusicActivity.class));
    }
}






