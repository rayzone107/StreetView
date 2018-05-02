package com.osahub.rachit.streetview.service;

import android.content.Context;
import android.content.Intent;

import com.osahub.rachit.streetview.modules.base.BaseIntentService;
import com.osahub.rachit.streetview.server.NetworkRequest;

public class FetchDataIntentService extends BaseIntentService {

    public FetchDataIntentService() {
        super("FetchDataIntentService");
    }

    public static void startService(Context context) {
        context.startService(new Intent(context, FetchDataIntentService.class));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handleActionFetchDataFromServer();
    }

    private void handleActionFetchDataFromServer() {
        NetworkRequest networkRequest = new NetworkRequest();
        networkRequest.getAllCategories();
        networkRequest.getAllLocations();
        networkRequest.getAllCategoryLocations();
    }
}