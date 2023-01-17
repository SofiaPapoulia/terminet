package com.example.terminet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TerminetAR.db";
    private static final int DATABASE_VERSION = 2;

    // MACHINE DATA TABLE
    private static final String TABLE_NAME_1 = "machineData";
    private static final String COLUMN_ID_1 = "machineID";
    private static final String COLUMN_STATUS = "machine_status";
    private static final String COLUMN_DAMAGE = "isDamaged";

    // RING COLOUR PATTERN TABLE WITH SOLUTION STEPS
    private static final String TABLE_NAME_2 = "ringColourPattern";
    private static final String COLUMN_ID_2 = "colourID";
    private static final String COLUMN_COLOR = "color_indicator";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STEPS = "action_steps";
    private static final String COLUMN_TYPE = "isErrorMessage";

    private static final String TABLE_NAME_3 = "applicationSettings";
    private static final String COLUMN_DASHBOARD_URL = "dashboardUrl";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Machine Data
        String createMachineDataTable = "CREATE TABLE " + TABLE_NAME_1 + " (" + COLUMN_ID_1 + " TEXT PRIMARY KEY, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_DAMAGE + " INTEGER);";

        db.execSQL(createMachineDataTable);

        // Solutions Data Table
        String createRingColourPattern = "CREATE TABLE " + TABLE_NAME_2 + " ( " + COLUMN_ID_2 + " INTEGER PRIMARY KEY, " +
                COLUMN_COLOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_STEPS + " TEXT, " +
                COLUMN_TYPE + " INTEGER);";

        db.execSQL(createRingColourPattern);

        String createApplicationUrl = "CREATE TABLE " + TABLE_NAME_3 + " ( " + COLUMN_DASHBOARD_URL + " TEXT);";

        db.execSQL(createApplicationUrl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME_1);
        db.execSQL("drop table if exists " + TABLE_NAME_2);
        db.execSQL("drop table if exists " + TABLE_NAME_3);
        onCreate(db);
    }

    public Boolean insertMachineData(String machineID, String status, Integer isDamaged){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("machineID", machineID);
        contentValues.put("status", status);
        contentValues.put("isDamaged", isDamaged);
        long result = db.insert("machineData", null, contentValues);
        if (result == -1){
            Toast.makeText(context,"Log failed to add to database", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context,"Log added to database", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean updateDashboardUrl(String dashboardUrl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dashboardUrl", dashboardUrl);
        long result = db.update("applicationSettings", contentValues, "dashboardUrl=?", new String[] {dashboardUrl});
        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    // SOLUTIONS DATA TABLE
    public void initializeRingColours(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_2, 1);
        values.put(COLUMN_COLOR, "off");
        values.put(COLUMN_DESCRIPTION, "Device not powered");
        values.put(COLUMN_STEPS, "Check if power supply is correct");
        values.put(COLUMN_TYPE, 1);
        Log.d("tag", "values");
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 2);
        values.put(COLUMN_COLOR, "Spinning yellow");
        values.put(COLUMN_DESCRIPTION, "Loading application and configuration.");
        values.put(COLUMN_STEPS, "No action needed");
        values.put(COLUMN_TYPE, 0);
        Log.d("tag2", "values");
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 3);
        values.put(COLUMN_COLOR, "Fixed yellow");
        values.put(COLUMN_DESCRIPTION, "Application loaded with invalid configuration");
        values.put(COLUMN_STEPS, "Load again the configuration and if necessary, use previous working version");
        values.put(COLUMN_TYPE, 1);
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 4);
        values.put(COLUMN_COLOR, "Blinking green/yellow");
        values.put(COLUMN_DESCRIPTION, "Communication link non-operational");
        values.put(COLUMN_STEPS, "Check that all the network cables are connected correctly and review the network configuration of the routers involved");
        values.put(COLUMN_TYPE, 1);
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 5);
        values.put(COLUMN_COLOR, "Blinking green");
        values.put(COLUMN_DESCRIPTION, "Operating in standby mode");
        values.put(COLUMN_STEPS, "No action needed");
        values.put(COLUMN_TYPE, 0);
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 6);
        values.put(COLUMN_COLOR, "Fixed green");
        values.put(COLUMN_DESCRIPTION, "Operating in HOT mode");
        values.put(COLUMN_STEPS, "No action needed");
        values.put(COLUMN_TYPE, 0);
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 7);
        values.put(COLUMN_COLOR, "Blinking blue");
        values.put(COLUMN_DESCRIPTION, "Operating in local mode (standby)");
        values.put(COLUMN_STEPS, "No action needed");
        values.put(COLUMN_TYPE, 0);
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 8);
        values.put(COLUMN_COLOR, "Fixed blue");
        values.put(COLUMN_DESCRIPTION, "Operating in local mode (HOT)");
        values.put(COLUMN_STEPS, "No action needed");
        values.put(COLUMN_TYPE, 0);
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 9);
        values.put(COLUMN_COLOR, "Fixed red");
        values.put(COLUMN_DESCRIPTION, "Hardware or application non-operational");
        values.put(COLUMN_STEPS, "Review the physical integrity of the RTU and reupload the configuration. Replace the device if the issue persists");
        values.put(COLUMN_TYPE, 1);
        db.insert(TABLE_NAME_2, null, values);

        values.put(COLUMN_ID_2, 10);
        values.put(COLUMN_COLOR, "Red arc");
        values.put(COLUMN_DESCRIPTION, "Device powered but not running");
        values.put(COLUMN_STEPS, "Load a desired configuration.");
        values.put(COLUMN_TYPE, 1);
        db.insert(TABLE_NAME_2, null, values);

        db.close();
    }

    // MACHINE DATA
    public Boolean updateMachineData(String machineID, String status, Integer isDamaged){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        contentValues.put("isDamaged", isDamaged);
        Cursor cursor = db.rawQuery("Select * from machineData where machineID=?", new String[] {machineID});
        if (cursor.getCount()>0){
            long result = db.update("machineData", contentValues, "machineID=?", new String[] {machineID});
            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteMachineData(String machineID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from machineData where machineID=?", new String[] {machineID});
        if (cursor.getCount()>0){
            long result = db.delete("machineData", "machineID=?", new String[] {machineID});
            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    // RETURNS ALL MALFUNCTIONED DEVICES
    public Cursor getMachineData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_1 ;

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getRingColors(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_2 ;

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getRingColorSolutionByID(String indicatorID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_2 + " WHERE colourID =?";
        Cursor cursor = null;
        if (db != null) {
            cursor=db.rawQuery(query,new String[] {String.valueOf(indicatorID)});
        }
        return cursor;
    }

}
