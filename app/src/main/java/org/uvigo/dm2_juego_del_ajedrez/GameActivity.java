package org.uvigo.dm2_juego_del_ajedrez;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



class skinTablero{
    int claro;
    int oscuro;

    public skinTablero(int claro, int oscuro) {
        this.claro = claro;
        this.oscuro = oscuro;
    }

}

public class GameActivity extends AppCompatActivity {
    GridView tablero;
    Casilla [][] casillas;
    skinTablero skin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //inicializar array casillas
        casillas = new Casilla[8][8];
        //TODO cambiar para qu funcion con las skins
        skin = new skinTablero(R.drawable.white,R.drawable.black);

        //rellenamos los fondos

        for (int i = 0; i<7 ; i++){
            //primera lineea empiea por blanco
            for(int j = 0; j < 7 ; j++){
                //Si i par entonces eempiezo een blanco
                if(i%2==0){
                    //Si j par entonces casilla  blanco
                    if(j%2==0){
                        casillas[i][j] = new Casilla (skin.claro);
                    }else{//Entonces es negra
                        casillas[i][j] = new Casilla (skin.oscuro);
                    }
                }else{
                    //Si j par entonces casilla  blanco
                    if(j%2!=0){
                        casillas[i][j] = new Casilla (skin.claro);
                    }else{//Entonces es negra
                        casillas[i][j] = new Casilla (skin.oscuro);
                    }
                }

            }
        }


        //Coger el tablero
        tablero = (GridView) findViewById(R.id.tablero);
        pieceAdapter pieceAdapter = new pieceAdapter(this,casillas);
        tablero.setAdapter(pieceAdapter);
        //añadir borad_box al tablero
    }


}

/*
        //clase abstracta que contieene el fondo y piza
        ViewHolder viewHolder = new ViewHolder();
        //coge un view de esee tipo
        viewHolder.square = (ImageView) rowView.findViewById(R.id.square_background);
        //cambia el fondo
        viewHolder.square.setImageResource(chessboardIds[position]);
        //coge un view de esee tipo
        viewHolder.piece = (ImageView) rowView.findViewById(R.id.piece);
        //cambia la piza
        viewHolder.piece.setImageResource(lp[position].getRessource());
**/
