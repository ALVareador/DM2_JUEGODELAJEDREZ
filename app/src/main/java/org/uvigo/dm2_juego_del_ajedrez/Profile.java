package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.drawable.Icon;
import android.util.Log;

import java.io.Serializable;
import java.sql.Array;
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

        this.skinBoardName="image000000#ffffff";
        this.skinPieceName="2";

        this.achievementsList= new ArrayList<Achievement>();
        this.friendsList= new ArrayList<Profile>();

        achievementsList.add(new Achievement("Hola","Quetal"));
        achievementsList.add(new Achievement("adios","Quetal"));
    }

    public Profile(String name, String image, String skinBoardName, String skinPieceName, int points, String achievementsList, String friendsList){
        this.name=name;
        this.image= image;
        this.points=points;

        this.skinBoardName= skinBoardName;
        this.skinPieceName= skinPieceName;

        //Rellena perfil desde archivo
        Log.e("ACHIEVEMENTLIST: ",achievementsList);
        ArrayList<String> achievementsElement= new ArrayList<String>(Arrays.asList(achievementsList.replace("[","").replace("]","").split(", ")));
        ArrayList<String> friendsElement= new ArrayList<String>(Arrays.asList(friendsList.replace("[","").replace("]","").split(", ")));

        this.achievementsList= new ArrayList<>();
        this.friendsList= new ArrayList<>();

        Log.e("",Arrays.asList(achievementsList).toString());
        if(!achievementsList.equals("[]")){
            for(String achievement: achievementsElement){
                Log.e("#######################",achievement);
                String[] achievementComponent= achievement.split(";");
                this.achievementsList.add(new Achievement(achievementComponent[0],achievementComponent[1])); //Linea con el achievement
            }
        }

        if(!friendsList.equals("[]")){
            for(String friend: friendsElement){
                String[] friendComponent= friend.split(";");
                Log.e("",friendComponent[0]+friendComponent[1]+friendComponent[2]+friendComponent[3]+Integer.parseInt(friendComponent[4])+friendComponent[5]+friendComponent[6]);
                this.friendsList.add(new Profile(friendComponent[0],friendComponent[1],friendComponent[2],friendComponent[3],Integer.parseInt(friendComponent[4]),friendComponent[5],friendComponent[6])); //Linea con el friend
            }
        }

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
    public String getPoints(){ return String.valueOf(points); }
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
        return name+";"+image+";"+skinBoardName+";"+skinPieceName+";"+achievementsList.toString()+";"+friendsList.toString();
    }
}
