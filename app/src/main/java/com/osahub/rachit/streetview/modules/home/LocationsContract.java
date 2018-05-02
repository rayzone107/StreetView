package com.osahub.rachit.streetview.modules.home;

import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;

import java.util.List;

/**
 * Created by Rachit Goyal on 26/04/18
 */

public interface LocationsContract {
    interface View {

        void changeViewState(ViewState viewState);

        void showData(List<Category> categories);
    }

    interface Presenter {

        void onResume();

    }
}
