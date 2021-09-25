package com.s344036.mattelab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;

public class StatsActivity extends AppCompatActivity implements EraseDialog.DialogClickListener {

    int gamesPlayed = 0;
    int rightOperations = 0;
    int wrongOperations = 0;
    int gamesWithOver50Percent = 0;
    int gamesWithUnder50Percent = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_stats);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MaterialButton eraseStats = (MaterialButton) findViewById(R.id.btn_stats_erase);
        eraseStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment eraseDialog = new EraseDialog();
                eraseDialog.show(getSupportFragmentManager(), "Erase");
            }
        });

        getStats();
    }

    public void getStats(){
        gamesPlayed = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("PLAYED", 0 );
        gamesWithOver50Percent = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("WON", 0);
        gamesWithUnder50Percent = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("LOST", 0);
        rightOperations = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("RIGHTOP", 0);
        wrongOperations = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("WRONGOP",0);

        setStats();
    }

    public void setStats(){
        setText(R.id.txt_stats_games, gamesPlayed);
        setText(R.id.txt_stats_won, gamesWithOver50Percent);
        setText(R.id.txt_stats_lost, gamesWithUnder50Percent);
        setText(R.id.txt_stats_right, rightOperations);
        setText(R.id.txt_stats_wrong, wrongOperations);
    }

    private void setText(int id, int value){
        TextView txt = (TextView) findViewById(id);
        txt.setText(String.valueOf(value));
    }

    public void clearStats(){
        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("PLAYED", 0 ).apply();
        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("WON", 0).apply();
        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("LOST", 0).apply();
        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("RIGHTOP", 0).apply();
        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("WRONGOP",0).apply();
    }

    @Override
    public void onYesClick() {
        clearStats();
        getStats();
    }

    @Override
    public void onNoClick() {
    }
}
