package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProfileArrayAdapter  extends ArrayAdapter<Profile> {

    public ProfileArrayAdapter(@NonNull Context context, List<Profile> objects) {
        super(context, 0, objects);
    }

    class ViewHolder {
        LinearLayout linearLayoutProfile;
        TextView textViewName;
        Button buttonUseProfile;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.profile_listview, null);

            viewHolder.linearLayoutProfile = convertView.findViewById(R.id.linearLayoutProfile);
            viewHolder.textViewName =convertView.findViewById(R.id.textViewName);
            viewHolder.buttonUseProfile = convertView.findViewById(R.id.buttonUseProfile);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textViewName.setText(getItem(position).getName());

        //Si esta siendo usado se pone en gris
        //viewHolder.buttonUseProfile.

        return convertView;
    }
}
