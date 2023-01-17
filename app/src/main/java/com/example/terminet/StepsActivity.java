package com.example.terminet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.example.terminet.databinding.ActivityStepsBinding;
import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tv;
    DBHelper dbhelper;
    ArrayList<String> color_indicator,description, action_steps;
    ArrayList<Integer> color_id, isErrorMessage;
    CustomAdapter customAdapter;

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        recyclerView = findViewById(R.id.recyclerView);
        tv = findViewById(R.id.tv_color_data);
        dbhelper = new DBHelper(StepsActivity.this);

        // REDIRECT TO HOME ACTIVITY
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> backMainMenuActivity());

        //Initialize parameters list
        color_id = new ArrayList<>();
        color_indicator = new ArrayList<>();
        description = new ArrayList<>();
        action_steps = new ArrayList<>();
        isErrorMessage = new ArrayList<>();

        // Store Data in the RecyclerView
        storeDataInArrayList();
        customAdapter = new CustomAdapter(StepsActivity.this, color_id, color_indicator, description);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StepsActivity.this));
    }

    void storeDataInArrayList(){
        Cursor cursor = dbhelper.getRingColors();
        if (cursor.getCount() == 0 ) {
            Toast.makeText(this, "No Ring Color Data are displayed", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                color_id.add(cursor.getInt(0));
                color_indicator.add(cursor.getString(1));
                description.add(cursor.getString(2));
                action_steps.add(cursor.getString(3));
                isErrorMessage.add(cursor.getInt(4));
            }
        }
    }

    public void backMainMenuActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}