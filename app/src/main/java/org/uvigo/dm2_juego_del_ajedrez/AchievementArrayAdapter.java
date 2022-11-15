package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AchievementArrayAdapter  extends ArrayAdapter<Achievement> {

    public AchievementArrayAdapter(@NonNull Context context, List<Achievement> objects) {
        super(context, 0, objects);
    }

    class ViewHolder {
        LinearLayout linearLayoutAchievement;
        TextView textViewName;
        CheckBox checkbox_achievement;
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
            viewHolder.checkbox_achievement = convertView.findViewById(R.id.checkbox_achievement);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textViewName.setText(getItem(position).getName());

        //Si el logro es obtenido(checkbox activo) se pone en gris
        if(viewHolder.checkbox_achievement.isChecked()){
            //TODO Cambiar a poner en gris
            viewHolder.textViewName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return convertView;
    }
}
