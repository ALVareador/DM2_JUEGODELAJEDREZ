package org.uvigo.dm2_juego_del_ajedrez;

public class SkinBoard{
    int lightcolor;
    int darkcolor;

    public SkinBoard(int lightcolor, int darkcolor) {
        this.lightcolor = lightcolor;
        this.darkcolor = darkcolor;
    }

    public int getLightColor(){
        return lightcolor;
    }

    public int getDarkcolor() {
        return darkcolor;
    }
}