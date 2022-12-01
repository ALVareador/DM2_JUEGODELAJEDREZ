package org.uvigo.dm2_juego_del_ajedrez;

import java.util.ArrayList;

public class History {
    String name;
    ArrayList<String> log;

    public History(String name){
        this.name=name;
        this.log= new ArrayList<>();
    }
    public History(String name, ArrayList<String> log) {
        this.name = name;
        this.log =log;
    }

    public String getName(){
        return name;
    }

    /**Devuelve la lista de movimientos*/
    public ArrayList<String> getLog(){
        return log;
    }

    /**String con los movimientos en un string*/
    public String getPlainLog(){
        return log.toString();
    }

    /**Añade un movimiento al historial*/
    public void addMove(String move) {
        log.add(move);
    }

    public String toString(){
        return name+";"+log.toString();
    }
}
