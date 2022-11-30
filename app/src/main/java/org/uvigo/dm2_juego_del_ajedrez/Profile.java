package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.drawable.Icon;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Profile implements Serializable {

    private String name ="";
    private String image="";

    private String skinBoardName, skinPieceName;

    private int points=0;

    private ArrayList<Achievement> achievementsList;
    private ArrayList<Profile> friendsList;

    public Profile(){
        this.name="";
        this.image="cath_image.png";
        
        this.points=0;

        this.skinBoardName="";
        this.skinPieceName="1";

        this.achievementsList= new ArrayList<Achievement>();
        this.friendsList= new ArrayList<Profile>();

        achievementsList.add(new Achievement("Hola","Quetal"));
        achievementsList.add(new Achievement("adios","Quetal"));
    }

    public Profile(String name, String image, String skinBoardName, String skinPieceName, int points, String achievementsList, ArrayList<Profile> friendsList){
        this.name=name;
        this.image= image;
        this.points=points;
        this.achievementsList= new ArrayList<>();

        this.skinBoardName="";
        this.skinPieceName="1";

        //Rellena perfil desde archivo
        ArrayList<String> achievementsElement= new ArrayList<String>(Arrays.asList(achievementsList.replace("[","").replace("]","").split(", ")));
        //ArrayList<Profile> friendsElement= new ArrayList<String>(Arrays.asList(friendsList.replace("[","").replace("]","").split(", ")));

        for(String achievement: achievementsElement){
            String[] achievementComponent= achievement.split(", ");
            this.achievementsList.add(new Achievement(achievementComponent[0],achievementComponent[1])); //Linea con el achievement
        }

        this.friendsList=friendsList;
    }

    /** Constructor con valores por defecto*/
    public Profile(String name){
        this.name=name;

        this.image="cath_image.png";
        this.skinBoardName="image000000#ffffff";
        this.skinPieceName="1";

        this.points=0;

        this.achievementsList= new ArrayList<Achievement>();
        this.friendsList= new ArrayList<Profile>();
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
    public String getImagePath(){
        return image;
    }
    /**Obtiene los logros obtenidos por el perfil*/
    public ArrayList<Achievement> getAchievements(){
        return achievementsList;
    }
    public ArrayList<Profile> getFriends(){
        return friendsList;
    }
    public String getSkinBoardName(){
        return skinBoardName;
    }

    public String getSkinPieceName(){
        return skinPieceName;
    }

    /** Añade sus puntos después de cada partida*/
    public void setPoints(int points){
        this.points+=points;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(String image){ this.image=image; }
    public void setSkinBoardName(String skinName){
        this.skinBoardName=skinName;
    }
    public void setSkinPieceName(String skinName){
        this.skinPieceName=skinName;
    }
    public void addFriend(Profile friend){
        friendsList.add(friend);
    }
    public void removeFriend(Profile friend){
        friendsList.remove(friend);
    }
    /**Añade un nuevo logro*/
    public void addAchievement(Achievement achievement){
        achievementsList.add(achievement);
    }

    @Override
    public String toString() {
        return name+","+image+","+skinBoardName+","+skinPieceName+","+achievementsList.toString()+","+friendsList.toString();
    }
}
