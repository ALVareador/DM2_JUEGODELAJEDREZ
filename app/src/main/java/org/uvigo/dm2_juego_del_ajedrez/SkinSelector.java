package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class SkinSelector extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_selector);

        Button boardSkins= (Button)findViewById(R.id.boardSkinSelection);
        Button pieceSkins= (Button)findViewById(R.id.pieceSkinSelection);

        boardSkins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subActividad = new Intent( SkinSelector.this, SkinsActivity.class );
                subActividad.putExtra("mode",true); //Enviamos el modo de juego
                activityResultLauncher.launch(subActividad);
            }
        });

        pieceSkins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subActividad = new Intent( SkinSelector.this, SkinsActivity.class );
                subActividad.putExtra("mode",false); //Enviamos el modo de juego
                activityResultLauncher.launch(subActividad);
            }
        });

        ActivityResultContract<Intent, ActivityResult> contract = new ActivityResultContracts.StartActivityForResult();
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        };

        ImageButton backButton= (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinSelector.this.setResult( MainActivity.RESULT_CANCELED );
                SkinSelector.this.finish();
            }
        });

        this.activityResultLauncher = this.registerForActivityResult(contract, callback);
    }

}
