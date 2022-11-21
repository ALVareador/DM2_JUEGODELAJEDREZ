package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.drawable.Icon;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Profile implements Serializable {

    private String name ="";
    private String image="";
    private Boolean used = false;
    private int points=0;

    private ArrayList<Achievement> achievementsList;
    private String skinBoardName, skinPieceName;

    public Profile(){
        this.name="";
        this.image="";
        this.used=false;
        this.points=0;
        this.achievementsList= new ArrayList<Achievement>();

        achievementsList.add(new Achievement("Hola","Quetal"));
        achievementsList.add(new Achievement("adios","Quetal"));
    }

    public Profile(String name, String image, String used, int points, String achievementsList){
        this.name=name;
        this.image= image;
        this.used=Boolean.parseBoolean(used);
        this.points=points;
        this.achievementsList= new ArrayList<>();

        this.skinBoardName="";
        //Rellena perfil desde archivo
        ArrayList<String> achievementsElement= new ArrayList<String>(Arrays.asList(achievementsList.replace("[","").replace("]","").split(", ")));

        for(String achievement: achievementsElement){
            String[] achievementComponent= achievement.split(", ");
            this.achievementsList.add(new Achievement(achievementComponent[0],achievementComponent[1])); //Linea con el achievement
        }
    }

    public Profile(String name){
        this.name=name;
        this.image="";
        this.skinBoardName="";
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
    public String getSkinBoardName(){
        return skinBoardName;
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
    public void setSkinBoardName(String skinName){
        this.skinBoardName=skinName;
    }
    /**Añade un nuevo logro*/
    public void addAchievement(Achievement achievement){
        achievementsList.add(achievement);
    }

    @Override
    public String toString() {
        return name+","+image+","+used+","+achievementsList.toString();
    }
}
