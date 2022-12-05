package org.uvigo.dm2_juego_del_ajedrez.core;

import android.graphics.drawable.Icon;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Profile implements Serializable {

    private String name ="";
    private String image="";

    private String skinBoardName, skinPieceName;

    private int points=0;

    private ArrayList<String> achievementsList;
    private ArrayList<String> friendsList;

    public Profile(){
        this.name="";
        this.image="cath_image.png";
        
        this.points=0;

        this.skinBoardName="image000000#ffffff";
        this.skinPieceName="2";

        this.achievementsList= new ArrayList<String>();
        this.friendsList= new ArrayList<String>();
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
                this.achievementsList.add(achievement); //Linea con el achievement
            }
        }

        Log.e("",friendsList.toString());
        if(!friendsList.equals("[]")){
            for(String friend: friendsElement){
                Log.e("",friend);
                this.friendsList.add(friend); //Linea con el friend
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

        this.achievementsList= new ArrayList<String>();
        this.friendsList= new ArrayList<String>();
    }

    public Profile(String name, String image){
        this.name=name;
        this.image=image;

        this.points=0;

        this.skinBoardName="image000000#ffffff";
        this.skinPieceName="2";

        this.achievementsList= new ArrayList<String>();
        this.friendsList= new ArrayList<String>();
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
    public ArrayList<String> getAchievements(){
        return achievementsList;
    }
    public ArrayList<String> getFriends(){
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
        if(!friendsList.contains(friend.getName())){
            friendsList.add(friend.getName());
        }else{
            Log.e(friend.getName()," ya es tu amigo");
        }

    }
    public void removeFriend(Profile friend){
        friendsList.remove(friend.getName());
    }
    /**Añade un nuevo logro*/
    public void addAchievement(Achievement achievement){
        if(!achievementsList.contains(achievement.getName())){
            achievementsList.add(achievement.getName());
            Log.w("Nombre logro",achievement.getName());
        }else{
            Log.w("El logro",achievement.getName()+" ya ha sido conseguido");
        }

    }

    @Override
    public String toString() {
        return name+";"+image+";"+skinBoardName+";"+skinPieceName+";"+points+";"+achievementsList.toString()+";"+friendsList.toString();
    }
}
