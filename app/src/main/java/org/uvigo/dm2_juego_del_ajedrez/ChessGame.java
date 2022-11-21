package org.uvigo.dm2_juego_del_ajedrez;

public class ChessGame {

    char[] posP1; //Posicion jugador 1
    Profile player1;
    Profile player2;

    String skin;
    String history; //Historial de jugadas

    /**New Game*/
    public ChessGame(Profile player1, Profile player2, char[] posP1){
        this.player1=player1;
        this.player2=player2;

        this.posP1= posP1;
        this.skin= player1.getSkinBoardName(); //La skin del tablero será la del jugador 1
        createBoard();

        this.history="";

    }

    /**Continuar partida, history contiene los movimientos hasta el momento de finalizacion*/
    public ChessGame(String history){
        this.history=history;
    }

    /**Inicializa el grid con los colores del P1*/
    private void initTable(char posP1) {
    }

    private void createBoard(){
        int primary_color;
        int secondary_color;

        if(skin.equals("")){
            primary_color=R.drawable.white;
            secondary_color=R.drawable.black;
        }else{
            String skin_component[]= skin.split("#");

            primary_color= Integer.parseInt("@drawable/"+skin_component[0]);
            secondary_color= Integer.parseInt("@drawable/"+skin_component[1]);
        }

        Integer[] chessboardIds = { primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color,
                secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color,
                primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color,
                secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color,
                primary_color, secondary_color, primary_color, secondary_color,primary_color, secondary_color, primary_color, secondary_color,
                secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color,
                primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color,
                secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color, secondary_color, primary_color,
        };
    }
}
