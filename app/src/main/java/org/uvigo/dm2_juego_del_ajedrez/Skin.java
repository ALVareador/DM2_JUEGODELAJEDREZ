package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Skin implements Serializable {

    private String name;
    private String image;
    private String lightcolor;
    private String darkcolor;
    private boolean used;

    public Skin(String name, String image, String lightcolor, String darkcolor, boolean used){
        this.name=name;
        this.image=image;
        this.lightcolor=lightcolor;
        this.darkcolor=darkcolor;
        this.used=used;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBitMapImage(byte[] encodedImage){
        try {
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodedImage, 0, encodedImage.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            Log.w("","GETBITMAPIMAGE");
            return null;
        }
    }

    /** Devuelve la imagen*/
    public Icon getImage(){
        return Icon.createWithFilePath(image);
    }
    public String getImagePath(){
        return image;
    }
    public String getLightcolor(){
        return lightcolor;
    }
    public String getDarkcolor(){
        return darkcolor;
    }
    public boolean getUsed(){
        return used;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUsed(boolean used){
        this.used=used;
    }
    //TODO metodo para crear colores accesibles desde imageview a partir de los strings de color

    @Override
    public String toString() {
        return name+","+image+","+lightcolor+","+darkcolor+","+used;
    }
}
