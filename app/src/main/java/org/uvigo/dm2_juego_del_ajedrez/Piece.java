package org.uvigo.dm2_juego_del_ajedrez;

import android.util.Log;

public class Piece {
    private String name;
    private char color;
    private String image;
    int position;

    public Piece(String name, char color, String image, int position){
        this.name=name;
        this.color=color;
        this.image=image;
        this.position=position;
        Log.w("POS",Integer.toString(position));
    }
    public String getName(){
        return name;
    }
    public char getColor(){
        return color;
    }
    public String getImage(){
        return image;
    }
    public int getPos(){
        return position;
    }
    public void setPos(int position){
        this.position=position;
    }
}
