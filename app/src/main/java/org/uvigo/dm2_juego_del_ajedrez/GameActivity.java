package org.uvigo.dm2_juego_del_ajedrez;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import androidx.appcompat.app.AppCompatActivity;



class skinTablero{
    int claro;
    int oscuro;

    public skinTablero(int claro, int oscuro) {
        this.claro = claro;
        this.oscuro = oscuro;
    }

}

public class GameActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    GridView tablero;
    pieceAdapter pieceAdapter;
    Casilla [] casillas;
    skinTablero skin;
    boolean casillaSeleccionada;
    int posCasillaSeleccionada;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //inicializar array casillas
        casillaSeleccionada = false;
        casillas = new Casilla[64];
        //TODO cambiar para qu funcion con las skins
        skin = new skinTablero(R.drawable.white,R.drawable.blue);

        //rellenamos los fondos

        int numLinea;
        int ptr = 0;
        for(int fila = 0; fila <8 ; fila++){
            numLinea = fila;
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

        //Piezas negras
        for(int i = 0; i <8;i++){
            casillas[8+i].setDrawablePieza(R.drawable.peon);
        }
        casillas[0].setDrawablePieza(R.drawable.torre);
        casillas[7].setDrawablePieza(R.drawable.torre);
        casillas[1].setDrawablePieza(R.drawable.caballo);
        casillas[6].setDrawablePieza(R.drawable.caballo);
        casillas[2].setDrawablePieza(R.drawable.alfil);
        casillas[5].setDrawablePieza(R.drawable.alfil);
        casillas[3].setDrawablePieza(R.drawable.reyna);
        casillas[4].setDrawablePieza(R.drawable.rey);

        //Piezas blancas
        for(int i = 0; i <8;i++){
            casillas[casillas.length-9-i].setDrawablePieza(R.drawable.peon);
        }
        casillas[casillas.length-1-0].setDrawablePieza(R.drawable.torre);
        casillas[casillas.length-1-7].setDrawablePieza(R.drawable.torre);
        casillas[casillas.length-1-1].setDrawablePieza(R.drawable.caballo);
        casillas[casillas.length-1-6].setDrawablePieza(R.drawable.caballo);
        casillas[casillas.length-1-2].setDrawablePieza(R.drawable.alfil);
        casillas[casillas.length-1-5].setDrawablePieza(R.drawable.alfil);
        casillas[casillas.length-1-3].setDrawablePieza(R.drawable.rey);
        casillas[casillas.length-1-4].setDrawablePieza(R.drawable.reyna);


        //Coger el tablero
        tablero = (GridView) findViewById(R.id.tablero);
        pieceAdapter = new pieceAdapter(this,casillas);
        tablero.setAdapter(pieceAdapter);
        tablero.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(casillaSeleccionada){
            //Logica de mover la pieza
            casillaSeleccionada = false;
            //recuperamos casillas
            Casilla anterior = (Casilla)parent.getItemAtPosition(posCasillaSeleccionada);
            Casilla siguiente = (Casilla)parent.getItemAtPosition(position);
            //Verificar que no se mueve una casilla vacia
            //Verificar que no se mueve a la misma casilla
            if(anterior.getDrawablePieza() != -1 && posCasillaSeleccionada != position){
                //parte grafica------
                //ponemos pieza de anterior en siguiente
                siguiente.setDrawablePieza(anterior.getDrawablePieza());
                //vaciamos anterior
                anterior.setDrawablePieza(-1);

                tablero.setAdapter(pieceAdapter);
            }

        }else{
            //Logica de selección
            //indicamos que hay casilla seleccionada
            casillaSeleccionada = true;
            //guardamos posición de la casilla seleccionada
            posCasillaSeleccionada = position;
        }
    }
}
