package org.uvigo.dm2_juego_del_ajedrez;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private ActivityResultLauncher<Intent> activityResultLauncher;
    public static Profile selectedProfile; //Perfil seleccionado en la aplicacion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uploader.loadGlobalProfile(getApplicationContext());

        Button newGame = findViewById(R.id.BotonNuevaPartida);
        Button continueGame = findViewById(R.id.botonContinuarPartida);
        Button credits = findViewById(R.id.botonCreditos);
        Button exit = findViewById(R.id.botonSalir);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subActividad = new Intent( MainActivity.this, NewGameActivity.class );
                activityResultLauncher.launch(subActividad);
            }
        });

        continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subActividad = new Intent( MainActivity.this, CreditsActivity.class );
                //subActividad.putExtra( "data", 1 );
                activityResultLauncher.launch(subActividad);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ActivityResultContract<Intent, ActivityResult> contract = new ActivityResultContracts.StartActivityForResult();
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //TODO PREGUNTAR Recupera el perfil GENERAL
            }
        };

        this.activityResultLauncher = this.registerForActivityResult(contract, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate(R.menu.configuration_options, menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;
        Intent subActividad;
        switch( menuItem.getItemId() ) {
            case R.id.MenuConfiguracionLogros:
                subActividad = new Intent( MainActivity.this, AchievementActivity.class );

                subActividad.putExtra( "selectedprofile1", selectedProfile);
                activityResultLauncher.launch(subActividad);
                toret = true;
                break;
            case R.id.MenuConfiguracionSkins:
                subActividad = new Intent( MainActivity.this, SkinSelector.class );
                activityResultLauncher.launch(subActividad);
                toret = true;
                break;
            case R.id.MenuConfiguracionHistorial:
                subActividad = new Intent( MainActivity.this, HistoryActivity.class );
                activityResultLauncher.launch(subActividad);
                toret = true;
                break;
            case R.id.MenuConfiguracionAjustes:
                subActividad = new Intent( MainActivity.this, SettingsActivity.class );
                activityResultLauncher.launch(subActividad);
                toret = true;
                break;
            case R.id.MenuConfiguracionPerfiles:
                subActividad = new Intent( MainActivity.this, ProfileActivity.class );
                activityResultLauncher.launch(subActividad);
                toret = true;
                break;
        }
        return toret;
    }

    /** ProfileActivity puede modificar el perfil general de la aplicacion sin intents*/
    public static void setSelectedProfile(Context context, Profile profile){
        selectedProfile=profile;
        Uploader.saveGlobalProfile(context);
        Log.e("PERFIL ACTUALIZADO EN MAIN ACTIVITY: ",selectedProfile.getName());
        Toast.makeText(context, "Perfil seleccionado: "+selectedProfile.getName(), Toast.LENGTH_SHORT).show();
    }

    public static Profile getSelectedProfile(){
        return selectedProfile;
    }


}