package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class GameActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Profile selectedProfile= MainActivity.getSelectedProfile();
    GridView tablero;
    pieceAdapter pieceAdapter;
    BoardBox[] casillas;
    SkinBoard skin;
    boolean casillaSeleccionada;
    int posCasillaSeleccionada;
    LinkedList<String> movimientos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        movimientos = new LinkedList<>();
        //inicializar array casillas
        casillaSeleccionada = false;
        casillas = new BoardBox[64];

        //TODO cambiar para que funcion con las skins
        //skin = new SkinBoard(R.drawable.white,R.drawable.blue);
        String[] colors= selectedProfile.getSkinBoardName().replace("image","").split("#");
        Log.e("CADENADECOLOR", selectedProfile.getSkinPieceName());
        Log.e("COLOR1", colors[0]);
        Log.e("COLOR2", colors[1]);
        skin = new SkinBoard(Color.parseColor("#"+colors[0]),
                             Color.parseColor("#"+colors[1]));

        //rellenamos los fondos
        popularCasillas();

        //Piezas negras
        colocarPiezas();

        //Coger el tablero
        tablero = (GridView) findViewById(R.id.tablero);
        pieceAdapter = new pieceAdapter(this,casillas);
        tablero.setAdapter(pieceAdapter);
        tablero.setOnItemClickListener(this);

    }

    public void popularCasillas(){
        int numLinea;
        int ptr = 0;
        for(int fila = 0; fila <8 ; fila++){
            numLinea = fila;
            if(numLinea%2 == 0){
                for(int columna = 0; columna <8 ; columna++){
                    if(columna%2 == 0)
                        casillas[ptr] = new BoardBox(skin.getLightColor());
                    else
                        casillas[ptr] = new BoardBox(skin.getDarkcolor());

                    ptr++;
                }
            }else{
                for(int columna = 0; columna <8 ; columna++){
                    if(columna%2 != 0)
                        casillas[ptr] = new BoardBox(skin.getLightColor());
                    else
                        casillas[ptr] = new BoardBox(skin.getDarkcolor());

                    ptr++;
                }
            }
        }
    }

    public void colocarPiezas(){
        Log.e("SKINPIECENAME",selectedProfile.getSkinPieceName());
        for(int i = 0; i <8;i++){
            //casillas[8+i].setDrawablePieza(R.drawable.peon);
            casillas[8+i].setDrawablePiece("blackpawn"+selectedProfile.getSkinPieceName()+".png");
        }
        //casillas[0].setDrawablePiece(R.drawable.torre);
        casillas[0].setDrawablePiece("blacktower"+selectedProfile.getSkinPieceName()+".png");
        //casillas[7].setDrawablePiece(R.drawable.torre);
        casillas[7].setDrawablePiece("blacktower"+selectedProfile.getSkinPieceName()+".png");

        //casillas[1].setDrawablePiece(R.drawable.caballo);
        casillas[1].setDrawablePiece("blackknight"+selectedProfile.getSkinPieceName()+".png");

        //casillas[6].setDrawablePiece(R.drawable.caballo);
        casillas[6].setDrawablePiece("blackknight"+selectedProfile.getSkinPieceName()+".png");

        //casillas[2].setDrawablePiece(R.drawable.alfil);
        casillas[2].setDrawablePiece("blackbishop"+selectedProfile.getSkinPieceName()+".png");

        //casillas[5].setDrawablePiece(R.drawable.alfil);
        casillas[5].setDrawablePiece("blackbishop"+selectedProfile.getSkinPieceName()+".png");

        //casillas[3].setDrawablePiece(R.drawable.reyna);
        casillas[3].setDrawablePiece("blackqueen"+selectedProfile.getSkinPieceName()+".png");

        //casillas[4].setDrawablePiece(R.drawable.rey);
        casillas[4].setDrawablePiece("blackking"+selectedProfile.getSkinPieceName()+".png");

        //Piezas blancas
        for(int i = 0; i <8;i++){
            //casillas[casillas.length-9-i].setDrawablePieza(R.drawable.peon);
            casillas[casillas.length-9-i].setDrawablePiece("whitepawn"+selectedProfile.getSkinPieceName()+".png");
        }
        //casillas[casillas.length-1-0].setDrawablePieza(R.drawable.torre);
        casillas[casillas.length-1-0].setDrawablePiece("whitetower"+selectedProfile.getSkinPieceName()+".png");

        //casillas[casillas.length-1-7].setDrawablePieza(R.drawable.torre);
        casillas[casillas.length-1-7].setDrawablePiece("whitetower"+selectedProfile.getSkinPieceName()+".png");

        //casillas[casillas.length-1-1].setDrawablePieza(R.drawable.caballo);
        casillas[casillas.length-1-1].setDrawablePiece("whiteknight"+selectedProfile.getSkinPieceName()+".png");
        //casillas[casillas.length-1-6].setDrawablePieza(R.drawable.caballo);
        casillas[casillas.length-1-6].setDrawablePiece("whiteknight"+selectedProfile.getSkinPieceName()+".png");

        //casillas[casillas.length-1-2].setDrawablePieza(R.drawable.alfil);
        casillas[casillas.length-1-2].setDrawablePiece("whitebishop"+selectedProfile.getSkinPieceName()+".png");

        //casillas[casillas.length-1-5].setDrawablePieza(R.drawable.alfil);
        casillas[casillas.length-1-5].setDrawablePiece("whitebishop"+selectedProfile.getSkinPieceName()+".png");

        //casillas[casillas.length-1-3].setDrawablePieza(R.drawable.rey);
        casillas[casillas.length-1-3].setDrawablePiece("whiteking"+selectedProfile.getSkinPieceName()+".png");

        //casillas[casillas.length-1-4].setDrawablePieza(R.drawable.reyna);
        casillas[casillas.length-1-4].setDrawablePiece("whitequeen"+selectedProfile.getSkinPieceName()+".png");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(casillaSeleccionada){
            //Logica de mover la pieza
            casillaSeleccionada = false;
            //recuperamos casillas
            BoardBox anterior = (BoardBox)parent.getItemAtPosition(posCasillaSeleccionada);
            BoardBox siguiente = (BoardBox)parent.getItemAtPosition(position);
            //Verificar que no se mueve una casilla vacia
            //Verificar que no se mueve a la misma casilla
            if(!anterior.getDrawablePiece().equals("") && posCasillaSeleccionada != position){
                //parte grafica------
                //ponemos pieza de anterior en siguiente
                siguiente.setDrawablePiece(anterior.getDrawablePiece());
                //vaciamos anterior
                anterior.setDrawablePiece("");
                tablero.setAdapter(pieceAdapter);

                //guardar movimiento---
                String movimiento = translateCasilla(posCasillaSeleccionada) + "-->" + translateCasilla(position);
                movimientos.add(movimiento);
                TextView lastMove = (TextView) findViewById(R.id.textView5);
                lastMove.setText(movimiento);
            }

        }else{
            //Logica de selección
            //indicamos que hay casilla seleccionada
            casillaSeleccionada = true;
            //guardamos posición de la casilla seleccionada
            posCasillaSeleccionada = position;
        }
    }

    public String translateCasilla(int pos){
        String[] letras = {"a","b","c","d","e","f","g","h"};
        int filaArray = pos/8+1;
        int fila = 9-filaArray;
        int columna = pos - ((filaArray -1) * 8);
        return letras[columna]+fila;
    }
}
