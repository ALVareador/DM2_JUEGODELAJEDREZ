package org.uvigo.dm2_juego_del_ajedrez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

        registerForContextMenu(listView);
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
            showTaskNameDialog(position);
        }else{
            return super.onContextItemSelected(item);
        }
        return true;
    }

    private void showTaskNameDialog(int position) {
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