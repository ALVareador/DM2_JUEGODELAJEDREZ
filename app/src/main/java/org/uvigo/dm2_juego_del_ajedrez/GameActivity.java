package org.uvigo.dm2_juego_del_ajedrez;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

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
    ArrayList<Piece> piezasComidas;

    //EMPTY PIECE
    Piece emptyPiece= new Piece("EMPTY",'E',"deadPieces.png",64);

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

        if(newGame){
            //Creamos nuevo historial
            history = new History(selectedProfile.getName()+" VS "+selectedRival.getName()+" "+Calendar.getInstance().getTime());
        }else{
            history=(History)getIntent().getSerializableExtra("history");
        }
        //Creamos array de piezas comidas
        piezasComidas = new ArrayList<Piece>();

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

        String[] colors= selectedProfile.getSkinBoardName().replace("image","").split("#");
        Log.e("CADENADECOLOR", selectedProfile.getSkinPieceName());
        Log.e("COLOR1", colors[0]);
        Log.e("COLOR2", colors[1]);
        skin = new SkinBoard(Color.parseColor("#"+colors[0]),
                             Color.parseColor("#"+colors[1]));

        //rellenamos los fondos
        drawBoard();

        //Piezas negras
        orderPieces(newGame);

        //Coger el tablero
        tablero = (GridView) findViewById(R.id.board);
        tablero.setHorizontalSpacing(0);
        tablero.setVerticalSpacing(0);
        pieceAdapter = new PieceAdapter(this,casillas);
        tablero.setAdapter(pieceAdapter);
        tablero.setOnItemClickListener(this);

        ImageButton backButton = this.findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uploader.updateHistory(getApplicationContext(),history);
                GameActivity.this.setResult( MainActivity.RESULT_CANCELED );
                GameActivity.this.finish();
            }
        });
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
    public void orderPieces(boolean type){
        Log.e("","ORDER PIECES");
        //NEW GAME
        if(type){
            Log.e("","NEW GAME");
            //BLACKS
            Piece bp1= new Piece("PAWN1",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",8);
            Piece bp2= new Piece("PAWN2",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",9);
            Piece bp3= new Piece("PAWN3",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",10);
            Piece bp4= new Piece("PAWN4",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",11);
            Piece bp5= new Piece("PAWN5",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",12);
            Piece bp6= new Piece("PAWN6",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",13);
            Piece bp7= new Piece("PAWN7",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",14);
            Piece bp8= new Piece("PAWN8",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",15);

            Piece bt2= new Piece("TOWER2",'B',"blacktower"+selectedProfile.getSkinPieceName()+".png",7);

            Piece bk1= new Piece("KNIGHT1",'B',"blackknight"+selectedProfile.getSkinPieceName()+".png",1);
            Piece bk2= new Piece("KNIGHT2",'B',"blackknight"+selectedProfile.getSkinPieceName()+".png",6);

            Piece bb1= new Piece("BISHOP1",'B',"blackbishop"+selectedProfile.getSkinPieceName()+".png",2);
            Piece bb2= new Piece("BISHOP2",'B',"blackbishop"+selectedProfile.getSkinPieceName()+".png",5);

            Piece bq= new Piece("QUEEN",'B',"blackqueen"+selectedProfile.getSkinPieceName()+".png",3);

            Piece bK= new Piece("KING",'B',"blackking"+selectedProfile.getSkinPieceName()+".png",4);

            //Para que sobreescriba las que ya hay muertas ahi
            Piece bt1= new Piece("TOWER1",'B',"blacktower"+selectedProfile.getSkinPieceName()+".png",0);

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

            history.addPos(bp1.getColor()+bp1.getName(),Integer.toString(bp1.getPos()));
            history.addPos(bp2.getColor()+bp2.getName(),Integer.toString(bp2.getPos()));
            history.addPos(bp3.getColor()+bp3.getName(),Integer.toString(bp3.getPos()));
            history.addPos(bp4.getColor()+bp4.getName(),Integer.toString(bp4.getPos()));
            history.addPos(bp5.getColor()+bp5.getName(),Integer.toString(bp5.getPos()));
            history.addPos(bp6.getColor()+bp6.getName(),Integer.toString(bp6.getPos()));
            history.addPos(bp7.getColor()+bp7.getName(),Integer.toString(bp7.getPos()));
            history.addPos(bp8.getColor()+bp8.getName(),Integer.toString(bp8.getPos()));
            history.addPos(bt1.getColor()+bt1.getName(),Integer.toString(bt1.getPos()));
            history.addPos(bt2.getColor()+bt2.getName(),Integer.toString(bt2.getPos()));
            history.addPos(bk1.getColor()+bk1.getName(),Integer.toString(bk1.getPos()));
            history.addPos(bk2.getColor()+bk2.getName(),Integer.toString(bk2.getPos()));
            history.addPos(bb1.getColor()+bb1.getName(),Integer.toString(bb1.getPos()));
            history.addPos(bb2.getColor()+bb2.getName(),Integer.toString(bb2.getPos()));
            history.addPos(bq.getColor()+bq.getName(),Integer.toString(bq.getPos()));
            history.addPos(bK.getColor()+bK.getName(),Integer.toString(bK.getPos()));

            //WHITES

            Piece wp1= new Piece("PAWN1",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-9-7);
            Piece wp2= new Piece("PAWN2",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-8-7);
            Piece wp3= new Piece("PAWN3",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-7-7);
            Piece wp4= new Piece("PAWN4",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-6-7);
            Piece wp5= new Piece("PAWN5",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-5-7);
            Piece wp6= new Piece("PAWN6",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-4-7);
            Piece wp7= new Piece("PAWN7",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-3-7);
            Piece wp8= new Piece("PAWN8",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",casillas.length-2-7);

            Piece wt1= new Piece("TOWER1",'W',"whitetower"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-0);
            Piece wt2= new Piece("TOWER2",'W',"whitetower"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-7);

            Piece wk1= new Piece("KNIGHT1",'W',"whiteknight"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-1);
            Piece wk2= new Piece("KNIGHT2",'W',"whiteknight"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-6);

            Piece wb1= new Piece("BISHOP1",'W',"whitebishop"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-2);
            Piece wb2= new Piece("BISHOP2",'W',"whitebishop"+selectedProfile.getSkinPieceName()+".png",casillas.length-1-5);

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

            history.addPos(wp1.getColor()+wp1.getName(),Integer.toString(wp1.getPos()));
            history.addPos(wp2.getColor()+wp2.getName(),Integer.toString(wp2.getPos()));
            history.addPos(wp3.getColor()+wp3.getName(),Integer.toString(wp3.getPos()));
            history.addPos(wp4.getColor()+wp4.getName(),Integer.toString(wp4.getPos()));
            history.addPos(wp5.getColor()+wp5.getName(),Integer.toString(wp5.getPos()));
            history.addPos(wp6.getColor()+wp6.getName(),Integer.toString(wp6.getPos()));
            history.addPos(wp7.getColor()+wp7.getName(),Integer.toString(wp7.getPos()));
            history.addPos(wp8.getColor()+wp8.getName(),Integer.toString(wp8.getPos()));
            history.addPos(wt1.getColor()+wt1.getName(),Integer.toString(wt1.getPos()));
            history.addPos(wt2.getColor()+wt2.getName(),Integer.toString(wt2.getPos()));
            history.addPos(wk1.getColor()+wk1.getName(),Integer.toString(wk1.getPos()));
            history.addPos(wk2.getColor()+wk2.getName(),Integer.toString(wk2.getPos()));
            history.addPos(wb1.getColor()+wb1.getName(),Integer.toString(wb1.getPos()));
            history.addPos(wb2.getColor()+wb2.getName(),Integer.toString(wb2.getPos()));
            history.addPos(wq.getColor()+wq.getName(),Integer.toString(wq.getPos()));
            history.addPos(wK.getColor()+wK.getName(),Integer.toString(wK.getPos()));

        }else{
            //CONTINUEGAME
            Log.e("","CONTINUE GAME");
            //BLACKS
            Piece bp1= new Piece("PAWN1",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN1"));
            Piece bp2= new Piece("PAWN2",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN2"));
            Piece bp3= new Piece("PAWN3",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN3"));
            Piece bp4= new Piece("PAWN4",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN4"));
            Piece bp5= new Piece("PAWN5",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN5"));
            Piece bp6= new Piece("PAWN6",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN6"));
            Piece bp7= new Piece("PAWN7",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN7"));
            Piece bp8= new Piece("PAWN8",'B',"blackpawn"+selectedProfile.getSkinPieceName()+".png",getPositions("BPAWN8"));

            Piece bt2= new Piece("TOWER2",'B',"blacktower"+selectedProfile.getSkinPieceName()+".png",getPositions("BTOWER2"));

            Piece bk1= new Piece("KNIGHT1",'B',"blackknight"+selectedProfile.getSkinPieceName()+".png",getPositions("BKNIGHT1"));
            Piece bk2= new Piece("KNIGHT2",'B',"blackknight"+selectedProfile.getSkinPieceName()+".png",getPositions("BKNIGHT2"));

            Piece bb1= new Piece("BISHOP1",'B',"blackbishop"+selectedProfile.getSkinPieceName()+".png",getPositions("BBISHOP1"));
            Piece bb2= new Piece("BISHOP2",'B',"blackbishop"+selectedProfile.getSkinPieceName()+".png",getPositions("BBISHOP2"));

            Piece bq= new Piece("QUEEN",'B',"blackqueen"+selectedProfile.getSkinPieceName()+".png",getPositions("BQUEEN"));

            Piece bK= new Piece("KING",'B',"blackking"+selectedProfile.getSkinPieceName()+".png",getPositions("BKING"));

            //WHITES

            Piece wp1= new Piece("PAWN1",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN1"));
            Piece wp2= new Piece("PAWN2",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN2"));
            Piece wp3= new Piece("PAWN3",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN3"));
            Piece wp4= new Piece("PAWN4",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN4"));
            Piece wp5= new Piece("PAWN5",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN5"));
            Piece wp6= new Piece("PAWN6",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN6"));
            Piece wp7= new Piece("PAWN7",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN7"));
            Piece wp8= new Piece("PAWN8",'W',"whitepawn"+selectedProfile.getSkinPieceName()+".png",getPositions("WPAWN8"));

            Piece wt1= new Piece("TOWER1",'W',"whitetower"+selectedProfile.getSkinPieceName()+".png",getPositions("WTOWER1"));
            Piece wt2= new Piece("TOWER2",'W',"whitetower"+selectedProfile.getSkinPieceName()+".png",getPositions("WTOWER2"));

            Piece wk1= new Piece("KNIGHT1",'W',"whiteknight"+selectedProfile.getSkinPieceName()+".png",getPositions("WKNIGHT1"));
            Piece wk2= new Piece("KNIGHT2",'W',"whiteknight"+selectedProfile.getSkinPieceName()+".png",getPositions("WKNIGHT2"));

            Piece wb1= new Piece("BISHOP1",'W',"whitebishop"+selectedProfile.getSkinPieceName()+".png",getPositions("WBISHOP1"));
            Piece wb2= new Piece("BISHOP2",'W',"whitebishop"+selectedProfile.getSkinPieceName()+".png",getPositions("WBISHOP2"));

            Piece wq= new Piece("QUEEN",'W',"whitequeen"+selectedProfile.getSkinPieceName()+".png",getPositions("WQUEEN"));

            Piece wK= new Piece("KING",'W',"whiteking"+selectedProfile.getSkinPieceName()+".png",getPositions("WKING"));

            Piece bt1= new Piece("TOWER1",'B',"blacktower"+selectedProfile.getSkinPieceName()+".png",getPositions("BTOWER1"));

            casillas[bp1.getPos()].setPiece(bp1);
            casillas[bp2.getPos()].setPiece(bp2);
            casillas[bp3.getPos()].setPiece(bp3);
            casillas[bp4.getPos()].setPiece(bp4);
            casillas[bp5.getPos()].setPiece(bp5);
            casillas[bp6.getPos()].setPiece(bp6);
            casillas[bp7.getPos()].setPiece(bp7);
            casillas[bp8.getPos()].setPiece(bp8);

            casillas[bt2.getPos()].setPiece(bt2);

            casillas[bk1.getPos()].setPiece(bk1);
            casillas[bk2.getPos()].setPiece(bk2);

            casillas[bb1.getPos()].setPiece(bb1);
            casillas[bb2.getPos()].setPiece(bb2);

            casillas[bq.getPos()].setPiece(bq);
            casillas[bK.getPos()].setPiece(bK);

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

            casillas[bt1.getPos()].setPiece(bt1);
        }

    }

    /**Obtiene la posicion registrada en el historial*/
    public int getPositions(String name){
        Log.e("GET POSITIONS",name);
        String pos= history.getPosPieces().get(name);
        int value= Integer.parseInt(pos);

        if(value==-1){
            Log.e("LA PIEZA "+name, "NO DEBE APARECER EN TABLERO");
            return 0;
        }

        return value;
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
                    //Damos logros por posiciones
                    positionAchievementHandler(getProfileByPiece(anterior.getPiece()),anterior.getPiece(),position);
                    //Si hay una pieza, comprueba si es del mismo color
                    if(!siguiente.getPiece().getName().equals("EMPTY")){
                        //Coinciden colores
                        if(anterior.getPiece().getColor()==siguiente.getPiece().getColor()){
                            Log.w("","NO SE PUEDE MOVER A UNA CASILLA CON UNA FIGURA DEL MISMO COLOR");
                            //añadimos el logro de comer tu propia pieza
                            Profile current = getProfileByPiece(anterior.getPiece());
                            if(!hasAchievement(current,"Insaciable"))
                                current.addAchievement(new Achievement("Insaciable","Intenta comerte tu propia pieza"));
                        }else{
                            //añadimos puntos por comer pieza
                            addPointsEaten(anterior.getPiece(),siguiente.getPiece());
                            //logros por poner piezas en ciertas posiciones
                            //parte grafica------
                            //ponemos pieza de anterior en siguiente

                            //Guardamos la posicion de la pieza antes de ser eliminada
                            history.addPos(anterior.getPiece().getColor()+anterior.getPiece().getName(),Integer.toString(siguiente.getPiece().getPos()));
                            String eatenPiece= siguiente.getPiece().getColor()+siguiente.getPiece().getName();
                            siguiente.setPiece(anterior.getPiece());
                            //vaciamos anterior
                            history.addPos(eatenPiece,Integer.toString(-1));
                            anterior.setPiece(emptyPiece);
                            tablero.setAdapter(pieceAdapter);

                            //guardar movimiento---
                        }
                    }else{
                        //parte grafica------
                        //ponemos pieza de anterior en siguiente
                        history.addPos(anterior.getPiece().getColor()+anterior.getPiece().getName(),Integer.toString(siguiente.getPiece().getPos()));
                        siguiente.setPiece(anterior.getPiece());
                        //vaciamos anterior
                        history.addPos(anterior.getPiece().getColor()+anterior.getPiece().getName(),Integer.toString(siguiente.getPiece().getPos()));
                        anterior.setPiece(emptyPiece);
                        tablero.setAdapter(pieceAdapter);

                    }

                //guardar movimiento---
                String movimiento = translateCasilla(posCasillaSeleccionada) + "-->" + translateCasilla(position);
                history.addMove(movimiento);
                TextView lastMove = (TextView) findViewById(R.id.historyLog);
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

    private void positionAchievementHandler(Profile profile, Piece piece, int position) {
        //Si un Alfil esta en la diagonal
        if(piece.getName().equals("BISHOP") && (position == 0 || position ==  7 || position == 63|| position == 56)){
            if(!hasAchievement(profile,"Francotirador en posicion"))
                profile.addAchievement(new Achievement("Francotirador en posicion","Coloca un alfil en una esquina del tablero"));
        }
        if(piece.getName().equals("PAWN") && (position < 7 || position > 56)){
            if(!hasAchievement(profile,"Zona hostil"))
                profile.addAchievement(new Achievement("Zona hostil","Lleva un peón a la ultima fila del tablero"));
        }


        //Si un peon esta en la ultima linea

    }

    private void addPointsEaten(Piece piece, Piece comida) {
        Profile eater;
        piezasComidas.add(comida);

        //comprobamos quien comio y se añaden los puntos al jugador correspondiente
        if(piece.getColor() == 'W'){
            selectedProfile.setPoints(100);
            eater = selectedProfile;
        }
        else{
            selectedRival.setPoints(100);
            eater = selectedRival;
        }

        eatenAchivementHandler(eater,piece,comida);


    }

    private void eatenAchivementHandler(Profile eater,Piece piece,Piece eaten){

        //Logro por comer pieza
        //TODO:Arreglar que se añadan los puntos al perfil
        ArrayList<String> achivementsPlayer = eater.getAchievements();

        //añadimos el logro de voraz si no lo tiene
        if(!hasAchievement(eater,"voraz"))
                eater.addAchievement(new Achievement("Voraz","Come una pieza"));



        //Si es reyna añadimos el logro
        if(!hasAchievement(eater,"Al final si que era mortal") && eaten.getName().equals("QUEEN"))
            eater.addAchievement(new Achievement("Al final si que era mortal","Come una reina"));

        //Si comio dos torres añadimos logro
        if(!hasAchievement(eater,"Un dia Oscuro")) {
            int contadorTorres = 0;
            for (Piece p : piezasComidas) {
                if( piece.getColor() != p.getColor() && p.getName().equals("TOWER"))
                    contadorTorres++;
            }
            if(contadorTorres >=2)
                eater.addAchievement(new Achievement("Un dia Oscuro","Come las dos torres"));
        }

        //Si se han comido un rey
        if(eaten.getName().contains("KING")) {
            //Si es un rey
            char loserColor=eaten.getColor();
            Log.w("", "EL REY " + loserColor + " HA MUERTO");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //Si J1 es selectedProfile y se han comido al rey negro GANA PROFILE
            //Si J1 es selectedProfile y se han comido al rey blanco PIERDE PROFILE
            //Si J1 es selectedRival y se han comido al rey negro GANA RIVAL
            //Si J1 es selectedRival y se han comido al rey blanco PIERDE RIVAL
            if((turn && loserColor=='B') || (!turn && loserColor=='W')){
                builder.setTitle("El jugador "+selectedProfile.getName()+" ha ganado");
            }else{
                builder.setTitle("El jugador "+selectedRival.getName()+" ha ganado");
            }

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //INFORMA Y DEJA LA PARTIDA
                    GameActivity.this.setResult( MainActivity.RESULT_CANCELED );
                    GameActivity.this.finish();
                }
            });
            builder.create().show();

        }
    }

    public boolean hasAchievement(Profile player,String name){
        ArrayList<String> achivementsPlayer = player.getAchievements();
        for (String a : achivementsPlayer){
            if(a.equals(name))
                return true;
        }
        return false;
    }

    public Profile getProfileByPiece(Piece piece){
        if(piece.getColor() == 'W')
            return selectedProfile;
        else
            return selectedRival;
    }

    public String translateCasilla(int pos){
        String[] letras = {"a","b","c","d","e","f","g","h"};
        int filaArray = pos/8+1;
        int fila = 9-filaArray;
        int columna = pos - ((filaArray -1) * 8);
        return letras[columna]+fila;
    }
}
