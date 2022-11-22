package org.uvigo.dm2_juego_del_ajedrez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Profile selectedProfile= (Profile)getIntent().getSerializableExtra("selectedprofile"); // TODO RECUPERAR EL PERFIL 1 SELECCIONADO AL INICIAR LA PARTIDA
        Profile selectedProfile2= (Profile)getIntent().getSerializableExtra("selectedprofile2"); // TODO RECUPERAR EL PERFIL 2 SELECCIONADO AL INICIAR LA PARTIDA
        new ChessGame(selectedProfile,selectedProfile2,new char[]{0,0});
    }
}