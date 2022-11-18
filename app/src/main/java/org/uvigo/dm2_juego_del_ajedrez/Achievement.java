package org.uvigo.dm2_juego_del_ajedrez;

import java.io.Serializable;

public class Achievement implements Serializable {
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

    @Override
    public String toString() {
        return name+","+description;
    }
}
