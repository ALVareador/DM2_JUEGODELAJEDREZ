package org.uvigo.dm2_juego_del_ajedrez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AchievementActivity extends AppCompatActivity {

    private ArrayList<Achievement> achievements = new ArrayList<>();
    private AchievementArrayAdapter achievementArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("WARN:","CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        //TODO Los logros se disparan cada partida, se añaden puntos extras

        ListView listView = findViewById(R.id.listViewAchievement);
        achievementArrayAdapter = new AchievementArrayAdapter(this, achievements,"VISIBLE");
        listView.setAdapter(achievementArrayAdapter);

        loadAchievements();

        registerForContextMenu(listView);
    }

    @Override
    protected void onPause() {
        Log.e("WARN:","PAUSE");
        super.onPause();
        if(!achievements.isEmpty()){
            saveAchievements();
        }
    }

    @Override
    protected void onResume() {
        Log.e("WARN:","RESUME");
        super.onResume();
        if (achievements.isEmpty()) {
            loadAchievements();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("WARN:", "START");
        if (achievements.isEmpty()) {
            loadAchievements();
        }
    }

    private void saveAchievements(){
        Set<String> achievementSet = new HashSet<>();
        SharedPreferences.Editor editor = this.getPreferences( Context.MODE_PRIVATE ).edit();

        editor.clear();
        for( Achievement achievement: this.achievements ) {
            achievementSet.add( achievement.getName()+";"+achievement.getDescription());
            System.out.println( "Stored achievement: " + achievement.getName()  );
        }
        // Write settings
        editor.putStringSet( "achievements_prefs", achievementSet ); //Guarda prefs en achievements_prefs
        System.out.println( "Saved achievements: " + achievementSet);

        editor.apply();

        Log.e("WARN","Saved state..." );
    }

    private void loadAchievements(){
        Set<String> achievementSet;
        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );

        // Retrieve achievements
        this.achievements.clear();
        achievementSet = prefs.getStringSet( "achievements_prefs", new HashSet<String>()  );
        System.out.println( "Loaded: " + achievementSet.toString() );
        for(String achievement: achievementSet) {
            String[] achievementComponent= achievement.split(";");
            this.achievements.add( new Achievement(achievementComponent[0],achievementComponent[1]));
        }

        achievementSet.clear();
        achievementArrayAdapter.notifyDataSetChanged();

        Log.e( "WARN","Loaded state..." );
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
        if (item.getItemId()==R.id.achievementMenuInfo) {
            position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
            showAchievementClueDialog(position);
        }else{
            return super.onContextItemSelected(item);
        }
        return true;
    }

    private void showAchievementClueDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pista: ");
        TextView textView = new TextView(this);
        textView.setText(achievements.get(position).getDescription());
        builder.setView(textView);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
}