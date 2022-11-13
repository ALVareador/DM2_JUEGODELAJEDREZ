package org.uvigo.dm2_juego_del_ajedrez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class FriendListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        ImageButton botonAtras = findViewById(R.id.AtrasButton);
        ImageButton botonBuscar = findViewById(R.id.buscarAmigoButton);
        ImageButton botonAnhadirAmigo = findViewById(R.id.AñadirAmigoButton);

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendListActivity.this.setResult( MainActivity.RESULT_CANCELED );
            }
        });
    }
}