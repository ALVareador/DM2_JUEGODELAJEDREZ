package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
        LinearLayout backButtonLayoutSpace;
        LinearLayout backButtonLayout;
        ImageButton backButton;
        TextView textViewName;
        TextView textViewPoints;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.profile_listview, null);

            viewHolder.backButton = convertView.findViewById(R.id.backButton);
            viewHolder.backButtonLayoutSpace = convertView.findViewById(R.id.backButtonLayoutSpace);
            viewHolder.backButtonLayout = convertView.findViewById(R.id.backButtonLayout);
            viewHolder.linearLayoutProfile = convertView.findViewById(R.id.linearLayoutProfile);
            viewHolder.textViewName =convertView.findViewById(R.id.textViewName);
            viewHolder.textViewPoints = convertView.findViewById(R.id.points_counter);

            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textViewName.setText(getItem(position).getName());
        viewHolder.textViewPoints.setText(getItem(position).getPoints());

        //Si esta siendo usado se pone en gris
        //viewHolder.buttonUseProfile.

        return convertView;
    }
}
