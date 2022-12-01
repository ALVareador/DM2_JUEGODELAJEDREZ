package org.uvigo.dm2_juego_del_ajedrez;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.LinkedList;

public class GameActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Profile selectedProfile= MainActivity.getSelectedProfile();
    Profile selectedRival;
    GridView tablero;
    PieceAdapter pieceAdapter;
    BoardBox[] casillas;
    SkinBoard skin;
    boolean casillaSeleccionada;
    int posCasillaSeleccionada;
    History history;

    //EMPTY PIECE
    Piece emptyPiece= new Piece("EMPTY",'E',"",-1);

    boolean turn;
    boolean normalMode;

    boolean newGame; //TRUE si newGame/ FALSE si continueGame

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Recuperamos parametros de newActivity
        newGame=(boolean)getIntent().getSerializableExtra("type");

        normalMode= (boolean)getIntent().getSerializableExtra("mode");
        selectedRival= (Profile)getIntent().getSerializableExtra("rival");
        turn= (boolean)getIntent().getSerializableExtra("turn");

        Log.e("","MODO RECUPERADO (true->NORMAL/false->RANDOM): "+normalMode+
                      "\nRIVAL RECUPERADO: "+selectedRival.getName()+
                      "\nTURNO RECUPERADO (true->J1 blanca/false->J1 negra): "+turn);

        //Creamos nuevo historial
        history = new History("GAME "+selectedProfile.getName()+" VS "+selectedRival.getName()+" "+Calendar.getInstance().getTime());

        //inicializar array casillas
        casillaSeleccionada = false;
        casillas = new BoardBox[64];

        TextView wPlayer= findViewById(R.id.whitesPlayer);
        TextView bPlayer= findViewById(R.id.blacksPlayer);

        ImageView iv_wPlayer= findViewById(R.id.whitesPlayerImage);
        ImageView iv_bPlayer= findViewById(R.id.blacksPlayerImage);

        if(turn){
            wPlayer.setText(selectedProfile.getName());
            iv_wPlayer.setImageBitmap(Uploader.bitmapFromAssets(getApplicationContext(),selectedProfile.getImagePath()));

            bPlayer.setText(selectedRival.getName());
            iv_bPlayer.setImageBitmap(Uploader.bitmapFromAssets(getApplicationContext(),selectedRival.getImagePath()));
        }else{
            wPlayer.setText(selectedRival.getName());
            iv_wPlayer.setImageBitmap(Uploader.bitmapFromAssets(getApplicationContext(),selectedRival.getImagePath()));
            bPlayer.setText(selectedProfile.getName());
            iv_bPlayer.setImageBitmap(Uploader.bitmapFromAssets(getApplicationContext(),selectedProfile.getImagePath()));
        }

        //TODO cambiar para que funcion con las skins
        //skin = new SkinBoard(R.drawable.white,R.drawable.blue);
        String[] colors= selectedProfile.getSkinBoardName().replace("image","").split("#");
        Log.e("CADENADECOLOR", selectedProfile.getSkinPieceName());
        Log.e("COLOR1", colors[0]);
        Log.e("COLOR2", colors[1]);
        skin = new SkinBoard(Color.parseColor("#"+colors[0]),
                             Color.parseColor("#"+colors[1]));

        //rellenamos los fondos
        drawBoard();

        //Piezas negras
        orderPieces();

        //Coger el tablero
        tablero = (GridView) findViewById(R.id.board);
        tablero.setHorizontalSpacing(0);
        tablero.setVerticalSpacing(0);
        pieceAdapter = new PieceAdapter(this,casillas);
        tablero.setAdapter(pieceAdapter);
        tablero.setOnItemClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("DB ACTUALIZADA","");
        Uploader.updateHistory(getApplicationContext(),history);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("DB ACTUALIZADA","");
        Uploader.updateHistory(getApplicationContext(),history);
    }

    public void drawBoard(){
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

    /** Crea las piezas en el tablero*/
    public void orderPieces(){

        //BLACKS
        Piece bp1= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",8);
        Piece bp2= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",9);
        Piece bp3= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",10);
        Piece bp4= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",11);
        Piece bp5= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",12);
        Piece bp6= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",13);
        Piece bp7= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",14);
        Piece bp8= new Piece("PAWN",'N',"blackpawn"+selectedProfile.getSkinPieceName()+".png",15);

        Piece bt1= new Piece("TOWER",'N',"blacktower"+selectedProfile.getSkinPieceName()+".png",0);
        Piece bt2= new Piece("TOWER",'N',"blacktower"+selectedProfile.getSkinPieceName()+".png",7);

        Piece bk1= new Piece("KNIGHT",'N',"blackknight"+selectedProfile.getSkinPieceName()+".png",1);
        Piece bk2= new Piece("KNIGHT",'N',"blackknight"+selectedProfile.getSkinPieceName()+".png",6);

        Piece bb1= new Piece("BISHOP",'N',"blackbishop"+selectedProfile.getSkinPieceName()+".png",2);
        Piece bb2= new Piece("BISHOP",'N',"blackbishop"+selectedProfile.getSkinPieceName()+".png",5);

        Piece bq= new Piece("QUEEN",'N',"blackqueen"+selectedProfile.getSkinPieceName()+".png",3);

        Piece bK= new Piece("KING",'N',"blackking"+selectedProfile.getSkinPieceName()+".png",4);

        Log.e("SKINPIECENAME",selectedProfile.getSkinPieceName());

        casillas[bp1.getPos()].setPiece(bp1);
        casillas[bp2.getPos()].setPiece(bp2);
        casillas[bp3.getPos()].setPiece(bp3);
        casillas[bp4.getPos()].setPiece(bp4);
        casillas[bp5.getPos()].setPiece(bp5);
        casillas[bp6.getPos()].setPiece(bp6);
        casillas[bp7.getPos()].setPiece(bp7);
        casillas[bp8.getPos()].setPiece(bp8);

        casillas[bt1.getPos()].setPiece(bt1);
        casillas[bt2.getPos()].setPiece(bt2);

        casillas[bk1.getPos()].setPiece(bk1);
        casillas[bk2.getPos()].setPiece(bk2);

        casillas[bb1.getPos()].setPiece(bb1);
        casillas[bb2.getPos()].setPiece(bb2);

        casillas[bq.getPos()].setPiece(bq);
        casillas[bK.getPos()].setPiece(bK);

        //WHITES

        Piece wp1= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-9);
        Piece wp2= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-8);
        Piece wp3= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-7);
        Piece wp4= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-6);
        Piece wp5= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-5);
        Piece wp6= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-4);
        Piece wp7= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-3);
        Piece wp8= new Piece("PAWN",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-2);

        Piece wt1= new Piece("TOWER",'W',"whitetower"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-0);
        Piece wt2= new Piece("TOWER",'W',"whitetower"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-7);

        Piece wk1= new Piece("KNIGHT",'W',"whiteknight"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-1);
        Piece wk2= new Piece("KNIGHT",'W',"whiteknight"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-6);

        Piece wb1= new Piece("BISHOP",'W',"whitebishop"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-2);
        Piece wb2= new Piece("BISHOP",'W',"whitebishop"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-5);

        Piece wq= new Piece("QUEEN",'W',"whitequeen"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-3);

        Piece wK= new Piece("KING",'W',"whiteking"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-4);

        casillas[wp1.getPos()].setPiece(wp1);
        casillas[wp2.getPos()].setPiece(wp2);
        casillas[wp3.getPos()].setPiece(wp3);
        casillas[wp4.getPos()].setPiece(wp4);
        casillas[wp5.getPos()].setPiece(wp5);
        casillas[wp6.getPos()].setPiece(wp6);
        casillas[wp7.getPos()].setPiece(wp7);
        casillas[wp8.getPos()].setPiece(wp8);

        casillas[wt1.getPos()].setPiece(wt1);
        casillas[wt2.getPos()].setPiece(wt2);
        casillas[wk1.getPos()].setPiece(wk1);
        casillas[wk2.getPos()].setPiece(wk2);
        casillas[wb1.getPos()].setPiece(wb1);
        casillas[wb2.getPos()].setPiece(wb2);
        casillas[wq.getPos()].setPiece(wq);
        casillas[wK.getPos()].setPiece(wK);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(casillaSeleccionada){
            Log.w("CASILLA SELECCIONADA",Integer.toString(position));
            //Logica de mover la pieza
            casillaSeleccionada = false;
            //recuperamos casillas
            BoardBox anterior = (BoardBox)parent.getItemAtPosition(posCasillaSeleccionada);
            BoardBox siguiente = (BoardBox)parent.getItemAtPosition(position);
            //Verificar que no se mueve una casilla vacia
            //Verificar que no se mueve a la misma casilla

            //Si hay una pieza
            if(!anterior.getDrawablePiece().equals("") && posCasillaSeleccionada != position){
                    //Si hay una pieza, comprueba si es del mismo color
                    if(!siguiente.getPiece().getName().equals("EMPTY")){
                        //Coinciden colores
                        if(anterior.getPiece().getColor()==siguiente.getPiece().getColor()){
                            Log.w("","NO SE PUEDE MOVER A UNA CASILLA CON UNA FIGURA DEL MISMO COLOR");
                        }else{
                            //parte grafica------
                            //ponemos pieza de anterior en siguiente
                            siguiente.setPiece(anterior.getPiece());
                            //vaciamos anterior
                            anterior.setPiece(emptyPiece);
                            tablero.setAdapter(pieceAdapter);

                            //guardar movimiento---
                            String movimiento = translateCasilla(posCasillaSeleccionada) + "-->" + translateCasilla(position);
                            history.addMove(movimiento);
                            TextView lastMove = (TextView) findViewById(R.id.historyLog);
                            lastMove.setText(movimiento);

                            if(siguiente.getPiece().getName().contains("KING")) {
                                //Si es un rey
                                Log.w("", "EL REY " + siguiente.getPiece().getColor() + " HA MUERTO");
                                //TODO añade achievement y finaliza actividad
                            }
                        }
                    }else{
                        //parte grafica------
                        //ponemos pieza de anterior en siguiente
                        siguiente.setPiece(anterior.getPiece());
                        //vaciamos anterior
                        anterior.setPiece(emptyPiece);
                        tablero.setAdapter(pieceAdapter);

                        //guardar movimiento---
                        String movimiento = translateCasilla(posCasillaSeleccionada) + "-->" + translateCasilla(position);
                        history.addMove(movimiento);
                        TextView lastMove = (TextView) findViewById(R.id.historyLog);
                        lastMove.setText(movimiento);
                    }
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
