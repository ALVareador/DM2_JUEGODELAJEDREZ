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

        skins.add(new Skin("SKIN0","","",""));
        skins.add(new Skin("SKIN1","","",""));

        ListView listView = findViewById(R.id.listViewSkin);
        skinArrayAdapter = new SkinArrayAdapter(this, skins);
        listView.setAdapter(skinArrayAdapter);

        loadSkins();
        registerForContextMenu(listView);
    }

    @Override
    protected void onPause() {
        Log.e("WARN:","PAUSE");
        super.onPause();
        if(!skins.isEmpty()){
            saveSkins();
        }
    }

    @Override
    protected void onResume() {
        Log.e("WARN:","RESUME");
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
            loadSkins();
        }
    }

    private void saveSkins(){
        try (FileOutputStream f = this.openFileOutput( "skins_data.cfg", Context.MODE_PRIVATE ) )
        {
            PrintStream cfg = new PrintStream( f );

            for(Skin skin: this.skins) {
                cfg.println( skin.getName() ); //SKIN NAME
                cfg.println( skin.getImage()); //SKIN IMAGE
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
        try (FileInputStream f = this.openFileInput("skins_data.cfg") )
        {
            BufferedReader cfg = new BufferedReader( new InputStreamReader( f ) );

            this.skins.clear();
            String skinLine = cfg.readLine();
            while( skinLine != null ) {
                //Recuperamos cada skin
                Skin skin = new Skin(cfg.readLine(),cfg.readLine(),cfg.readLine(),cfg.readLine());
                this.skins.add(skin);

                skinLine = cfg.readLine();
            }

            cfg.close();
            Log.e( "WARN", "LOADED DATA" );

            this.skinArrayAdapter.notifyDataSetChanged();
        }
        catch (IOException exc)
        {
            Log.e( "WARN", "Error loading state" );
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
        builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}