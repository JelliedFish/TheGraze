package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.Toast;

/**
 * Created by Слава on 23.06.2017.
 */

public class MyService extends Service {
    private static final String tag = "MyService";
    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public void onCreate(){
        Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();

        mp = MediaPlayer.create(this, R.raw.melodiya_dlya_sharmanki_melodiya_dlya_sharmanki);
        mp.setLooping(true);
    }
    @Override
    public void onDestroy(){
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        mp.stop();
    }
    @Override
    public void onStart(Intent intent, int startid){
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        mp.start();
    }

    public void onPause(){
        Toast.makeText(this, "My Service Paused", Toast.LENGTH_LONG).show();
        mp.pause();
    }
}
