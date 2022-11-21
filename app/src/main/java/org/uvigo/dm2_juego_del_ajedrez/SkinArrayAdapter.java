package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SkinArrayAdapter extends ArrayAdapter<Skin> {

    public SkinArrayAdapter(@NonNull Context context, List<Skin> objects) {
        super(context, 0, objects);
        Log.w("SKINARRAYADAPTER",objects.toString());
    }

    class ViewHolder {
        LinearLayout linearLayoutSkins;
        ImageView iv_SkinPhoto;
        TextView textViewName;
        CheckBox useSkin;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.skin_listview, null);

            viewHolder.linearLayoutSkins = convertView.findViewById(R.id.linearLayoutSkins);
            viewHolder.iv_SkinPhoto = convertView.findViewById(R.id.iv_SkinPhoto);
            viewHolder.textViewName =convertView.findViewById(R.id.textViewName);
            viewHolder.useSkin= convertView.findViewById(R.id.useSkin);

            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        Log.w("",getItem(position).getName());
        viewHolder.textViewName.setText(getItem(position).getName());

        //Si esta siendo usado se pone en gris
        if (getItem(position).getUsed()){
            viewHolder.textViewName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            viewHolder.textViewName.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        }

        viewHolder.useSkin.setChecked(getItem(position).getUsed());
        viewHolder.useSkin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getItem(position).setUsed(isChecked);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
