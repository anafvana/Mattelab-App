package com.s344036.mattelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    /*------- OVERRIDES -------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialises app and sets correct language
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.setAppLocale(this);

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

        // Sets initial values for statistics, in case they do not exist
        initStats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setAppLocale(this);
    }

    /*------- AUXILIARY METHODS -------*/

    // Initialises statistics, if do not exist
    private void initStats(){
        SharedPreferences stats = getSharedPreferences("STATISTICS", MODE_PRIVATE);
        if (!((stats.contains("PLAYED") && stats.contains("WON") && stats.contains("LOST") && stats.contains("RIGHTOP") && stats.contains("WRONGOP")))) {
            stats.edit().putInt("PLAYED", 0 ).apply();
            stats.edit().putInt("WON", 0).apply();
            stats.edit().putInt("LOST", 0).apply();
            stats.edit().putInt("RIGHTOP", 0).apply();
            stats.edit().putInt("WRONGOP",0).apply();
        }
    }

    // Auxiliary method for DRY: creation of new activity
    private void newActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}