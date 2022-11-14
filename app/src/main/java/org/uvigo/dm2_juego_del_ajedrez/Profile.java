package org.uvigo.dm2_juego_del_ajedrez;

public class Profile {

    private String name ="";
    private Boolean used = false;
    private int points=0;

    public Profile(){
        this.name="";
        this.used=false;
        this.points=0;
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

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", used=" + used +
                ", points=" + points +
                '}';
    }
}
