package com.osahub.rachit.streetview.modules.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.service.FetchDataIntentService;

public class SplashActivity extends BaseActivity {

    public static boolean databaseHasData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FetchDataIntentService.startActionFetchDataFromServer(this);
    }
}