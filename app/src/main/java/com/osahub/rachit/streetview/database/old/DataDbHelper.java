package com.osahub.rachit.streetview.database.old;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database Helper Class to create and update Database, and to create tables.
 * Created by Rachit on 30-May-16.
 */
public class DataDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "World Tour 3D: " + DataDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "world_tour.db";

    final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " + DataContract.CategoriesEntry.TABLE_NAME + " (" +
            DataContract.CategoriesEntry._ID + " INTEGER PRIMARY KEY, " +
            DataContract.CategoriesEntry.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
            DataContract.CategoriesEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            DataContract.CategoriesEntry.COLUMN_TYPE + " TEXT, " +
            DataContract.CategoriesEntry.COLUMN_ORDER + " INTEGER, " +
            DataContract.CategoriesEntry.COLUMN_CREATED_ON + " TEXT, " +
            DataContract.CategoriesEntry.COLUMN_UPDATED_ON + " TEXT" +
            " );";

    final String SQL_CREATE_LOCATIONS_TABLE = "CREATE TABLE " + DataContract.LocationsEntry.TABLE_NAME + " (" +
            DataContract.LocationsEntry._ID + " INTEGER PRIMARY KEY, " +
            DataContract.LocationsEntry.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
            DataContract.LocationsEntry.COLUMN_LOCATION_NAME + " TEXT NOT NULL, " +
            DataContract.LocationsEntry.COLUMN_DESCRIPTION + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_DESCRIPTION_SMALL + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_CITY + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_STATE + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_COUNTRY + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_LATITUDE + " FLOAT, " +
            DataContract.LocationsEntry.COLUMN_LONGITUDE + " FLOAT, " +
            DataContract.LocationsEntry.COLUMN_IMAGE_PATH + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_THUMBNAIL_PATH + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_CREATED_ON + " TEXT, " +
            DataContract.LocationsEntry.COLUMN_UPDATED_ON + " TEXT" +
            " );";

    final String SQL_CREATE_CATEGORY_LOCATIONS_TABLE = "CREATE TABLE " + DataContract.CategoryLocationsEntry.TABLE_NAME + " (" +
            DataContract.CategoryLocationsEntry._ID + " INTEGER PRIMARY KEY, " +
            DataContract.CategoryLocationsEntry.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
            DataContract.CategoryLocationsEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
            DataContract.CategoryLocationsEntry.COLUMN_LOCATION_ID + " INTEGER NOT NULL, " +
            DataContract.CategoryLocationsEntry.COLUMN_ORDER + " INTEGER, " +
            DataContract.CategoryLocationsEntry.COLUMN_CREATED_ON + " TEXT, " +
            DataContract.CategoryLocationsEntry.COLUMN_UPDATED_ON + " TEXT" +
            " );";

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATIONS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_LOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.CategoriesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.LocationsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.CategoryLocationsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
