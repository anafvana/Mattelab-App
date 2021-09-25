package com.example.mattelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;


import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialises app and sets correct language
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAppLocale();

        // Sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // Play button
        Button btn_play = findViewById(R.id.btn_main_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity(PlayActivity.class);
            }
        });

        // Statistics button
        Button btn_stats = findViewById(R.id.btn_main_stats);
        btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity(StatsActivity.class);
            }
        });

        // Preferences button
        Button btn_pref = findViewById(R.id.btn_main_prefs);
        btn_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity(PreferencesActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAppLocale();
    }

    public void newActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    // Sets appLocale as the one previously chosen by the user
    public void setAppLocale() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String selectedLocale = preferences.getString("LANG", "");

        if (selectedLocale != null) {
            // If current locale is not the same as the one saved in preferences, change and recreate
            if(!getResources().getConfiguration().locale.toString().equals(selectedLocale)){
                Resources resources = getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                Configuration conf = resources.getConfiguration();
                conf.setLocale(new Locale(selectedLocale));
                resources.updateConfiguration(conf, dm);
                recreate();
            }
        }
    }
}