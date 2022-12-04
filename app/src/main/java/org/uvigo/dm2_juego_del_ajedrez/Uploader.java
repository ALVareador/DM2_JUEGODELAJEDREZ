package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class Uploader extends AppCompatActivity {
    private static DBManager dbManager;
    private static Profile selectedProfile;
    private static ArrayList<Profile> profiles= new ArrayList<Profile>();

    /**Convierte una skin en los assets EXAMPLE: context, imageffffff#000000.png*/
    public static Bitmap bitmapFromAssets(Context context, String imagePath)
    {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(imagePath);
            return BitmapFactory.decodeStream(stream);
        }
        catch (Exception ignored) {}
        finally
        {
            try
            {
                if(stream != null)
                {
                    stream.close();
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    public static void updateHistory(Context context, History h){
        dbManager = new DBManager( context );
        Log.w("ACTUALIZA HISTORY","");
        dbManager.deleteHistory(h.getName());
        dbManager.addHistory(h);
    }

    public static void updateProfile(Context context, Profile profile){
        dbManager = new DBManager( context );
        Log.w("ACTUALIZA PROFILE","");
        dbManager.deleteProfile(profile.getName());
        dbManager.addProfile(profile);
    }

    public static void saveGlobalProfile(Context context){
        selectedProfile=MainActivity.getSelectedProfile();

        try (FileOutputStream f = context.openFileOutput( "global_data.cfg", Context.MODE_PRIVATE ) )
        {
            PrintStream cfg = new PrintStream( f );
            cfg.println( selectedProfile.getName() ); //PROFILE NAME
            cfg.println( selectedProfile.getImagePath()); //PROFILE IMAGE
            cfg.println( selectedProfile.getSkinBoardName()); //PROFILE BOARD
            cfg.println( selectedProfile.getSkinPieceName()); //PROFILE PIECE
            cfg.println( selectedProfile.getPoints()); //PROFILE POINTS
            cfg.println( selectedProfile.getAchievements().toString()); //PROFILE ACHIEVEMENTS
            cfg.println( selectedProfile.getFriends().toString()); //PROFILE FRIENDS

            cfg.close();
            Log.e( "WARN", "SAVED DATA" );
        }
        catch(IOException exc) {
            Log.e( "WARN", "Error saving state" );
        }
    }
    public static void loadGlobalProfile(Context context){

        try (FileInputStream f = context.openFileInput("global_data.cfg")){
            BufferedReader cfg = new BufferedReader( new InputStreamReader( f ) );

            String profileLine = cfg.readLine(); //Corresponde al nombre del perfil

            String cfg_image, cfg_board, cfg_piece, cfg_point, cfg_achievements, cfg_friends;
            while( profileLine != null ) {

                //Recuperamos cada perfil
                cfg_image= cfg.readLine();

                cfg_board= cfg.readLine();
                cfg_piece= cfg.readLine();

                cfg_point= cfg.readLine();

                cfg_achievements= cfg.readLine();
                cfg_friends= cfg.readLine();

                Log.e("CHARGED_DATA",profileLine+" "+cfg_image);
                selectedProfile= new Profile(profileLine,cfg_image, cfg_board, cfg_piece, Integer.parseInt(cfg_point), cfg_achievements, cfg_friends);

                MainActivity.setSelectedProfile(context, selectedProfile);

                profileLine = cfg.readLine();
            }

            cfg.close();

            if(selectedProfile.getName().equals("")){
                MainActivity.setSelectedProfile(context, new Profile("default"));
                Toast.makeText(context, "Seleccionado perfil default", Toast.LENGTH_SHORT).show();
            }

            Log.e( "WARN", "LOADED DATA: "+selectedProfile.toString() );
        }
        catch (IOException exc)
        {
            Log.e( "WARN", "Error loading state" );
        }
    }

    public static ArrayList<Profile> getProfiles(){
        return profiles;
    }

    /**Guarda los perfiles dados como profilesToSave*/
    public static void saveProfiles(Context context, ArrayList<Profile> profilesToSave){

        //Si no hay skins en el momento de guardar es la primera ejecucion y tenemos que generar todas las skins
        if(profilesToSave.isEmpty()){
            Profile defaultProfile= new Profile("default");
            profilesToSave.add(defaultProfile);
            selectedProfile= defaultProfile;
        }

        try (FileOutputStream f = context.openFileOutput( "profile_data.cfg", Context.MODE_PRIVATE ) )
        {
            PrintStream cfg = new PrintStream( f );

            for(Profile profile: profilesToSave) {
                Log.e("SAVEPROFILE",profile.toString());
                cfg.println( profile.getName() ); //PROFILE NAME
                cfg.println( profile.getImagePath()); //PROFILE IMAGE
                cfg.println( profile.getSkinBoardName()); //PROFILE BOARD
                cfg.println( profile.getSkinPieceName()); //PROFILE PIECE
                cfg.println( profile.getPoints()); //PROFILE POINTS
                cfg.println( profile.getAchievements().toString()); //PROFILE ACHIEVEMENTS
                cfg.println( profile.getFriends().toString()); //PROFILE FRIENDS
            }

            cfg.close();
            Log.e( "WARN", "SAVED DATA" );
        }
        catch(IOException exc) {
            Log.e( "WARN", "Error saving state" );
        }
    }

    /** Devuelve una arraylist con los perfiles permanentes*/
    public static ArrayList<Profile> loadProfiles(Context context){
        Log.e("",context.getFilesDir().toString());
        profiles.clear();
        try (FileInputStream f = context.openFileInput("profile_data.cfg")){
            BufferedReader cfg = new BufferedReader( new InputStreamReader( f ) );

            String profileLine = cfg.readLine(); //Corresponde al nombre del perfil

            String cfg_image, cfg_board, cfg_piece, cfg_point, cfg_achievements, cfg_friends;
            while( profileLine != null ) {

                //Recuperamos cada perfil
                cfg_image= cfg.readLine();

                cfg_board= cfg.readLine();
                cfg_piece= cfg.readLine();

                cfg_point= cfg.readLine();

                cfg_achievements= cfg.readLine();
                cfg_friends= cfg.readLine();

                Log.e("CHARGED_DATA",profileLine+" "+cfg_image);
                profiles.add(new Profile(profileLine,cfg_image, cfg_board, cfg_piece, Integer.parseInt(cfg_point), cfg_achievements, cfg_friends));

                profileLine = cfg.readLine();
            }

            cfg.close();
            Log.e( "WARN", "LOADED DATA: "+profiles.toString() );

        }
        catch (IOException exc)
        {
            Log.e( "WARN", "Error loading state" );
        }

        return profiles;
    }
}
