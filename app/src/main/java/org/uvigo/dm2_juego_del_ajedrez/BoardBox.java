package org.uvigo.dm2_juego_del_ajedrez;

public class BoardBox {

    public int drawableBackground;
    public String drawablePieza;

    //TODO Hacer qu coja las views quee tocan


    public BoardBox(int drawableBackground, String drawablePieza) {
        this.drawableBackground = drawableBackground;
        this.drawablePieza = drawablePieza;
    }

    public BoardBox(int drawableBackground) {
        this.drawableBackground = drawableBackground;
        this.drawablePieza = "";
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
    public void setDrawablePiece(String drawablePieza) {
        this.drawablePieza = drawablePieza;
    }
}