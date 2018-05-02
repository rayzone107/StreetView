package com.osahub.rachit.streetview.modules.splash;

import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;

/**
 * Created by Rachit Goyal on 02/05/18
 */

public interface SplashContract {
    interface View {

        void changeViewState(ViewState viewState);

        void startLocationActivity();
    }
    interface Presenter {

        void onCreate();
    }
}
