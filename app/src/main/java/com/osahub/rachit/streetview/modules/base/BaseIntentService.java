package com.osahub.rachit.streetview.modules.base;

import android.app.IntentService;
import android.content.Context;

import com.osahub.rachit.streetview.database.CategoryDatabaseHelper;
import com.osahub.rachit.streetview.database.CategoryLocationDatabaseHelper;
import com.osahub.rachit.streetview.database.DatabaseContract;
import com.osahub.rachit.streetview.database.LocationDatabaseHelper;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public abstract class BaseIntentService extends IntentService {

    public Context mContext;
    public DatabaseContract.CategoryContract mCategoryDatabaseHelper;
    public DatabaseContract.LocationContract mLocationDatabaseHelper;
    public DatabaseContract.CategoryLocationContract mCategoryLocationDatabaseHelper;

    public BaseIntentService(String name) {
        super(name);
        mContext = getApplicationContext();
        mCategoryDatabaseHelper = new CategoryDatabaseHelper();
        mLocationDatabaseHelper = new LocationDatabaseHelper();
        mCategoryLocationDatabaseHelper = new CategoryLocationDatabaseHelper();
    }
}
