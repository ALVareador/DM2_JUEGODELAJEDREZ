package org.uvigo.dm2_juego_del_ajedrez;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

 class ViewHolder  {
    public ImageView fondo;
    public ImageView pieza;

    //TODO Hacer qu coja las views quee tocan
    public ViewHolder (String fondo,String pieza){
        this.fondo = (ImageView) rowView.findViewById(fondo);
        this.pieza = pieza;
    }
}

class skinTablero{
     String claro;
    String oscuro;

    public skinTablero(String claro, String oscuro) {
        this.claro = claro;
        this.oscuro = oscuro;
    }

}

public class GameActivity extends AppCompatActivity {
    GridView tablero;
    ViewHolder [][] casillas;
    skinTablero skin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializar array casillas
        casillas = new ViewHolder [7][7];
        //TODO cambiar para qu funcion con las skins
        skin = new skinTablero("R.drawable.lightsquare","R.drawable.darksquare");

        //rellenamos los fondos

        for (int i = 0; i<7 ; i++){
            //primera lineea empiea por blanco
            for(int j = 0; j < 7 ; j++){
                //Si i par entonces eempiezo een blanco
                if(i%2==0){
                    //Si j par entonces casilla  blanco
                    if(j%2==0){
                        casillas[i][j] = new ViewHolder (skin.claro,"");
                    }else{//Entonces es negra
                        casillas[i][j] = new ViewHolder (skin.oscuro,"");
                    }
                }else{
                    //Si j par entonces casilla  blanco
                    if(j%2!=0){
                        casillas[i][j] = new ViewHolder (skin.claro,"");
                    }else{//Entonces es negra
                        casillas[i][j] = new ViewHolder (skin.oscuro,"");
                    }
                }

            }
        }


        //Coger el tablero
        tablero = findViewById(R.id.tablero);
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
