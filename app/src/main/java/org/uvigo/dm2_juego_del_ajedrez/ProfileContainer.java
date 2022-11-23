package org.uvigo.dm2_juego_del_ajedrez;


import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileContainer extends Application {
    Profile selectedProfile;

    public ProfileContainer(){
        this.selectedProfile=new Profile();
    }

    public Profile getSelectedProfile(){
        return selectedProfile;
    }

    public void setSelectedProfile(Profile profile){
        this.selectedProfile=profile;
    }


}
