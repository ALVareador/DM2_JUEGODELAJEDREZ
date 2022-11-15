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
        achievements.add(new Achievement("Mente fria","Haz que un jugador abandone una partida contra ti"));
        achievements.add(new Achievement("Las reglas del juego","Gana una partida"));

        ListView listView = findViewById(R.id.listViewAchievement);
        achievementArrayAdapter = new AchievementArrayAdapter(this, achievements);
        listView.setAdapter(achievementArrayAdapter);

    }
}