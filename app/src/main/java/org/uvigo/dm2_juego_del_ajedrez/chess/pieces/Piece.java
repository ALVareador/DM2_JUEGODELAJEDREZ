package org.uvigo.dm2_juego_del_ajedrez.chess.pieces;

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
    /**Devuelve el nombre de la pieza*/
    public String getName(){
        return name;
    }
    /**Devuelve el color d ela pieza*/
    public char getColor(){
        return color;
    }
    /**Devuelve la ruta de la imagen de la pieza*/
    public String getImage(){
        return image;
    }
    /**Obtiene la posicion de la pieza*/
    public int getPos(){
        return position;
    }
}
