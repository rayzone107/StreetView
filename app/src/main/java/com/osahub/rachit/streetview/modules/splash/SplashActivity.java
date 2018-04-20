package com.osahub.rachit.streetview.modules.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.service.FetchDataIntentService;

public class SplashActivity extends AppCompatActivity {

    public static boolean databaseHasData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*List<CategoryLocations> categoryLocationsList = DataParser.getAllCategoryLocations(this);
        if (categoryLocationsList.size() > 0) {
            databaseHasData = true;
            Intent intent = new Intent(SplashActivity.this, LocationsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }*/
        FetchDataIntentService.startActionFetchDataFromServer(this);
    }
}