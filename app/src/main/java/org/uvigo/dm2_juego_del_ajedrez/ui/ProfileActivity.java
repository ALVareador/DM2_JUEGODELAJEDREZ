package org.uvigo.dm2_juego_del_ajedrez.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.uvigo.dm2_juego_del_ajedrez.R;
import org.uvigo.dm2_juego_del_ajedrez.core.GameMusic;
import org.uvigo.dm2_juego_del_ajedrez.core.Profile;
import org.uvigo.dm2_juego_del_ajedrez.core.Uploader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProfileActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private ArrayList<Profile> profiles = new ArrayList<>();

    private ProfileArrayAdapter profileArrayAdapter;

    public Profile selectedProfile;

    private ImageButton backButton;
    private ListView listView;
    private GameMusic music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profiles.add(new Profile("default"));

        listView = findViewById(R.id.listViewProfile);
        profiles= Uploader.loadProfiles(getApplicationContext());
        profileArrayAdapter = new ProfileArrayAdapter(this, profiles);


        //El perfil seleccionado sera por defecto el default, sino cambiar
        try{
            selectedProfile= MainActivity.getSelectedProfile();
            Toast.makeText(this, "El perfil seleccionado es "+selectedProfile.getName(), Toast.LENGTH_SHORT).show();
        }catch(NullPointerException e){
            selectedProfile=new Profile("default");
            profileArrayAdapter.notifyDataSetChanged();
            Toast.makeText(this, "El perfil seleccionado es default", Toast.LENGTH_SHORT).show();
        }

        listView.setAdapter(profileArrayAdapter);

        registerForContextMenu(listView);

        //Si se le da al boton de añadir perfil
        findViewById(R.id.buttonAddProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfile();
            }
        });

        ActivityResultContract<Intent, ActivityResult> contract = new ActivityResultContracts.StartActivityForResult();
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //cargarInterfaz();

            }
        };

        this.activityResultLauncher = this.registerForActivityResult(contract, callback);

        music = MainActivity.getMusic();

        backButton = this.findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGlobalSelectedProfile();
                ProfileActivity.this.setResult( MainActivity.RESULT_CANCELED );
                ProfileActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        profiles= Uploader.loadProfiles(getApplicationContext());

        listView = findViewById(R.id.listViewProfile);
        profileArrayAdapter = new ProfileArrayAdapter(this, profiles);
        listView.setAdapter(profileArrayAdapter);

        //Empezamos la musica
        music.onContinue(getApplicationContext());

        registerForContextMenu(listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        profiles= Uploader.loadProfiles(getApplicationContext());
        //Empezamos la musica
        music.onContinue(getApplicationContext());

        profileArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("STOP","");
        //Guardamos en el uploader
        updateProfiles();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("PAUSE","");
        //Guardamos en el uploader
        updateProfiles();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listViewProfile){
            getMenuInflater().inflate(R.menu.profile_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position= ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position;

        switch (item.getItemId()){
            case(R.id.profileMenuInfo):
                position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;

                Profile infoProfile= getProfileByName(profiles.get(position).getName());
                Intent subActividad = new Intent( ProfileActivity.this, VisualizeProfileActivity.class );
                subActividad.putExtra( "visualizeprofile", infoProfile);
                activityResultLauncher.launch(subActividad);
                break;
            case(R.id.profileMenuAddFriend):
                position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                Profile addProfile= getProfileByName(profiles.get(position).getName());
                selectedProfile.addFriend(getApplicationContext(),addProfile);
                Toast.makeText( this, "Has añadido a "+addProfile.getName()+" como amigo", Toast.LENGTH_SHORT ).show();
                profileArrayAdapter.notifyDataSetChanged();

                //Guardamos en el uploader
                updateProfiles();
                break;
            case(R.id.profileMenuRemoveFriend):
                position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                Profile removedProfile= getProfileByName(profiles.get(position).getName());
                Log.e("FRINED TO REMOVE",removedProfile.getName());
                if(selectedProfile.getFriends().contains(removedProfile.getName())){
                    selectedProfile.removeFriend(removedProfile);
                    Toast.makeText( this, removedProfile.getName()+" ya no es tu amigo", Toast.LENGTH_SHORT ).show();
                }else{
                    Toast.makeText( this, removedProfile.getName()+" no es tu amigo", Toast.LENGTH_SHORT ).show();
                }
                profileArrayAdapter.notifyDataSetChanged();
                //Guardamos en el uploader
                updateProfiles();
                break;
            case(R.id.profileMenuUse):
                Log.w("","WARN: PROFILEMENUUSE");
                position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                //CAMBIA EL PERFIL SELECCIONADO

                selectedProfile=getProfileByName(profiles.get(position).getName());
                Log.e("","Perfil posterior: "+selectedProfile.toString());

                Toast.makeText( this, "Perfil seleccionado: "+selectedProfile.getName(), Toast.LENGTH_SHORT ).show();
                changeGlobalSelectedProfile();
                break;
            case(R.id.profileMenuEdit):
                position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                doEdit(position);
                break;

            case(R.id.profileMenuDelete):
                position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                profiles.remove(position);
                Toast.makeText( this, "Perfil eliminado correctamente", Toast.LENGTH_SHORT ).show();
                profileArrayAdapter.notifyDataSetChanged();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public void saveProfile(Profile p){

        Log.e( "WARN", "creating user File" );
        File temp = new File(p.getName()+".cfg");
        temp.delete();
        try (FileOutputStream f = this.openFileOutput( p.getName()+".cfg", Context.MODE_PRIVATE ) )
        {
            PrintStream cfg = new PrintStream( f );


            Log.e("SAVEPROFILE",p.toString());
            cfg.println( p.getName() ); //PROFILE NAME
            cfg.println( p.getImagePath()); //PROFILE IMAGE
            cfg.println( p.getSkinBoardName()); //PROFILE BOARD
            cfg.println( p.getSkinPieceName()); //PROFILE PIECE
            cfg.println( p.getPoints()); //PROFILE POINTS
            cfg.println( p.getAchievements().toString()); //PROFILE ACHIEVEMENTS
            cfg.println( p.getFriends().toString()); //PROFILE FRIENDS


            cfg.close();
            Log.e( "WARN", "SAVED DATA" );
        }
        catch(IOException exc) {
            Log.e( "WARN", "Error saving state" );
        }
    }

    /** Añade un nuevo perfil*/
    private void addProfile() {
        profiles.add(new Profile());
        showEditNameDialog(profiles.size()-1);
        //Guardamos en el uploader
        updateProfiles();
    }

    /**Cambia el perfil global*/
    private void changeGlobalSelectedProfile(){
        try{
            Log.e("PERFIL ACTUALIZADO: ",selectedProfile.getName());
            MainActivity.setSelectedProfile(getApplicationContext(),selectedProfile);
        }catch(NullPointerException e){
            Toast.makeText(this, "No se ha seleccionado ningun perfil, por favor selecciona uno", Toast.LENGTH_LONG).show();
        }
    }

    /** Añade al nuevo profile un nombre, o modifica un nombre*/
    private void showEditNameDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Profile Name");
        EditText editText = new EditText(this);
        editText.setText(profiles.get(position).getName());
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String profileName = editText.getText().toString();
                profiles.get(position).setName(profileName);
                profileArrayAdapter.notifyDataSetChanged();

                //updateProfiles();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    /** Modifica la photo de perfil*/
    private void showEditPhotoDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Profile Photo Path");
        EditText editText = new EditText(this);
        editText.setText(profiles.get(position).getImagePath());
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String imageName = editText.getText().toString();
                profiles.get(position).setImage(imageName);
                profileArrayAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    /** Permite editar el nombre y la imagen de un perfil*/
    private void doEdit(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to modify the profile photo?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //MODIFICA LA RUTA DE LA FOTO
                showEditPhotoDialog(position);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();



        builder.setTitle("Do you want to modify the profile name?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //MODIFICA EL NOMBRE
                showEditNameDialog(position);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();

    }

    /**Devuelve un perfil sobre el nombre*/
    public Profile getProfileByName(String name){
        Log.e("","GETPROFILEBYNAME");
        Profile pr;
        int i=0;
        int toret=-1;
        Log.e("","SIZE: "+profiles.size());

        while(i<=profiles.size()-1){
            pr= profiles.get(i);
            Log.e("","PROFILE ENCONTRADO: "+pr.toString());
            //Encuentra el perfil
            Log.e("","NAME ENCONTRADO: "+pr.getName());
            if(name.equals(pr.getName())){
                toret=i;
            }
            i++;
        }

        return profiles.get(toret);

    }

    /**Actualiza los perfiles generales*/
    public void updateProfiles(){

        ArrayList<Profile> tempProfiles= profiles;
        Profile tempSP=null;

        for(Profile pr: profiles){
            if(selectedProfile.getName().equals(pr.getName())){
                //Quitamos el selected profile
                Log.e("PERFIL",selectedProfile.toString()+" eliminado");
                tempSP=pr;
            }
        }

        if(tempSP!=null){
            tempProfiles.remove(tempSP);
        }

        tempProfiles.add(selectedProfile);

        Log.e("PERFIL ACTUALIZADO A",selectedProfile.toString());

        Uploader.saveProfiles(getApplicationContext(),tempProfiles);
    }

}