package org.uvigo.dm2_juego_del_ajedrez.core;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Profile implements Serializable {

    private String name;
    private String image;

    private String skinBoardName, skinPieceName;

    private int points;

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
        ArrayList<String> achievementsElement= new ArrayList<String>(Arrays.asList(achievementsList.replace("[","").replace("]","").split(", ")));
        ArrayList<String> friendsElement= new ArrayList<String>(Arrays.asList(friendsList.replace("[","").replace("]","").split(", ")));

        this.achievementsList= new ArrayList<>();
        this.friendsList= new ArrayList<>();

        if(!achievementsList.equals("[]")){
            for(String achievement: achievementsElement){
                this.achievementsList.add(achievement); //Linea con el achievement
            }
        }

        if(!friendsList.equals("[]")){
            for(String friend: friendsElement){
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

    /**Devuelve el nombre de un perfil*/
    public String getName() {
        return name;
    }

    /**Devuelve los puntos de un perfil*/
    public String getPoints(){ return String.valueOf(points); }

    /** Devuelve la ruta de la imagen*/
    public String getImagePath(){
        return image;
    }

    /**Obtiene los logros obtenidos por el perfil*/
    public ArrayList<String> getAchievements(){
        return achievementsList;
    }

    /**Obtiene los amigos por perfil*/
    public ArrayList<String> getFriends(){
        return friendsList;
    }

    /**Devuelve el nombre del tablero seleccionado para este jugador*/
    public String getSkinBoardName(){
        return skinBoardName;
    }

    /**Devuelve el nombre de las piezas seleccionadas por este jugador*/
    public String getSkinPieceName(){
        return skinPieceName;
    }

    /** Añade sus puntos después de cada partida*/
    public void setPoints(int points){
        this.points+=points;
    }

    /**Modifica el nombre*/
    public void setName(String name) {
        this.name = name;
    }

    /**Modifica la imagen*/
    public void setImage(String image){ this.image=image; }

    /**Modifica el tablero*/
    public void setSkinBoardName(String skinName){
        this.skinBoardName=skinName;
    }

    /**Modifica las piezas*/
    public void setSkinPieceName(String skinName){
        this.skinPieceName=skinName;
    }

    /**Añade un amigo*/
    public void addFriend(Context context, Profile friend){
        if(!friendsList.contains(friend.getName())){
            friendsList.add(friend.getName());
        }else{
            Toast.makeText(context, friend.getName()+" ya es tu amigo", Toast.LENGTH_SHORT).show();
        }

    }

    /**Elimina un amigo*/
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
