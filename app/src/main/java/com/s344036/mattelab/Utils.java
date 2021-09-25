package com.s344036.mattelab;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class Utils {
    //Sets app locale for activities
    public static void setAppLocale(AppCompatActivity activity){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
        String selectedLocale = preferences.getString("LANG", "");

        if (selectedLocale != null) {
            // If current locale is not the same as the one saved in preferences, change and recreate
            if(!activity.getResources().getConfiguration().locale.toString().equals(selectedLocale)){
                Resources resources = activity.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                Configuration conf = resources.getConfiguration();
                conf.setLocale(new Locale(selectedLocale));
                resources.updateConfiguration(conf, dm);
                activity.recreate();
            }
        }
    }

    // Sets app locale for fragments
    public static void setFragmentLocale(Fragment fragment, String selectedLocale){
        Resources resources = fragment.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.setLocale(new Locale(selectedLocale));
        resources.updateConfiguration(conf, dm);
    }
}
