package org.uvigo.dm2_juego_del_ajedrez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private ArrayList<Profile> profiles = new ArrayList<>();
    private ProfileArrayAdapter profileArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //TODO Aqui hay que recuperar los posibles perfiles desde memoria
        profiles.add(new Profile("default"));

        ListView listView = findViewById(R.id.listViewProfile);
        profileArrayAdapter = new ProfileArrayAdapter(this, profiles);
        listView.setAdapter(profileArrayAdapter);

        findViewById(R.id.buttonAddProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfile();
            }
        });
    }

    /** Añade un nuevo perfil*/
    private void addProfile() {
        profiles.add(new Profile());
        showTaskNameDialog(profiles.size()-1);
    }

    /** Añade al nuevo profile un nombre*/
    private void showTaskNameDialog(int position) {
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
            }
        });
        builder.create().show();
    }
}