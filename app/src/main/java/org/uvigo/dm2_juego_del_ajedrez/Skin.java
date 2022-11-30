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

    public Skin(String name, String image){
        this.name=name;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    /** Devuelve la imagen*/
    public Icon getImage(){
        return Icon.createWithFilePath(image);
    }
    public String getImagePath(){
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TODO metodo para crear colores accesibles desde imageview a partir de los strings de color

    @Override
    public String toString() {
        return name+","+image;
    }
}
