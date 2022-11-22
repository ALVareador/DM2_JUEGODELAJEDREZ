package org.uvigo.dm2_juego_del_ajedrez;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class visualizeProfileActivity extends AppCompatActivity {
    private AchievementArrayAdapter achievementArrayAdapter;
    private ProfileArrayAdapter friendsArrayAdapter;
    private Profile profile;

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
        ivProfile.setImageIcon(profile.getImage());

        //NAME
        TextView tvName= findViewById(R.id.tv_name);
        tvName.setText(profile.getName());

        //POINTS
        TextView tvPoints= findViewById(R.id.points_counter);
        tvPoints.setText(profile.getPoints());

        //LISTVIEW LOGROS
        ListView listView_achievements = findViewById(R.id.listview_visualizeProfile_achievements);
        achievementArrayAdapter = new AchievementArrayAdapter(this, profile.getAchievements());
        listView_achievements.setAdapter(achievementArrayAdapter);

        //LISTVIEW FRIENDS
        ListView listView_friends = findViewById(R.id.listview_visualizeProfile_friends);
        friendsArrayAdapter = new ProfileArrayAdapter(this, profile.getFriends());
        listView_friends.setAdapter(friendsArrayAdapter);
    }

    public void onResume() {
        super.onResume();
        //Recuperamos el perfil actual
        profile= (Profile)getIntent().getSerializableExtra("visualizeprofile");

    }
}
