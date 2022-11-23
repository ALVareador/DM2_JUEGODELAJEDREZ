package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
        viewHolder.textViewName.setText(getItem(position).getName());

        Skin skin= getItem(position);

        //Actualizamos la imageBitmap
        skin.setImageBitmap(bitmapFromAssets(getContext(),skin));

        viewHolder.iv_SkinPhoto.setImageBitmap(bitmapFromAssets(getContext(),skin));

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

        notifyDataSetChanged();
        return convertView;
    }

    public Bitmap bitmapFromAssets(Context context, Skin skin)
    {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(skin.getImagePath());
            return BitmapFactory.decodeStream(stream);
        }
        catch (Exception ignored) {}
        finally
        {
            try
            {
                if(stream != null)
                {
                    stream.close();
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

}
