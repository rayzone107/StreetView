package com.osahub.rachit.streetview.modules.home.search;

import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity;

import java.util.List;

/**
 * Created by Rachit Goyal on 30/04/18
 */

public interface SearchContract {

    interface View {

        void changeViewState(BaseActivity.ViewState viewState);

        void updateSearchedList(List<Category> searchedCategoryList, List<Location> searchedLocationList);

        void showRequestResponse(boolean isSuccessful);
    }

    interface Presenter {

        void searchStringChange(String newText);

        void onRequestLocationClicked(String searchString);
    }
}
