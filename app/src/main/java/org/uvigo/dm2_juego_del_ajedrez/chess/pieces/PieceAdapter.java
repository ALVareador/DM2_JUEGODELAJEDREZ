package org.uvigo.dm2_juego_del_ajedrez.chess.pieces;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.uvigo.dm2_juego_del_ajedrez.R;
import org.uvigo.dm2_juego_del_ajedrez.core.Uploader;
import org.uvigo.dm2_juego_del_ajedrez.chess.board.BoardBox;

/**
 * Adaptador para panejar los eventos del tablero
 *
 * @author Ruben Gomez Martinez
 * @author Alvaro Novoa Fernandez
 * @author Andres Garcia Figueroa
 */
public class PieceAdapter extends BaseAdapter{
    Context context;
    BoardBox[] boardBoxes;

    /**
     * Construye e inicializa el adaptador
     *
     * @param context   contexto actual
     * @param casillas  array con las casillas del tablero
     */
    public PieceAdapter(Context context, BoardBox[] casillas) {
        this.context = context;
        this.boardBoxes = casillas;
    }

    /**
     * Devuelve el numero de casillas del tablero
     *
     * @return  el numero de casillas del tablero
     */
    @Override
    public int getCount() {
        return boardBoxes.length;
    }

    /**
     * Devuelve la pieza de la casilla especificada en el array
     *
     * @param i posicion de la pieza a devolver
     * @return  pieza en la casilla especificada en el array
     */
    @Override
    public Object getItem(int i) {
        return boardBoxes[i];
    }

    /**
     * Devuelve 0
     *
     * @param i nota a este pedazo de metodo :)
     * @return  devulve 0
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Dibuja la casilla especificada en el tablero y devuleve la vista dibujada
     *
     * @param i         casilla a dibujar
     * @param view      vista en la que estamos dibujando
     * @param viewGroup vista padre de la vista en la que estamos dibujando
     * @return          la vista dibujada
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.board_box, viewGroup, false);
        }

        //FONDO
        ImageView background = (ImageView) view.findViewById(R.id.boardbox_background);
        //PIEZA
        ImageView piece = (ImageView) view.findViewById(R.id.piece);

        //Si no es la ultima casilla, rellena normal
        if(i!=64){
            background.setBackgroundColor(boardBoxes[i].getDrawableBackground());
            if(boardBoxes[i].drawablePieza != "")
                piece.setImageBitmap(Uploader.bitmapFromAssets(context,boardBoxes[i].getDrawablePiece()));
        }else{
            //Sino pinta d eun color distinto
            background.setBackgroundColor(Color.parseColor("#444444"));
            piece.setImageBitmap(Uploader.bitmapFromAssets(context,"deadPieces.png"));
        }

        return view;
    }
}
