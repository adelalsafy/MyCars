package com.example.adelalsay.mycars.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {
    // All Static variables
    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    public static final String DB_NAME = "cars.db";

    // Car table name
    public static final String CAR_TB_NAME= "car";

    // user Table Columns names
    public static final String CAR_CLN_ID = "id";
    public static final String CAR_CLN_MODEL = "model";
    public static final String CAR_CLN_COLOR = "color";
    public static final String CAR_CLN_DESCRAPTION = "description";
    public static final String CAR_CLN_IMAGE = "image";
    public static final String CAR_CLN_DPL = "distancePerLetter" ;
    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



}


