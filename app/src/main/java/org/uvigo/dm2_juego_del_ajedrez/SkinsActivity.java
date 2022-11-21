package org.uvigo.dm2_juego_del_ajedrez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class SkinsActivity extends AppCompatActivity {

    private ArrayList<Skin> skins = new ArrayList<>();
    private SkinArrayAdapter skinArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins);

        //skins.clear();
        //skins.add(new Skin("SKIN0","./imageFFFFFF000000.png","#FFFFFF","#000000"));
        //skins.add(new Skin("SKIN1","./image000000FFFFFF.png","#000000","#FFFFFF"));

        //saveSkins();
        //imageView.setColorFilter(color); Aplica un color

        ListView listView = findViewById(R.id.listViewSkin);

        Log.e("SKINS: ",skins.toString());
        skinArrayAdapter = new SkinArrayAdapter(this, skins);
        listView.setAdapter(skinArrayAdapter);

        //loadSkins();

        Log.e("",skinArrayAdapter.toString());
        registerForContextMenu(listView);
    }

    @Override
    protected void onPause() {
        Log.e("WARN:","PAUSE: "+skins.toString());
        super.onPause();
        if(!skins.isEmpty()){
            saveSkins();
        }
    }

    @Override
    protected void onResume() {
        Log.e("WARN:","RESUME: "+skins.toString());
        super.onResume();
        if (skins.isEmpty()) {
            loadSkins();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("WARN:", "START");
        if (skins.isEmpty()) {
            Log.e("","LOAD SKINS");
            loadSkins();
        }
    }

    private void saveSkins(){
        try (FileOutputStream f = this.openFileOutput( "skins_data.cfg", Context.MODE_PRIVATE ) )
        {
            PrintStream cfg = new PrintStream( f );

            for(Skin skin: this.skins) {
                Log.e("SAVESKIN",skin.toString());
                cfg.println( skin.getName() ); //SKIN NAME
                cfg.println( skin.getImagePath()); //SKIN IMAGE
                cfg.println( skin.getLightcolor() ); //SKIN COLOR 1
                cfg.println( skin.getDarkcolor() ); //SKIN COLOR 2
            }

            cfg.close();
            Log.e( "WARN", "SAVED DATA" );
        }
        catch(IOException exc) {
            Log.e( "WARN", "Error saving state" );
        }
    }

    private void loadSkins(){
        try (FileInputStream f = this.openFileInput("skins_data.cfg")){
            BufferedReader cfg = new BufferedReader( new InputStreamReader( f ) );

            this.skins.clear();
            String skinLine = cfg.readLine(); //Corresponde al nombre de la skin
            Log.e("",skinLine);

            String cfg_image,cfg_lightcolor,cfg_darkcolor;
            while( skinLine != null ) {

                //Recuperamos cada skin
                cfg_image= cfg.readLine();
                cfg_lightcolor= cfg.readLine();
                cfg_darkcolor= cfg.readLine();

                Log.e("CHARGED_DATA",skinLine+" "+cfg_image+" "+cfg_lightcolor+" "+cfg_darkcolor);
                this.skins.add(new Skin(skinLine,cfg_image,cfg_lightcolor,cfg_darkcolor));

                skinLine = cfg.readLine();
            }

            cfg.close();
            Log.e( "WARN", "LOADED DATA" );
        }
        catch (IOException exc)
        {
            Log.e( "WARN", "Error loading state" );
        }finally {
            skinArrayAdapter.notifyDataSetChanged();
            Log.e("","DataSetChanged");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listViewSkin){
            getMenuInflater().inflate(R.menu.skin_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position;
        if (item.getItemId()==R.id.skinInfo) {
            position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
            showSkinNameDialog(position);
        }else{
            return super.onContextItemSelected(item);
        }
        return true;
    }

    private void showSkinNameDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Skin: ");
        ImageView imageView = new ImageView(this);
        imageView.setImageIcon(skins.get(position).getImage());

        builder.setView(imageView);
        builder.setPositiveButton("CLOSE", null);

        builder.create().show();
    }
}