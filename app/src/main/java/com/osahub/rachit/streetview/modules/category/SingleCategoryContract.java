package com.osahub.rachit.streetview.modules.category;

import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 01/05/18
 */

public interface SingleCategoryContract {

    interface View {

        void changeViewState(BaseActivity.ViewState viewState);

        void finishActivity();

        void showData(String categoryName, List<Location> locationList);

        void showError(@RawRes int rawRes, @StringRes int messageRes);

        void changeToolbarTitle(String title);
    }

    interface Presenter {

        void fetchData(boolean showFavourites, int categoryId);

        ArrayList<Integer> getLocationIds();
    }
}
