package com.osahub.rachit.streetview.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.osahub.rachit.streetview.database.DataContract.CategoriesEntry;
import com.osahub.rachit.streetview.database.DataContract.CategoryLocationsEntry;
import com.osahub.rachit.streetview.database.DataContract.LocationsEntry;

/**
 * Database Helper Class to create and update Database, and to create tables.
 * Created by Rachit on 30-May-16.
 */
public class DataDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "World Tour 3D: " + DataDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "world_tour.db";

    final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CategoriesEntry.TABLE_NAME + " (" +
            CategoriesEntry._ID + " INTEGER PRIMARY KEY, " +
            CategoriesEntry.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
            CategoriesEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            CategoriesEntry.COLUMN_TYPE + " TEXT, " +
            CategoriesEntry.COLUMN_ORDER + " INTEGER, " +
            CategoriesEntry.COLUMN_CREATED_ON + " TEXT, " +
            CategoriesEntry.COLUMN_UPDATED_ON + " TEXT" +
            " );";

    final String SQL_CREATE_LOCATIONS_TABLE = "CREATE TABLE " + LocationsEntry.TABLE_NAME + " (" +
            LocationsEntry._ID + " INTEGER PRIMARY KEY, " +
            LocationsEntry.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
            LocationsEntry.COLUMN_LOCATION_NAME + " TEXT NOT NULL, " +
            LocationsEntry.COLUMN_DESCRIPTION + " TEXT, " +
            LocationsEntry.COLUMN_DESCRIPTION_SMALL + " TEXT, " +
            LocationsEntry.COLUMN_CITY + " TEXT, " +
            LocationsEntry.COLUMN_STATE + " TEXT, " +
            LocationsEntry.COLUMN_COUNTRY + " TEXT, " +
            LocationsEntry.COLUMN_LATITUDE + " FLOAT, " +
            LocationsEntry.COLUMN_LONGITUDE + " FLOAT, " +
            LocationsEntry.COLUMN_IMAGE_PATH + " TEXT, " +
            LocationsEntry.COLUMN_THUMBNAIL_PATH + " TEXT, " +
            LocationsEntry.COLUMN_CREATED_ON + " TEXT, " +
            LocationsEntry.COLUMN_UPDATED_ON + " TEXT" +
            " );";

    final String SQL_CREATE_CATEGORY_LOCATIONS_TABLE = "CREATE TABLE " + CategoryLocationsEntry.TABLE_NAME + " (" +
            CategoryLocationsEntry._ID + " INTEGER PRIMARY KEY, " +
            CategoryLocationsEntry.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
            CategoryLocationsEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
            CategoryLocationsEntry.COLUMN_LOCATION_ID + " INTEGER NOT NULL, " +
            CategoryLocationsEntry.COLUMN_ORDER + " INTEGER, " +
            CategoryLocationsEntry.COLUMN_CREATED_ON + " TEXT, " +
            CategoryLocationsEntry.COLUMN_UPDATED_ON + " TEXT" +
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CategoriesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CategoryLocationsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
