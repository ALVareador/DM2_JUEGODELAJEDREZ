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
    Casilla [] casillas;
    skinTablero skin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //inicializar array casillas
        casillas = new Casilla[64];
        //TODO cambiar para qu funcion con las skins
        skin = new skinTablero(R.drawable.white,R.drawable.black);

        //rellenamos los fondos

        int numLinea;
        int ptr = 0;
        for(int fila = 0; fila <8 ; fila++){
            numLinea = fila/8;
            if(numLinea%2 == 0){
                for(int columna = 0; columna <8 ; columna++){
                    if(columna%2 == 0)
                        casillas[ptr] = new Casilla (skin.claro);
                    else
                        casillas[ptr] = new Casilla (skin.oscuro);

                    ptr++;
                }
            }else{
                for(int columna = 0; columna <8 ; columna++){
                    if(columna%2 != 0)
                        casillas[ptr] = new Casilla (skin.claro);
                    else
                        casillas[ptr] = new Casilla (skin.oscuro);

                    ptr++;
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
