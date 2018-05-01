package com.turkishlegacy.nutritionfactsmobile;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;

public class DatabaseHandler extends SQLiteOpenHelper {
    //Database name
    public static final String DATABASE_NAME = "database.db";
    //table name
    public static final String TABLE_NAME = "Facts";
    //column names
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "QUANTITY";
    public static final String COL_3 = "CALORIES";
    public static final String COL_4 = "PROTEIN";
    public static final String COL_5 = "CARB";
    public static final String COL_6 = "FAT";

    private DatabaseHandler databaseHandler;

    public DatabaseHandler(Context con) {
        //creates the database and the table
        super(con, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execute sql to create columns within a table
        try {
            db.execSQL("create table " + TABLE_NAME + " (NAME TEXT NOT NULL," +
                    "QUANTITY TEXT NOT NULL,CALORIES TEXT NOT NULL,PROTEIN TEXT NOT NULL,CARB TEXT NOT NULL,FAT TEXT NOT NULL);");
        } catch (Exception ex) {
        }

    }

    //will upgrade the table if the database version is lower than what is called in the constructor
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //inserting data
    public boolean addFood(String name, String quantity, String calories, String protein, String carb, String fat) {
        SQLiteDatabase db = this.getWritableDatabase();
        //its like an array, empty values which new data be inserted
        ContentValues contentValues = new ContentValues();
        //adding it to columns
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, quantity);
        contentValues.put(COL_3, calories);
        contentValues.put(COL_4, protein);
        contentValues.put(COL_5, carb);
        contentValues.put(COL_6, fat);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //retrieving the data
    //cursor class is an interface that is used to get data from database using resultset
    public String getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        //get all the columns
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        //query
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        //get the data in columns
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String quantity = cursor.getString(1);
            String calories = cursor.getString(2);
            String protein = cursor.getString(3);
            String carb = cursor.getString(4);
            String fat = cursor.getString(5);
            //output the data which is in a buffer
            buffer.append("Name: " + name + "\n" + "Serving size: " + quantity + "\n" + "Calories: " + calories +
                    "\n" + "Protein: " + protein + "\n" + "Carb: " + carb + "\n" + "Fat: " + fat + "\n\n");

        }
        return buffer.toString();

    }

    public String searchData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        //gonna get calories and quantity only
        String[] columns = {COL_1, COL_2, COL_3};
        //searching for name
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "='" + name + "'", null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            //returning name quantity and calories
            int columnName = cursor.getColumnIndex(COL_1);
            int columnQuantity = cursor.getColumnIndex(COL_2);
            int columnCalories = cursor.getColumnIndex(COL_3);

            String foodName = cursor.getString(columnName);
            String quantity = cursor.getString(columnQuantity);
            String calories = cursor.getString(columnCalories);

            buffer.append("Name: " + foodName + "\n" + "Serving size: " + quantity + "\n" + "Calories: " + calories);

        }
        return buffer.toString();

    }

    public String getName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        //searching for name
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "='" + name + "'", null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String getName = cursor.getString(0);
            return getName;
        }
        return null;

    }

    public String getQuantity(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        //searching for name to find quantity related to it
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "='" + name + "'", null, null, null, null);

        if (cursor != null) {
            //getting quantity from column 2
            cursor.moveToFirst();
            String getQuantity = cursor.getString(1);
            return getQuantity;
        }
        return null;

    }

    public String getCalories(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        //searching for name to find calories related to it
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "='" + name + "'", null, null, null, null);

        if (cursor != null) {
            //getting calories from column 3
            cursor.moveToFirst();
            String getCalories = cursor.getString(2);
            return getCalories;
        }
        return null;

    }

    public String getProtein(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        //searching for name to find calories related to it
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "='" + name + "'", null, null, null, null);

        if (cursor != null) {
            //getting protein from column 4
            cursor.moveToFirst();
            String getProtein = cursor.getString(3);
            return getProtein;
        }
        return null;

    }

    public String getCarb(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        //searching for name to find calories related to it
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "='" + name + "'", null, null, null, null);

        if (cursor != null) {
            //getting carb from column 5
            cursor.moveToFirst();
            String getCarb = cursor.getString(4);
            return getCarb;
        }
        return null;

    }

    public String getFat(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        //searching for name to find calories related to it
        Cursor cursor = db.query(TABLE_NAME, columns, COL_1 + "='" + name + "'", null, null, null, null);

        if (cursor != null) {
            //getting fat from column 6
            cursor.moveToFirst();
            String getFat = cursor.getString(5);
            return getFat;
        }
        return null;

    }

}

