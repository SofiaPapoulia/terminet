package com.example.terminet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Solution extends AppCompatActivity {

    String color_indicator,description, action_steps;
    Integer isErrorMessage;
    DBHelper dbhelper;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        dbhelper = new DBHelper(Solution.this);
        String indicatorID = getIntent().getExtras().getString("indicatorID");
        getIndicatorDetailsByID(indicatorID);

        // REDIRECT TO HOME ACTIVITY
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> backIndicatorListActivity());
    }

    public void getIndicatorDetailsByID(String indicatorID){
        Cursor cursor = dbhelper.getRingColorSolutionByID(indicatorID);
        Button main_menu_btn = findViewById(R.id.btn_main_menu);
        TextView solution_txt = findViewById(R.id.solution_txt);
        if (cursor.getCount() == 0 ) {
            solution_txt.setText("No Ring Color Data are in database, so no solution can be displayed.");
        } else {
            while (cursor.moveToNext()) {
                action_steps = cursor.getString(3);
                isErrorMessage = cursor.getInt(4);

                if (isErrorMessage == 0) {
                    //Then user doesn't need to do anything
                    solution_txt.setText(action_steps);
                    main_menu_btn.setText("Back to Main Menu");
                    main_menu_btn.setOnClickListener(v -> {
                        backMainActivity();
                    });
                }
                else {
                    //Show Action and remote eye button
                    main_menu_btn.setText("Open Remote Eye");
                    main_menu_btn.setOnClickListener(v -> {
                        // OPEN REMOTE EYE APPLICATION
                        Intent launchRemoteEye = getPackageManager().getLaunchIntentForPackage("com.wideum.remoteeye");
                        // CHECK IF REMOTE EYE APPLICATION EXISTS IN THE DEVICE
                        if (launchRemoteEye !=null){
                            startActivity(launchRemoteEye);
                        }else {
                            Toast.makeText(Solution.this,"You need to install the Remote Eye Application in order to proceed", Toast.LENGTH_LONG).show();
                            try{
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=" + "com.wideum.remoteeye")));
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                            }
                        }
                    });
                    solution_txt.setText(action_steps);
                }
            }
        }
    }

    public void backMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void backIndicatorListActivity(){
        Intent i = new Intent(this, StepsActivity.class);
        startActivity(i);
    }
}

