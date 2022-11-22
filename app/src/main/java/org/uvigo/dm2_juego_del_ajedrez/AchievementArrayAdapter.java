package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AchievementArrayAdapter  extends ArrayAdapter<Achievement> {
    String mode;
    public AchievementArrayAdapter(@NonNull Context context, List<Achievement> objects, String mode) {
        super(context, 0, objects);
        this.mode=mode; //VISIBLE OR INVISIBLE
    }

    class ViewHolder {
        LinearLayout linearLayoutAchievement;
        TextView textViewName;
        ImageView im_blockedachievement;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.achievement_listview, null);

            viewHolder.linearLayoutAchievement = convertView.findViewById(R.id.linearLayoutAchievement);
            viewHolder.textViewName =convertView.findViewById(R.id.textViewName);
            viewHolder.im_blockedachievement = convertView.findViewById(R.id.im_blockedachievement);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textViewName.setText(getItem(position).getName());

        if(mode.equals("INVISIBLE")){
            viewHolder.im_blockedachievement.setVisibility(View.INVISIBLE);
        }


        //Si el logro es obtenido(checkbox activo) se pone en gris

        //TODO SACAR EL CANDADO O PONER EN VERDE LAS COMPLETADAS

        return convertView;
    }
}
