package org.uvigo.dm2_juego_del_ajedrez;

public class Achievement {
    private String name;
    private String description;

    public Achievement(String name, String description){
        this.name=name;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
