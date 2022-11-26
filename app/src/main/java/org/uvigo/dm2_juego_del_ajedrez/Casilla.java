package org.uvigo.dm2_juego_del_ajedrez;

public class Casilla {

    public int DrawableFondo;
    public int DrawablePieza;

    //TODO Hacer qu coja las views quee tocan


    public Casilla(int drawableFondo, int drawablePieza) {
        DrawableFondo = drawableFondo;
        DrawablePieza = drawablePieza;
    }

    public Casilla(int drawableFondo) {
        DrawableFondo = drawableFondo;
        DrawablePieza = -1;
    }
}