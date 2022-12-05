package org.uvigo.dm2_juego_del_ajedrez.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.uvigo.dm2_juego_del_ajedrez.R;
import org.uvigo.dm2_juego_del_ajedrez.core.Profile;
import org.uvigo.dm2_juego_del_ajedrez.core.Skin;
import org.uvigo.dm2_juego_del_ajedrez.core.Uploader;

import java.util.ArrayList;
import java.util.List;

public class SkinArrayAdapter extends ArrayAdapter<Skin> {
    boolean mode;
    ArrayList<Profile> profiles;
    Profile selectedProfile= MainActivity.getSelectedProfile();

    public SkinArrayAdapter(@NonNull Context context, List<Skin> objects, boolean mode) {
        super(context, 0, objects);
        this.mode=mode;
        profiles=Uploader.loadProfiles(getContext());

        Log.w("SKINARRAYADAPTER",objects.toString());
    }

    class ViewHolder {
        LinearLayout linearLayoutSkins;
        LinearLayout backButtonLayoutSpace;
        LinearLayout backButtonLayout;
        ImageButton backButton;
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

            viewHolder.backButton = convertView.findViewById(R.id.backButton);
            viewHolder.backButtonLayoutSpace = convertView.findViewById(R.id.backButtonLayoutSpace);
            viewHolder.backButtonLayout = convertView.findViewById(R.id.backButtonLayout);
            viewHolder.linearLayoutSkins = convertView.findViewById(R.id.linearLayoutSkins);
            viewHolder.iv_SkinPhoto = convertView.findViewById(R.id.iv_SkinPhoto);
            viewHolder.textViewName =convertView.findViewById(R.id.textViewName);
            viewHolder.useSkin= convertView.findViewById(R.id.useSkin);

            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textViewName.setText(getItem(position).getName());

        Skin skin= getItem(position);


        viewHolder.iv_SkinPhoto.setImageBitmap(Uploader.bitmapFromAssets(getContext(),skin.getImagePath()+".png"));


        viewHolder.useSkin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String imagePath=getItem(position).getImagePath();

                if(mode){
                    selectedProfile.setSkinBoardName(imagePath);
                    Toast.makeText(SkinArrayAdapter.super.getContext(), "El perfil "+selectedProfile.getName()+" ha cambiado su skin de tablero a "+imagePath, Toast.LENGTH_SHORT).show();
                }else{
                    selectedProfile.setSkinPieceName(imagePath);
                    Toast.makeText(SkinArrayAdapter.super.getContext(), "El perfil "+selectedProfile.getName()+" ha cambiado su skin de pieza a "+imagePath, Toast.LENGTH_SHORT).show();
                }
                updateProfiles();
                notifyDataSetChanged();
            }
        });

        notifyDataSetChanged();
        return convertView;
    }

    public void updateProfiles(){

        ArrayList<Profile> tempProfiles= profiles;
        Profile tempSP=null;

        for(Profile pr: profiles){
            if(selectedProfile.getName().equals(pr.getName())){
                //Quitamos el selected profile
                Log.e("PERFIL",selectedProfile.toString()+" eliminado");
                tempSP=pr;
            }
        }

        if(tempSP!=null){
            tempProfiles.remove(tempSP);
        }

        tempProfiles.add(selectedProfile);

        Log.e("PERFIL ACTUALIZADO A",selectedProfile.toString());

        Uploader.saveProfiles(getContext(),tempProfiles);
    }
}
