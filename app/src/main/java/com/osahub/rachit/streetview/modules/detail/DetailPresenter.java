package com.osahub.rachit.streetview.modules.detail;

import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImages;
import com.osahub.rachit.streetview.model.LocationSimilarPlaces;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.server.NetworkResponse;

import java.util.List;

import static com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;

/**
 * Created by Rachit Goyal on 29/04/18
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    private NetworkRequest mNetworkRequest;
    private DatabaseHelper mDatabaseHelper;

    DetailPresenter(DetailContract.View view, NetworkRequest networkRequest, DatabaseHelper databaseHelper) {
        mView = view;
        mNetworkRequest = networkRequest;
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void fetchLocation(int locationId) {
        mView.changeViewState(ViewState.LOADING);
        Location location;
        if (locationId != -1) {
            location = mDatabaseHelper.mLocationDbHelper.getLocationById(locationId);
            mView.setLocation(location);
        } else {
            mNetworkRequest.getLocationById(locationId, new NetworkResponse<Location>() {
                @Override
                public void onData(Location location) {
                    mView.setLocation(location);
                }

                @Override
                public void onError() {
                    mView.changeViewState(ViewState.ERROR);
                }
            });
        }
        fetchDetails(locationId);
    }

    private void fetchDetails(int locationId) {
        mNetworkRequest.getLocationImages(locationId, new NetworkResponse<List<LocationImages>>() {
            @Override
            public void onData(List<LocationImages> images) {
                mView.changeViewState(ViewState.DATA);
                mView.showImages(images);
            }

            @Override
            public void onError() {
                mView.changeViewState(ViewState.ERROR);
            }
        });

        mNetworkRequest.getLocationSimilarPlaces(locationId, new NetworkResponse<List<LocationSimilarPlaces>>() {
            @Override
            public void onData(List<LocationSimilarPlaces> similarPlaces) {
                mView.showSimilarPlaces(similarPlaces);
            }

            @Override
            public void onError() {
                mView.hideSimilarPlaces();
            }
        });
    }

}
