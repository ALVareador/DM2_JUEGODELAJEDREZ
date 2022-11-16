package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.drawable.Icon;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Profile implements Serializable {

    private String name ="";
    private String image="";
    private Boolean used = false;
    private int points=0;

    private ArrayList<Achievement> achievementsList;
    //TODO Aqui deben ir las skins que tenga seleccionadas el perfil

    public Profile(){
        this.name="";
        this.image="";
        this.used=false;
        this.points=0;
        this.achievementsList= new ArrayList<Achievement>();
        achievementsList.add(new Achievement("Hola","Que tal"));
        achievementsList.add(new Achievement("Hay que","socializar"));
    }

    public Profile(String name){
        this.name=name;
        this.image="";
        this.used=false;
        this.points=0;
        this.achievementsList= new ArrayList<Achievement>();
    }

    public String getName() {
        return name;
    }
    public String getPoints(){
        return String.valueOf(points);
    }
    /** Devuelve la imagen*/
    public Icon getImage(){
        return Icon.createWithFilePath(image);
    }
    public Boolean getUsed() {
        return used;
    }
    /**Obtiene los logros obtenidos por el perfil*/
    public ArrayList<Achievement> getAchievements(){
        return achievementsList;
    }

    /** Añade sus puntos después de cada partida*/
    public void setPoints(int points){
        this.points+=points;
    }
    public void setUsed(Boolean done) {
        this.used = done;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(String image){ this.image=image; }
    /**Añade un nuevo logro*/
    public void addAchievement(Achievement achievement){
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
