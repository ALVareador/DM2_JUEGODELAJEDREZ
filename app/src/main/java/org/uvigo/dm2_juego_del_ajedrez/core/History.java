package org.uvigo.dm2_juego_del_ajedrez.core;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class History implements Serializable {
    String name;
    ArrayList<String> log;
    HashMap<String,String> posPieces;

    public History(String name){
        this.name=name;
        this.log= new ArrayList<>();
        this.posPieces=new HashMap<>();
    }
    public History(String name, ArrayList<String> log, HashMap<String, String> posPieces) {
        this.name = name;
        this.log =log;
        this.posPieces=posPieces;
    }

    public History(String name, String log, String posPieces) {
        Log.e("CREATE HISTORY", name+" "+log+" "+posPieces);
        this.name = name;
        this.log = getParsedLog(log);
        this.posPieces=getParsedPos(posPieces);
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

    public HashMap<String, String> getPosPieces(){
        return posPieces;
    }

    /**String con los movimientos en un string*/
    public String getPlainPos(){
        return posPieces.toString();
    }

    /** Añade una pieza y su posicion para poder recuperarla en el continuarPartida*/
    public void addPos(String name, String pos){
        if(posPieces.get(name)!=null){
            Log.e("SE MODIFICARA LA POSICION DE LA PIEZA "+name," A "+pos);
            posPieces.replace(name,pos);
        }else{
            Log.e("SE ANADIRA LA POSICION DE LA PIEZA "+name," A "+pos);
            posPieces.put(name,pos);
        }
    };

    //{key1=value1, key2=value2}
    public String toString(){
        return name+";"+log.toString()+";"+posPieces;
    }

    /**Devuelve un log como lista*/
    public ArrayList<String> getParsedLog(String log){
        String[] parsedString= log.replace("[","").replace("]","").split(", ");
        Log.e("PARSEDSTRING: ",parsedString.toString());
        ArrayList<String> toret= new ArrayList<>();

        for(String move: parsedString){
            toret.add(move);
        }

        return toret;
    }

    /**Devuelve el mapa de posiciones desde string*/
    public HashMap<String, String> getParsedPos(String pos){
        String[] parsedString= pos.replace("{","").replace("}","").split(", ");
        Log.e("PARSEDSTRING: ",parsedString.toString());

        HashMap<String,String> toret= new HashMap<>();

        for(String line: parsedString){
            String[] parsedLine= line.split("=");
            toret.put(parsedLine[0],parsedLine[1]);
        }

        Log.e("MAP POSICIONES",toret.toString());
        return toret;
    }
}
