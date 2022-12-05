package org.uvigo.dm2_juego_del_ajedrez.chess.board;

import android.util.Log;

import org.uvigo.dm2_juego_del_ajedrez.chess.pieces.Piece;

public class BoardBox {

    public int drawableBackground;
    public String drawablePieza;
    private Piece piece;

    public BoardBox(int drawableBackground) {
        this.drawableBackground = drawableBackground;
        this.drawablePieza = "";
        this.piece= new Piece("EMPTY",'E',"",64);
    }

    /**Devuelve el fondo*/
    public int getDrawableBackground() {
        return drawableBackground;
    }

    /** Devuelve la pieza a dibujar*/
    public String getDrawablePiece() {
        return drawablePieza;
    }

    /**Devuelve la pieza asociada*/
    public Piece getPiece(){
        return piece;
    }
    /** Mueve la pieza y modifica la vista*/
    public void setPiece(Piece piece){
        if(!piece.equals(null)){
            Log.e("","PIEZA:"+piece.getName());
            this.piece= piece;
            setDrawablePiece(piece.getImage());
        }else{
            Log.e("","PIECE ES NULA");
        }
    };

    public void setDrawablePiece(String drawablePieza) {
        this.drawablePieza = drawablePieza;
    }
}