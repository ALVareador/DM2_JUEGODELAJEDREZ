package org.uvigo.dm2_juego_del_ajedrez.chess.board;

public class SkinBoard{
    int lightcolor;
    int darkcolor;

    public SkinBoard(int lightcolor, int darkcolor) {
        this.lightcolor = lightcolor;
        this.darkcolor = darkcolor;
    }

    /**Devuelve el color claro del tablero*/
    public int getLightColor(){
        return lightcolor;
    }
    /**Devuelve el color oscuro del tablero*/
    public int getDarkcolor() {
        return darkcolor;
    }
}