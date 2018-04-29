package com.osahub.rachit.streetview.modules.detail;

import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImages;
import com.osahub.rachit.streetview.model.LocationSimilarPlaces;
import com.osahub.rachit.streetview.modules.base.BaseActivity;

import java.util.List;

/**
 * Created by Rachit Goyal on 29/04/18
 */

public interface DetailContract {
    interface View {

        void changeViewState(BaseActivity.ViewState viewState);

        void setLocation(Location location);

        void showImages(List<LocationImages> locationImages);

        void showSimilarPlaces(List<LocationSimilarPlaces> similarPlaces);

        void hideSimilarPlaces();
    }

    interface Presenter {

        void fetchLocation(int locationId);
    }
}
