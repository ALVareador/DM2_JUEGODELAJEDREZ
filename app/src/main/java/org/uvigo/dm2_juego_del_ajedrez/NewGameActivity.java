package org.uvigo.dm2_juego_del_ajedrez;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewGameActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ProfileArrayAdapter profileArrayAdapter;
    private ArrayList<Profile> rivals = new ArrayList<>();

    private Profile selectedProfile= MainActivity.getSelectedProfile();
    private Profile selectedRival= null;

    boolean normalMode; //TRUE NORMAL; FALSE RANDOM
    boolean turn; //FALSE J1 juega con negras; TRUE J2 juega con negras

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        //TODO Cargar rivals desde base de datos, todos los jugadores menos el selectedProfile loadRivals()
        rivals= loadRivals();

        ListView listView = findViewById(R.id.newGameOponentList);
        profileArrayAdapter = new ProfileArrayAdapter(this, rivals);
        listView.setAdapter(profileArrayAdapter);

        registerForContextMenu(listView);

        ImageButton white= (ImageButton)findViewById(R.id.newGameWhiteButton);
        ImageButton black= (ImageButton)findViewById(R.id.newGameBlackButton);

        Button normalModeButton= (Button)findViewById(R.id.NormalButton);
        Button randomModeButton= (Button)findViewById(R.id.RandomButton);

        Button startGame= (Button)findViewById(R.id.startButton);

        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),selectedProfile.getName()+" jugara blancas",Toast.LENGTH_SHORT).show();
                turn=true;
            }
        });

        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),selectedProfile.getName()+" jugara negras",Toast.LENGTH_SHORT).show();
                turn=false;
            }
        });

        normalModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Modo normal seleccionado",Toast.LENGTH_SHORT).show();
                normalMode=true;
            }
        });

        randomModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Modo Random seleccionado",Toast.LENGTH_SHORT).show();
                normalMode=false;
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subActividad = new Intent( NewGameActivity.this, GameActivity.class );

                subActividad.putExtra("mode",normalMode); //Enviamos el modo de juego
                subActividad.putExtra("rival",selectedRival); //Enviamos al rival
                subActividad.putExtra("turn",turn); //Enviamos el turno al juego

                subActividad.putExtra("type",true);

                activityResultLauncher.launch(subActividad);
            }
        });

        ActivityResultContract<Intent, ActivityResult> contract = new ActivityResultContracts.StartActivityForResult();
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        };

        this.activityResultLauncher = this.registerForActivityResult(contract, callback);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.newGameOponentList){
            getMenuInflater().inflate(R.menu.rivals_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position;
        if (item.getItemId()==R.id.chooseRival) {
            position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
            selectedRival=rivals.get(position);
            Toast.makeText(this, "Tu rival es "+rivals.get(position), Toast.LENGTH_SHORT).show();
        }else{
            return super.onContextItemSelected(item);
        }
        return true;
    }

    /**Carga los rivales disponibles desde BD */
    public ArrayList<Profile> loadRivals(){
        ArrayList<Profile> rivals= new ArrayList<>();
        rivals.add(new Profile("defaulRival1"));
        return rivals;
    }
}