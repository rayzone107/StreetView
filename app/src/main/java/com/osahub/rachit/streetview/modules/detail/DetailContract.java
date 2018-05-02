package com.osahub.rachit.streetview.modules.detail;

import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImage;
import com.osahub.rachit.streetview.modules.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 29/04/18
 */

public interface DetailContract {
    interface View {

        void changeViewState(BaseActivity.ViewState viewState);

        void setLocation(Location location);

        void showImages(List<LocationImage> locationImages);

        void showSimilarPlaces(ArrayList<Integer> similarPlaces);

        void hideSimilarPlaces();

        void finishActivity();

        void showThumbnailInImages();
    }

    interface Presenter {

        void fetchLocation(int locationId);
    }
}
