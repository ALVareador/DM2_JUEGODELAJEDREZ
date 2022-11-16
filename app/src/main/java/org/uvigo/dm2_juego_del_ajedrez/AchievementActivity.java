package org.uvigo.dm2_juego_del_ajedrez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity {

    private ArrayList<Achievement> achievements = new ArrayList<>();
    private AchievementArrayAdapter achievementArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        //TODO Hay que añadir aqui todos los achievements que haya en el juego
        //TODO Los logros se disparan cada partida, se añaden puntos extras
        //Genericos
        achievements.add(new Achievement("Mente fria","Haz que un jugador abandone una partida contra ti"));
        achievements.add(new Achievement("Las reglas del juego","Gana una partida"));
        achievements.add(new Achievement("Todo es mas diveertido con amigos","Añade un amigo"));
        achievements.add(new Achievement("Voraz","Come una pieza"));

        //Piezas
        achievements.add(new Achievement("Al final si que era mortal","Come una reina"));
        achievements.add(new Achievement("Un dia Oscuro","Come las dos torres"));
        achievements.add(new Achievement("Zona hostil","Lleva un peón a la ultima fila del tablero"));
        achievements.add(new Achievement("Francotirador en posicion","Coloca un alfil en una esquina del tablero"));
        achievements.add(new Achievement("Cuestion de gustos","Intenta mover la pieza de otro jugador"));
        achievements.add(new Achievement("100 metros, vaya","Consigue transformar un peon en reina en una partida"));

        //SKINS
        achievements.add(new Achievement("Todo es mas bonito con color","Activa un aspecto del tablero"));
        achievements.add(new Achievement("Jugando a ser dios","Activa un aspecto de pieza"));


        ListView listView = findViewById(R.id.listViewAchievement);
        achievementArrayAdapter = new AchievementArrayAdapter(this, achievements);
        listView.setAdapter(achievementArrayAdapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listViewAchievement){
            getMenuInflater().inflate(R.menu.achievement_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position;
        switch (item.getItemId()){
            case(R.id.achievementMenuInfo):
                position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                showTaskNameDialog(position);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    private void showTaskNameDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pista: ");
        TextView textView = new TextView(this);
        textView.setText(achievements.get(position).getDescription());
        builder.setView(textView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}