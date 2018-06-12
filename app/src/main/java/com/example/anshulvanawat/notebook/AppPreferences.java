package com.example.anshulvanawat.notebook;

import android.app.FragmentTransaction;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        android.app.FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        SettingFragment settingFragment=new SettingFragment();
        //R.id.content get you the root layout, works sames as R.id.note_container "id of root layout"
        fragmentTransaction.add(android.R.id.content,settingFragment,"SETTING_FRAGMENT");
        fragmentTransaction.commit();



    }

    public static class SettingFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.app_preferences);

        }
    }
}
