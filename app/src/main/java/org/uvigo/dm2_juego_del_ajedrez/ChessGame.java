package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ChessGame extends AppCompatActivity{

    char[] posP1; //Posicion jugador 1
    Profile player1;
    Profile player2;

    String skin;
    ArrayList<Piece> piecesAtGame;
    ArrayList<String> history; //Historial de jugadas

    /**New Game*/
    public ChessGame(Profile player1, Profile player2, char[] posP1){
        this.player1=player1;
        this.player2=player2;

        this.posP1= posP1;
        //this.skin= player1.getSkinBoardName(); //La skin del tablero será la del jugador 1
        createBoard();

        this.piecesAtGame= new ArrayList<Piece>();
        this.history=new ArrayList<String>();

    }

    /**Continuar partida, history contiene los movimientos hasta el momento de finalizacion*/
    public ChessGame(ArrayList<String> history){
        this.history=history;
        //TODO COLOCA LAS PIEZAS DONDE TOCAN recreateGame();
    }

    /**Inicializa el grid con los colores del P1*/
    private void createBoard(){
        int primary_color;
        int secondary_color;

        if(skin.equals("")){
            primary_color =  R.drawable.white;
            secondary_color =  R.drawable.black;
        }else{
            String skin_component[]= skin.split("#");

            primary_color = getResources().getIdentifier("@drawable/"+skin_component[0], null, getPackageName());
            secondary_color = getResources().getIdentifier("@drawable/"+skin_component[1], null, getPackageName());
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

        //TODO SELECCIONAR POSICION

        piecesAtGame.add( new Piece("BTOWER1",false,0, 0));
        piecesAtGame.add( new Piece("BKNIGHT1",false,0, 1));
        piecesAtGame.add( new Piece("BBISHOP1",false,0, 2));
        piecesAtGame.add( new Piece("BQUEEN",false,0, 3));
        piecesAtGame.add( new Piece("BKING",false,0, 4));
        piecesAtGame.add( new Piece("BBISHOP2",false,0, 5));
        piecesAtGame.add( new Piece("BKNIGHT2",false,0, 6));
        piecesAtGame.add( new Piece("BTOWER2",false,0, 7));

        piecesAtGame.add( new Piece("BPAWN1",false,1, 0));
        piecesAtGame.add( new Piece("BPAWN2",false,1, 1));
        piecesAtGame.add( new Piece("BPAWN3",false,1, 2));
        piecesAtGame.add( new Piece("BPAWN4",false,1, 3));
        piecesAtGame.add( new Piece("BPAWN5",false,1, 4));
        piecesAtGame.add( new Piece("BPAWN6",false,1, 5));
        piecesAtGame.add( new Piece("BPAWN7",false,1, 6));
        piecesAtGame.add( new Piece("BPAWN8",false,1, 7));

        piecesAtGame.add( new Piece("WTOWER1",true,7, 0));
        piecesAtGame.add( new Piece("WKNIGHT1",true,7, 1));
        piecesAtGame.add( new Piece("WBISHOP1",true,7, 2));
        piecesAtGame.add( new Piece("WQUEEN",true,7, 3));
        piecesAtGame.add( new Piece("WKING",true,7, 4));
        piecesAtGame.add( new Piece("WBISHOP2",true,7, 5));
        piecesAtGame.add( new Piece("WKNIGHT2",true,7, 6));
        piecesAtGame.add( new Piece("WTOWER2",true,7, 7));

        piecesAtGame.add( new Piece("WPAWN1",true,6, 0));
        piecesAtGame.add( new Piece("WPAWN2",true,6, 1));
        piecesAtGame.add( new Piece("WPAWN3",true,6, 2));
        piecesAtGame.add( new Piece("WPAWN4",true,6, 3));
        piecesAtGame.add( new Piece("WPAWN5",true,6, 4));
        piecesAtGame.add( new Piece("WPAWN6",true,6, 5));
        piecesAtGame.add( new Piece("WPAWN7",true,6, 6));
        piecesAtGame.add( new Piece("WPAWN8",true,6, 7));



    }
}
