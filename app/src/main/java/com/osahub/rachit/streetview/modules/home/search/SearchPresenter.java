package com.osahub.rachit.streetview.modules.home.search;

import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.server.NetworkResponse;

import java.util.List;

/**
 * Created by Rachit Goyal on 30/04/18
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;
    private DatabaseHelper mDatabaseHelper;
    private NetworkRequest mNetworkRequest;

    SearchPresenter(SearchContract.View view, DatabaseHelper databaseHelper, NetworkRequest networkRequest) {
        mView = view;
        mDatabaseHelper = databaseHelper;
        mNetworkRequest = networkRequest;
    }

    @Override
    public void searchStringChange(String newText) {
        mView.changeViewState(ViewState.LOADING);
        if (newText.isEmpty()) {
            mView.changeViewState(ViewState.LOADING);
        } else {
            List<Category> searchedCategoryList = mDatabaseHelper.mCategoryDbHelper.getAllCategoriesThatContainString(newText);
            List<Location> searchedLocationList = mDatabaseHelper.mLocationDbHelper.getAllLocationsThatContainString(newText);
            if (searchedCategoryList.isEmpty() && searchedLocationList.isEmpty()) {
                mView.changeViewState(ViewState.ERROR);
            } else {
                mView.changeViewState(ViewState.DATA);
                mView.updateSearchedList(searchedCategoryList, searchedLocationList);
            }
        }
    }

    @Override
    public void onRequestLocationClicked(String searchString) {
        mNetworkRequest.addRequestedLocation(searchString, new NetworkResponse<Void>() {
            @Override
            public void onData(Void object) {
                mView.showRequestResponse(true);
            }

            @Override
            public void onError() {
                mView.showRequestResponse(false);
            }
        });
    }
}
