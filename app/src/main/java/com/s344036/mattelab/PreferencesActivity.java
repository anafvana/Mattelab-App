package com.s344036.mattelab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class PreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_prefs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportFragmentManager().beginTransaction().replace(R.id.settings_container, new Preferences()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAppLocale();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void setAppLocale() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String selectedLocale = preferences.getString("LANG", "");

        if(!getResources().getConfiguration().locale.toString().equals(selectedLocale)){
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.setLocale(new Locale(selectedLocale));
            resources.updateConfiguration(conf, dm);
            Intent i = new Intent(this, PreferencesActivity.class);
            finish();
            startActivity(i);
        }
    }
}
