package com.osahub.rachit.streetview.modules.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.osahub.rachit.streetview.database.DatabaseHelper;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public abstract class BaseActivity extends AppCompatActivity {

    public enum ViewState {
        LOADING,
        DATA,
        ERROR
    }

    public Context mContext;
    public DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mDatabaseHelper = new DatabaseHelper();
    }
}
