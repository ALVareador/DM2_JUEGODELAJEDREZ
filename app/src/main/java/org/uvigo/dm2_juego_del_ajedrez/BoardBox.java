package org.uvigo.dm2_juego_del_ajedrez;

import android.util.Log;

public class BoardBox {

    public int drawableBackground;
    public String drawablePieza;
    private Piece piece;

    //TODO Hacer qu coja las views quee tocan


    public BoardBox(int drawableBackground, String drawablePieza) {
        this.drawableBackground = drawableBackground;
        this.drawablePieza = drawablePieza;
        this.piece= new Piece("EMPTY",'E',"",-1);
    }

    public BoardBox(int drawableBackground) {
        this.drawableBackground = drawableBackground;
        this.drawablePieza = "";
        this.piece= new Piece("EMPTY",'E',"",-1);
    }

    public int getDrawableBackground() {
        return drawableBackground;
    }

    public String getDrawablePiece() {
        return drawablePieza;
    }

    public void setDrawableBackground(int drawableBackground) {
        this.drawableBackground = drawableBackground;
    }
    public Piece getPiece(){
        return piece;
    }
    /** Mueve la pieza y modifica la vista*/
    public void setPiece(Piece piece){
        Log.e("","PIEZA:"+piece.getName());
        this.piece= piece;
        setDrawablePiece(piece.getImage());
    };

    public void setDrawablePiece(String drawablePieza) {
        this.drawablePieza = drawablePieza;
    }
}