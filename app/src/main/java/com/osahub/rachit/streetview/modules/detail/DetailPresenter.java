package com.osahub.rachit.streetview.modules.detail;

import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImage;
import com.osahub.rachit.streetview.model.LocationSimilarPlace;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.server.NetworkResponse;

import java.util.ArrayList;
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
            if (location == null) {
                mNetworkRequest.getLocationById(locationId, new NetworkResponse<Location>() {
                    @Override
                    public void onData(Location location) {
                        mView.setLocation(location);
                        fetchDetails(location.getLocationId());
                    }

                    @Override
                    public void onError() {
                        mView.changeViewState(ViewState.ERROR);
                    }
                });
            } else {
                mView.setLocation(location);
                fetchDetails(locationId);
            }
        } else {
            mView.finishActivity();
        }
    }

    private void fetchDetails(int locationId) {
        mNetworkRequest.getLocationImages(locationId, new NetworkResponse<List<LocationImage>>() {
            @Override
            public void onData(List<LocationImage> images) {
                mView.changeViewState(ViewState.DATA);
                if (images.isEmpty()) {
                    mView.showThumbnailInImages();
                } else {
                    mView.showImages(images);
                }
            }

            @Override
            public void onError() {
                mView.changeViewState(ViewState.ERROR);
            }
        });

        mNetworkRequest.getLocationSimilarPlaces(locationId, new NetworkResponse<List<LocationSimilarPlace>>() {
            @Override
            public void onData(List<LocationSimilarPlace> similarPlaces) {
                ArrayList<Integer> similarPlacesIds = new ArrayList<>();
                for (LocationSimilarPlace locationSimilarPlace : similarPlaces) {
                    similarPlacesIds.add(locationSimilarPlace.getSimilarLocationId());
                }
                mView.showSimilarPlaces(similarPlacesIds);
            }

            @Override
            public void onError() {
                mView.hideSimilarPlaces();
            }
        });
    }

}
