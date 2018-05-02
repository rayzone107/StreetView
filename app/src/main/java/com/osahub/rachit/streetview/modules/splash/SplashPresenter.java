package com.osahub.rachit.streetview.modules.splash;

import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;

/**
 * Created by Rachit Goyal on 02/05/18
 */

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;
    private DatabaseHelper mDatabaseHelper;

    SplashPresenter(SplashContract.View view, DatabaseHelper databaseHelper) {
        mView = view;
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void onCreate() {
        if (mDatabaseHelper.mCategoryDbHelper.getCategoryCount() != 0 &&
                mDatabaseHelper.mLocationDbHelper.getLocationCount() != 0 &&
                mDatabaseHelper.mCategoryLocationDatabaseHelper.getCategoryLocationCount() != 0) {
            mView.startLocationActivity();
        } else {
            mView.changeViewState(ViewState.LOADING);
        }
    }
}
