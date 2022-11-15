package org.uvigo.dm2_juego_del_ajedrez;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Profile {

    private String name ="";
    private Boolean used = false;
    private int points=0;
    private ArrayList<String> achievementsList;

    public Profile(){
        this.name="";
        this.used=false;
        this.points=0;
        this.achievementsList= new ArrayList<String>();
    }

    public Profile(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public int getPoints(){
        return points;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUsed() {
        return used;
    }

    /** Añade sus puntos después de cada partida*/
    public void setPoints(int points){
        this.points+=points;
    }
    public void setUsed(Boolean done) {
        this.used = done;
    }

    /**Obtiene los logros obtenidos por el perfil*/
    public ArrayList<String> getAchievements(){
        return achievementsList;
    }
    /**Añade un nuevo logro*/
    public void setAchievements(String achievement){
        achievementsList.add(achievement);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", used=" + used +
                ", points=" + points +
                '}';
    }
}
