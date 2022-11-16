package org.uvigo.dm2_juego_del_ajedrez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity {

    private ArrayList<Achievement> achievements = new ArrayList<>();
    private AchievementArrayAdapter achievementArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        //TODO Hay que añadir aqui todos los achievements que haya en el juego
        //TODO Los logros se disparan cada partida
        //Genericos
        achievements.add(new Achievement("Mente fria","Haz que un jugador abandone una partida contra ti"));
        achievements.add(new Achievement("Las reglas del juego","Gana una partida"));
        achievements.add(new Achievement("Todo es mas diveertido con amigos","Añade un amigo"));
        achievements.add(new Achievement("Voraz","Come una pieza"));

        //Piezas
        achievements.add(new Achievement("Al final si que era mortal","Come una reina"));
        achievements.add(new Achievement("Un día Oscuro","Come las dos torres"));
        achievements.add(new Achievement("Zona hostíl","Lleva un peón a la última fila del tablero"));
        achievements.add(new Achievement("Francotirador en posición","Coloca un alfil en una esquina del tablero"));

        //SKINS
        achievements.add(new Achievement("Todo es mas bonito con color","Activa un aspecto del tablero"));

        ListView listView = findViewById(R.id.listViewAchievement);
        achievementArrayAdapter = new AchievementArrayAdapter(this, achievements);
        listView.setAdapter(achievementArrayAdapter);
    }
}