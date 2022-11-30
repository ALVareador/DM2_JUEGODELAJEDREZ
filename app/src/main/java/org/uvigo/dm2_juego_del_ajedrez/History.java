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

    public ArrayList<String> getLog(){
        return log;
    }

    public String getPlainLog(){
        return log.toString();
    }

    public void addMove(String move) {
        log.add(move);
    }

    public String toString(){
        return name+";"+log.toString();
    }
}
