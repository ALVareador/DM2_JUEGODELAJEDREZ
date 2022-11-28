package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class visualizeProfileActivity extends AppCompatActivity {
    private AchievementArrayAdapter achievementArrayAdapter;
    private ProfileArrayAdapter friendsArrayAdapter;
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

        //NAME
        TextView tvName= findViewById(R.id.tv_name);
        tvName.setText(profile.getName());

        //POINTS
        TextView tvPoints= findViewById(R.id.points_counter);
        tvPoints.setText(profile.getPoints());

        //LISTVIEW LOGROS
        ListView listView_achievements = findViewById(R.id.listview_visualizeProfile_achievements);
        achievementArrayAdapter = new AchievementArrayAdapter(this, profile.getAchievements(),"INVISIBLE");
        listView_achievements.setAdapter(achievementArrayAdapter);

        //LISTVIEW FRIENDS
        ListView listView_friends = findViewById(R.id.listview_visualizeProfile_friends);
        friendsArrayAdapter = new ProfileArrayAdapter(this, profile.getFriends());
        listView_friends.setAdapter(friendsArrayAdapter);

        //BACK BUTTON
        backButton = this.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualizeProfileActivity.this.setResult( MainActivity.RESULT_CANCELED );
                visualizeProfileActivity.this.finish();
            }
        });
    }

    public void onResume() {
        super.onResume();
        //Recuperamos el perfil actual
        profile= (Profile)getIntent().getSerializableExtra("visualizeprofile");
    }
}