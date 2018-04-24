package com.osahub.rachit.streetview.modules.base;

import android.app.IntentService;
import android.content.Context;

import com.osahub.rachit.streetview.database.DatabaseHelper;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public abstract class BaseIntentService extends IntentService {

    public Context mContext;
    public DatabaseHelper mDatabaseHelper;

    public BaseIntentService(String name) {
        super(name);
        mDatabaseHelper = new DatabaseHelper();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
