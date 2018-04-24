package com.osahub.rachit.streetview.database;

import com.osahub.rachit.streetview.database.old.DataContract;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class DatabaseHelper implements DatabaseContract {

    public DatabaseContract.CategoryContract mCategoryDbHelper;
    public DatabaseContract.LocationContract mLocationDbHelper;
    public DatabaseContract.CategoryLocationContract mCategoryLocationDatabaseHelper;

    public DatabaseHelper() {
        mCategoryDbHelper = new CategoryDatabaseHelper();
        mLocationDbHelper = new LocationDatabaseHelper();
        mCategoryLocationDatabaseHelper = new CategoryLocationDatabaseHelper();
    }
}
