package org.uvigo.dm2_juego_del_ajedrez.core;

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

    /**Devuelve el nombre de la skin*/
    public String getName() {
        return name;
    }

    /** Devuelve la imagen*/
    public String getImagePath(){
        return image;
    }

    /**Cambia el nombre de la imagen*/
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name+","+image;
    }
}
