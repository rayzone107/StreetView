package com.osahub.rachit.streetview.modules.home;

import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.server.NetworkResponse;

import java.util.List;

/**
 * Created by Rachit Goyal on 26/04/18
 */

public class LocationsPresenter implements LocationsContract.Presenter {

    private LocationsContract.View mView;
    private DatabaseHelper mDatabaseHelper;
    private NetworkRequest mNetworkRequest;

    LocationsPresenter(LocationsContract.View view, DatabaseHelper databaseHelper, NetworkRequest networkRequest) {
        mView = view;
        mDatabaseHelper = databaseHelper;
        mNetworkRequest = networkRequest;
    }

    @Override
    public void onResume() {
        mView.changeViewState(ViewState.LOADING);
        final List<Category> categories = mDatabaseHelper.mCategoryDbHelper.getAllCategories();

        if (categories.isEmpty()) {
            mNetworkRequest.getAllCategories(new NetworkResponse<List<Category>>() {
                @Override
                public void onData(List<Category> categoriesList) {
                    fetchLocations(categoriesList);
                }

                @Override
                public void onError() {
                    mView.changeViewState(ViewState.ERROR);
                }
            });
        } else {
            fetchLocations(categories);
        }
    }

    private void fetchLocations(final List<Category> categories) {
        if (mDatabaseHelper.mLocationDbHelper.getLocationCount() == 0) {
            mNetworkRequest.getAllLocations(new NetworkResponse<List<Location>>() {
                @Override
                public void onData(List<Location> locations) {
                    mView.changeViewState(ViewState.DATA);
                    mView.showData(categories);
                }

                @Override
                public void onError() {
                    mView.changeViewState(ViewState.ERROR);
                }
            });
        } else {
            mView.changeViewState(ViewState.DATA);
            mView.showData(categories);
        }
    }
}
