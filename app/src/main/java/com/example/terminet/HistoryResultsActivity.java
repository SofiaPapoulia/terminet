package com.example.terminet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HistoryResultsActivity extends AppCompatActivity {

    Button btnBack;
    DBHelper dbhelper;
    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_results);
        // get history data
        dbhelper = new DBHelper(HistoryResultsActivity.this);
        Cursor res = dbhelper.getMachineData();
        if (res.getCount() == 0) {
            tvResults.setText("All set!");
        } else {}

        // REDIRECT TO HOME ACTIVITY
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMainMenuActivity();
            }
        });

    }

    public void backMainMenuActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}