package org.uvigo.dm2_juego_del_ajedrez;

public class Piece {
    String name; //BTOWER, BQUEEN, WPAWN, etc
    boolean white;
    int row, column;

    public Piece(String name, boolean white, int row, int column){
        this.name=name;
        this.white=white;

        this.row= row;
        this.column=column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean getColor() {
        return white;
    }

    public int[] getPos(){
        return new int[]{getRow(),getColumn()};
    }

    /** Cambia posicion y devuelve modificacion de historial*/
    public String setPos(int newRow, int newColumn){
        int oldRow, oldColumn;
        oldRow= row;
        oldColumn=column;

        this.row=newRow;
        this.column=newColumn;

        return drawPieceinHistory(white,name,new int[]{oldRow,oldColumn},new int[]{newRow,newColumn});
    }

    public String drawPieceinHistory(boolean white, String name, int[] oldPosition, int[] newPosition){
        String result="";
        if(white){
            result+='W'; //BLANCA
        }else{
            result+='B'; //NEGRA
        }

        //WTOWER AT R1 C0 -> R2 C2
        result+=name+" AT R"+oldPosition[0]+" C"+oldPosition[1]+" -> R"+newPosition[0]+" C"+newPosition[1];

        return result;

    }
}
