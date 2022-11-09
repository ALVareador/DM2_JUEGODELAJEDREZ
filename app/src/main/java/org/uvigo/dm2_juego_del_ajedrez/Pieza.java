package org.uvigo.dm2_juego_del_ajedrez;

public class Pieza {
    String name; //BTOWER, BQUEEN, WPAWN, etc
    String color;
    int row, column;

    public Pieza(String name, String color, int row, int column){
        this.name=name;
        this.color=color;
        this.row= row;
        this.column=column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getColor() {
        return color;
    }

    /** Cambia posicion y devuelve modificacion de historial*/
    public void setPos(int newRow, int newColumn){
        int oldRow, oldColumn;
        oldRow= row;
        oldColumn=column;

        this.row=newRow;
        this.column=newColumn;

        //BTOWER AT R1 C0 -> R2 C2
        //updateHistory(oldRow,oldColumn,row,column);
    }
}
