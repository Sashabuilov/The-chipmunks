package com.example.beatthemall;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackGroundMusicActivity extends Service {
    private static final String TAG = "MyService";
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.freemusic_back1);
        player.setLooping(true); // зацикливаем
    }
    @Override
    public void onDestroy() {
        player.stop();
    }
    @Override
    public void onStart(Intent intent, int startid) {
        player.start();
    }
}