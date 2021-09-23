package com.example.mattelab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialises app and sets correct language
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAppLocale();

        // Sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setActionBar(toolbar);

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
                newActivityForResult(PreferencesActivity.class, 555);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 555){
            if(resultCode==RESULT_OK) {
                recreate();
            }
        }
    }

    public void newActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void newActivityForResult(Class c, int requestCode){
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, requestCode);
    }

    // Sets appLocale as the one previously chosen by the user
    public void setAppLocale() {
        if (getSharedPreferences("PREFERENCE", MODE_PRIVATE).contains("LANG")) {
            String countryCode = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("LANG", "");
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();

            if (!conf.locale.toString().equals(countryCode)) {
                conf.setLocale(new Locale(countryCode));
                getBaseContext().getResources().updateConfiguration(conf, dm);
                resources.updateConfiguration(conf, dm);
                recreate();
            }
        }
    }
}