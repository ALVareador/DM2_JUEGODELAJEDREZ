package org.uvigo.dm2_juego_del_ajedrez;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class pieceAdapter  extends BaseAdapter{
    Context context;
    Casilla[] casillas;

    public pieceAdapter(Context context, Casilla[] casillas) {
        this.context = context;
        this.casillas = casillas;
    }

    @Override
    public int getCount() {
        return casillas.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.board_box, viewGroup, false);
        }

        ImageView background = (ImageView) view.findViewById(R.id.boardbox_background);
        ImageView piece = (ImageView) view.findViewById(R.id.piece);
        background.setImageResource(casillas[i].DrawableFondo);
        //Todo: hacer que cambie la pieza
        if(casillas[i].DrawablePieza != -1)
        piece.setImageResource(casillas[i].DrawablePieza);

        return view;

    }
}
