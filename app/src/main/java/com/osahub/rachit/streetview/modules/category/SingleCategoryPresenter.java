package com.osahub.rachit.streetview.modules.category;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.server.NetworkResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 01/05/18
 */

public class SingleCategoryPresenter implements SingleCategoryContract.Presenter {

    private SingleCategoryContract.View mView;
    private DatabaseHelper mDatabaseHelper;
    private NetworkRequest mNetworkRequest;
    private boolean mShowFavourites;
    private int mCategoryId;

    SingleCategoryPresenter(SingleCategoryContract.View view, DatabaseHelper databaseHelper,
                            NetworkRequest networkRequest) {
        mView = view;
        mDatabaseHelper = databaseHelper;
        mNetworkRequest = networkRequest;
    }

    @Override
    public void fetchData(boolean showFavourites, int categoryId) {
        mShowFavourites = showFavourites;
        mCategoryId = categoryId;
        mView.changeViewState(ViewState.LOADING);
        if (showFavourites) {
            fetchLocations(categoryId, "Your Favourites");
        } else {
            if (categoryId != -1) {
                Category localCategory = mDatabaseHelper.mCategoryDbHelper.getCategoryById(categoryId);
                if (localCategory == null) {
                    mNetworkRequest.getCategoryById(categoryId, new NetworkResponse<Category>() {
                        @Override
                        public void onData(Category serverCategory) {
                            fetchLocations(serverCategory.getCategoryId(), serverCategory.getName());
                        }

                        @Override
                        public void onError() {
                            mView.changeViewState(ViewState.ERROR);
                            mView.showError(R.raw.error_message, R.string.could_not_connect);
                        }
                    });
                } else {
                    fetchLocations(categoryId, localCategory.getName());
                }
            } else {
                mView.finishActivity();
            }
        }
    }

    private void fetchLocations(int categoryId, final String categoryName) {
        List<Location> locationList = mShowFavourites ? mDatabaseHelper.mLocationDbHelper.getAllFavouritedLocations() :
                mDatabaseHelper.mLocationDbHelper.getLocationsByCategoryId(categoryId);

        if (!mShowFavourites && (locationList == null || locationList.isEmpty())) {
            mNetworkRequest.getLocationsByCategoryId(categoryId, new NetworkResponse<List<Location>>() {
                @Override
                public void onData(List<Location> serverLocationList) {
                    showData(serverLocationList, categoryName);
                }

                @Override
                public void onError() {
                    mView.changeViewState(ViewState.ERROR);
                    mView.showError(R.raw.error_message, R.string.could_not_connect);
                }
            });
        } else if (mShowFavourites && (locationList == null || locationList.isEmpty())) {
            mView.changeViewState(ViewState.ERROR);
            mView.changeToolbarTitle(categoryName);
            mView.showError(R.raw.empty, R.string.no_favourites_added);
        } else {
            showData(locationList, categoryName);
        }
    }

    private void showData(List<Location> serverLocationList, String categoryName) {
        mView.changeViewState(ViewState.DATA);
        mView.showData(categoryName, serverLocationList);
    }

    @Override
    public ArrayList<Integer> getLocationIds() {
        return mShowFavourites ? mDatabaseHelper.mLocationDbHelper.getAllFavouritedLocationIds() :
                mDatabaseHelper.mLocationDbHelper.getLocationIdsByCategoryId(mCategoryId, -1);
    }
}
