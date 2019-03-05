package com.example.beatthemall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AppSession {
    private static AppSession mInstance;

    public static AppSession getInstance() {
        if (mInstance == null) {
            mInstance = new AppSession();
        }
        return mInstance;
    }
// костыль для получения метода getSharedPreferences
    private Activity helperActivity;

    void prepareAppSessionForActivity(Activity activity) {
        helperActivity = activity;
        sharedPreferences = helperActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
//РАБОТА С НАСТРОЙКАМИ:

    //имя файла настроек
    private static final String APP_PREFERENCES = "Settings";
    //ключ сохранения настроек CheckBox
    private static final String APP_PREFERENCES_PLAY_MUSIC = "enable_music";
    //создаем переменную класса SharedPreferences
    private SharedPreferences sharedPreferences;
    //считываем переменную из файла настроек
    public Boolean getIsPlayMusic() {
        return  sharedPreferences.getBoolean(APP_PREFERENCES_PLAY_MUSIC, true);
    }
    //записываем переменную в файл настроек
    public void setIsPlayMusic(Boolean playMusic) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(APP_PREFERENCES_PLAY_MUSIC, playMusic);
        editor.apply();
        refreshPlayMusic();
    }

    //проверяем булевую переменную на True
    public void refreshPlayMusic() {
        if (getIsPlayMusic()) {
            startPlayMusic();
        } else {
            stopPlayMusic();
        }
    }

//РАБОТА С ФОНОВОЙ МУЗЫКОЙ

    //запускаем сервис с фоновой музыкой
    public void startPlayMusic() {
        helperActivity.startService(new Intent(helperActivity, BackGroundMusicActivity.class));
    }
        //останавливаем сервис с фоновой музыкой
    public void stopPlayMusic() {
        helperActivity.stopService(new Intent(helperActivity, BackGroundMusicActivity.class));
    }
}
