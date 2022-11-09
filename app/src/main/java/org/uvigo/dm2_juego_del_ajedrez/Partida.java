package org.uvigo.dm2_juego_del_ajedrez;

public class Partida {
    char posP1; //Posicion jugador 1
    String history; //Historial de jugadas
    /**New Game*/
    public Partida(char posP1){
        this.posP1= posP1;
        this.history="";
        initTable(posP1);
    }

    /**Continuar partida, history contiene los movimientos hasta el momento de finalizacion*/
    public Partida(String history){
        this.history=history;
    }

    /**Inicializa el grid con los colores del P1*/
    private void initTable(char posP1) {
    }
}
