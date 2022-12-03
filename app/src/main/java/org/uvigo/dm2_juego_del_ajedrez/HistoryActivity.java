package org.uvigo.dm2_juego_del_ajedrez;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private static DBManager dbManager;
    private SimpleCursorAdapter dbAdapter;

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        this.dbManager = new DBManager( this.getApplicationContext() );

        //BACKBUTTON
        backButton = this.findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.this.setResult( MainActivity.RESULT_CANCELED );
                HistoryActivity.this.finish();
            }
        });

        ListView listView = this.findViewById( R.id.listViewHistory );
        this.dbAdapter=new SimpleCursorAdapter(this, R.layout.history_listview, null, new String[] { DBManager.HISTORY_NAME, DBManager.HISTORY_LOG }, new int[] { R.id.textViewName}, 0);

        dbAdapter.changeCursor( dbManager.getHistoriesByName(MainActivity.getSelectedProfile().getName()));
        Switch allHistories= findViewById(R.id.switchHistory);

        //Comprueba si el switch está activo
        allHistories.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // ALL HISTORY
                    Log.e("true","USER");
                    dbAdapter.changeCursor( dbManager.getHistories());
                } else {
                    // HISTORY USUARIOS
                    Log.e("true","ALL");
                    dbAdapter.changeCursor( dbManager.getHistoriesByName(MainActivity.getSelectedProfile().getName()));
                }
            }
        });

        dbAdapter.notifyDataSetChanged();
        listView.setAdapter( this.dbAdapter );

        ActivityResultContract<Intent, ActivityResult> contract = new ActivityResultContracts.StartActivityForResult();
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        };

        this.activityResultLauncher = this.registerForActivityResult(contract, callback);
        registerForContextMenu(listView);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView listView = this.findViewById(R.id.listViewHistory);
        this.dbAdapter = new SimpleCursorAdapter(this, R.layout.history_listview, null, new String[]{DBManager.HISTORY_NAME, DBManager.HISTORY_LOG}, new int[]{R.id.textViewName}, 0);

        dbAdapter.changeCursor( dbManager.getHistoriesByName(MainActivity.getSelectedProfile().getName()));
        Switch allHistories= findViewById(R.id.switchHistory);

        //Comprueba si el switch está activo
        allHistories.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // ALL HISTORY
                    Log.e("true","USER");
                    dbAdapter.changeCursor( dbManager.getHistories());
                } else {
                    // HISTORY USUARIOS
                    Log.e("true","ALL");
                    dbAdapter.changeCursor( dbManager.getHistoriesByName(MainActivity.getSelectedProfile().getName()));
                }
            }
        });

        dbAdapter.notifyDataSetChanged();
        listView.setAdapter( this.dbAdapter );

        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listViewHistory){
            getMenuInflater().inflate(R.menu.history_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position= ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position;
        Cursor cursor = this.dbAdapter.getCursor();

        if (item.getItemId()==R.id.showLog) {
            if(cursor.moveToPosition(position)){

                String name= cursor.getString(0);
                String description= cursor.getString(1);

                Intent subActividad = new Intent( HistoryActivity.this, HistoryDescriptionActivity.class );
                subActividad.putExtra( "name", name);
                subActividad.putExtra( "description", description);
                activityResultLauncher.launch(subActividad);
            }else{
                Toast.makeText(this, "Error al intentar leer el log", Toast.LENGTH_SHORT).show();
            }
        }else if(item.getItemId()==R.id.continueGamebyHistory){
            //TODO CONTINUAR PARTIDA SEGUN EL HISTORIAL

        }else{
            return super.onContextItemSelected(item);
        }
        return true;
    }
}