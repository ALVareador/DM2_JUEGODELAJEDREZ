package org.uvigo.dm2_juego_del_ajedrez.core;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import org.uvigo.dm2_juego_del_ajedrez.R;

import java.io.Serializable;

public class GameMusic extends MediaPlayer implements Serializable {

    MediaPlayer music;
    AudioManager am;

    float volumen;

    int progress;

    public GameMusic(Context context) {
        am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        music = MediaPlayer.create(context, R.raw.music);
        //am = new AudioManager();
        progress = 0;
        volumen = 1;
    }

    public void onStart(){
        music.start();
    }

    public void onPause(){
        progress = music.getCurrentPosition();
        music.pause();
    }
    public void onContinue(Context context) {
        if(music==null){
            music.start();
            music.seekTo(progress);
        }else{
            music = create(context, R.raw.music);
            music.start();
            music.seekTo(progress);
        }
        music.setVolume(volumen, volumen);
    }

    public void onStop(){
        onPause();
        music.release();
    }

    public float getVolumen() {
        return volumen;
    }

    public void setVolume(float volumen) {
        //music.setVolume(volumen, volumen);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (volumen * am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)), 0);
        this.volumen = volumen;
        //Log.e("WARN", am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
    }



}
