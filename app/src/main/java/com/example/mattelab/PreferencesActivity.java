package com.example.mattelab;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class PreferencesActivity extends AppCompatActivity {
    // Auxiliary variables to prevent trigger firing at time of spinner creation
    private final int spinnerCount = 1;
    private int spinnerSelectionCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);
        colourRoundsButton();

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_prefs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // and create back button (on toolbar)
        ImageView imageView = (ImageView) findViewById(R.id.img_prefs_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Create dropdown (spinner) with language options
        Spinner spinnerLang = (Spinner) findViewById(R.id.dropd_prefs_lang);
        ArrayAdapter<CharSequence> arrayLang = ArrayAdapter.createFromResource(this, R.array.strarr_prefs_langs, android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(arrayLang);
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // If selection count is smaller than the number of spinners, then trigger is being fired without user click
                if (spinnerSelectionCount < spinnerCount) {
                    spinnerSelectionCount++;
                }
                // Set language when user clicks
                else {
                    setLang((String) parent.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        // Create buttons and save number of rounds
        for (int i = 5; i<16; i += 5) {
            int id = getResources().getIdentifier("btn_pref_" + i, "id", getPackageName());
            Button btn =(Button) findViewById(id);
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRounds(finalI);
                }
            });
        }
    }

    public void setLang(String selectedLang){
        // Standard is English
        String countryCode = "en";

        // If select not English...
        if (selectedLang.toLowerCase().contains("norsk")){
            countryCode = "nb";
        } else if (selectedLang.toLowerCase().contains("deutsch")) {
            countryCode = "de";
        }

        //Set newly selected language
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.setLocale(new Locale(countryCode));
        getBaseContext().getResources().updateConfiguration(conf, dm);
        resources.updateConfiguration(conf, dm);

        // Save language preference to persistent memory
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("LANG", countryCode).apply();
        setResult(RESULT_OK);

        // Automatically reload preferences page
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void setRounds(int rounds){
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("ROUNDS", String.valueOf(rounds)).apply();
        setResult(RESULT_OK);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    // Colours button for selected number of rounds (and sets default to 5, if not selected)
    public void colourRoundsButton(){
        MaterialButton btn;

        // If round preference is set, check and select corresponding button
        if (getSharedPreferences("PREFERENCE", MODE_PRIVATE).contains("ROUNDS")){
            String rounds = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("ROUNDS", "");
            switch (rounds) {
                case "10":
                    btn = (MaterialButton) findViewById(R.id.btn_pref_10);
                    break;
                case "15":
                    btn = (MaterialButton) findViewById(R.id.btn_pref_15);
                    break;
                default:
                    btn = (MaterialButton) findViewById(R.id.btn_pref_5);
            }

        }
        // If it's not, set it to default (5) and select button
        else {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("ROUNDS", "5").apply();
            btn = (MaterialButton) findViewById(R.id.btn_pref_5);
        }

        // Fetch theme colour value
        TypedValue tv = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, tv, true);

        // And recolour button for selected value
        btn.setBackgroundColor(tv.data);
    }

}
