package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.Serializable;

public class GameMusic extends MediaPlayer implements Serializable {

    MediaPlayer music;

    float volumen;

    int progress;

    public GameMusic(Context context) {
        music = MediaPlayer.create(context, R.raw.music);
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
        music.setVolume(volumen, volumen);
        this.volumen = volumen;
    }



}
