package com.example.terminet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    //Initialize Variable
    Button btnScan, btnHome, btnSteps, btnSettings;
    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper = new DBHelper(MainActivity.this);
        checkIfIndicatorsExist();


        //Assign Variable
        btnScan = findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(v -> {
            // Initialize intent integrator
            IntentIntegrator intentIntegrator = new IntentIntegrator(
                    MainActivity.this
            );
            //Set Promp text
            intentIntegrator.setPrompt("For flash use volume up key");
            //Set Beep
            intentIntegrator.setBeepEnabled(true);
            //Locked Orientation
            intentIntegrator.setOrientationLocked(true);
            //Set Capture activity
            intentIntegrator.setCaptureActivity(Capture.class);
            //Initiate scan
            intentIntegrator.initiateScan();
        });

        // REDIRECT TO HOME ACTIVITY
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(v -> openHomeActivity());

        // REDIRECT TO INDICATOR ACTIVITY
        btnSteps = findViewById(R.id.btn_steps);
        btnSteps.setOnClickListener(v -> openStepsActivity());

        // REDIRECT TO SETTINGS ACTIVITY
        btnSettings = findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(v -> openSettingsActivity());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Initialize Intent Result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //Check Condition
        if (intentResult.getContents() != null) {
            String scan_results = intentResult.getContents();
            openDashboard(scan_results);
        } else {
            //When result content is null
            //Display toast
            Toast.makeText(getApplicationContext(), "No QR code found, please adjust your camera", Toast.LENGTH_SHORT).show();
        }
    }

    // OPEN ACTIVITIES CHOICES
    public void openHomeActivity(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void openHistoryActivity(){
        Intent i = new Intent(this, HistoryResultsActivity.class);
        startActivity(i);
    }

    public void openSettingsActivity(){
        Intent i = new Intent(this, HistoryResultsActivity.class);
        startActivity(i);
    }

    public void openStepsActivity(){
        Intent i = new Intent(this, StepsActivity.class);
        startActivity(i);

    }

    public void checkIfIndicatorsExist(){
        Cursor cursor = dbhelper.getRingColors();
        if (cursor.getCount() == 0 ) {
            dbhelper.initializeRingColours();
        }
    }

    private void openDashboard(String dashboardUrl){
        Uri uri = Uri.parse(dashboardUrl);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }

}