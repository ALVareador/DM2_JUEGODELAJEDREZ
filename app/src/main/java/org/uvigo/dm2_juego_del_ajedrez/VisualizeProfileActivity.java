package org.uvigo.dm2_juego_del_ajedrez;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VisualizeProfileActivity extends AppCompatActivity {
    private ArrayAdapter frArrayAdapter, achArrayAdapter;
    private Profile profile;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizeprofile);

        //Recuperamos el perfil actual
        profile= (Profile)getIntent().getSerializableExtra("visualizeprofile");
        Log.println(Log.WARN,"WARN",profile.toString());

        Log.println(Log.WARN,"","He recuperado "+profile.toString());

        //PHOTO
        ImageView ivProfile= findViewById(R.id.iv_profileimage);
        ivProfile.setImageBitmap(Uploader.bitmapFromAssets(getApplicationContext(),profile.getImagePath()));

        //NAME
        TextView tvName= findViewById(R.id.tv_name);
        tvName.setText(profile.getName());

        //POINTS
        TextView tvPoints= findViewById(R.id.points_counter);
        tvPoints.setText(profile.getPoints());

        //LISTVIEW LOGROS
        ListView listView_achievements = findViewById(R.id.listview_visualizeProfile_achievements);
        achArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_selectable_list_item, profile.getAchievements() );
        listView_achievements.setAdapter(achArrayAdapter);

        //LISTVIEW FRIENDS
        ListView listView_friends = findViewById(R.id.listview_visualizeProfile_friends);
        frArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, profile.getFriends());
        listView_friends.setAdapter(frArrayAdapter);

        //BACK BUTTON
        backButton = this.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisualizeProfileActivity.this.setResult( MainActivity.RESULT_CANCELED );
                VisualizeProfileActivity.this.finish();
            }
        });
    }

    public void onResume() {
        super.onResume();
        //Recuperamos el perfil actual
        profile= (Profile)getIntent().getSerializableExtra("visualizeprofile");
    }
}