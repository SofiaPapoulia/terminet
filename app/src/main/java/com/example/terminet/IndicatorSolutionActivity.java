package com.example.terminet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class IndicatorSolutionActivity extends AppCompatActivity {
    String color_indicator,description, action_steps;
    Integer isErrorMessage;
    DBHelper dbhelper;
    Button btnBack, btnSolution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_solution);
        dbhelper = new DBHelper(IndicatorSolutionActivity.this);
        String indicatorID = getIntent().getExtras().getString("indicatorID");

        // REDIRECT TO HOME ACTIVITY
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> backIndicatorListActivity());
        getIndicatorDetailsByID(indicatorID);

        btnSolution = findViewById(R.id.btn_solution);
        btnSolution.setOnClickListener(v -> {
            // Start the new activity here and pass any necessary information as extras
            Intent intent = new Intent(v.getContext(), Solution.class);
            intent.putExtra("indicatorID", String.valueOf(indicatorID));
            v.getContext().startActivity(intent);
        });

    }

    public void getIndicatorDetailsByID(String indicatorID){
        Cursor cursor = dbhelper.getRingColorSolutionByID(indicatorID);
        TextView intv = findViewById(R.id.indicator_name_txt);
        TextView idtv = findViewById(R.id.indicator_description_txt);
        if (cursor.getCount() == 0 ) {
            idtv.setText("No Ring Color Data are displayed");
        } else {
            while (cursor.moveToNext()) {
                color_indicator = cursor.getString(1);
                description = cursor.getString(2);
                action_steps = cursor.getString(3);
                isErrorMessage = cursor.getInt(4);

                intv.setText("Indicator: " + color_indicator);
                idtv.setText(description);
            }
        }
    }

    public void backIndicatorListActivity(){
        Intent i = new Intent(this, StepsActivity.class);
        startActivity(i);
    }

}