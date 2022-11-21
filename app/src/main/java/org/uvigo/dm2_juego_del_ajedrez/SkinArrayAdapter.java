package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
            //viewHolder.buttonUseProfile = convertView.findViewById(R.id.buttonUseProfile);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textViewName.setText(getItem(position).getName());

        //Si esta siendo usado se pone en gris
        //viewHolder.buttonUseProfile.

        return convertView;
    }
}
