package com.osahub.rachit.streetview.modules.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.osahub.rachit.streetview.database.CategoryDatabaseHelper;
import com.osahub.rachit.streetview.database.CategoryLocationDatabaseHelper;
import com.osahub.rachit.streetview.database.DatabaseContract;
import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.database.LocationDatabaseHelper;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public DatabaseHelper mDatabaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            mContext = getActivity();
        }
        mDatabaseHelper = new DatabaseHelper();
    }
}
