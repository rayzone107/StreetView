package com.osahub.rachit.streetview.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.osahub.rachit.streetview.AppController;
import com.osahub.rachit.streetview.activity.LocationsActivity;
import com.osahub.rachit.streetview.activity.SplashActivity;
import com.osahub.rachit.streetview.database.DataParser;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.CategoryLocations;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.server.VolleyRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FetchDataIntentService extends IntentService {
    private static final String ACTION_FETCH_DATA = "ACTION_FETCH_DATA";

    private List<Category> categoryList = new ArrayList<>();
    private List<Location> locationList = new ArrayList<>();
    private List<CategoryLocations> categoryLocationsList = new ArrayList<>();

    boolean isCategoriesSaved = false, isLocationsSaved = false, isCategoryLocationsSaved = false;

    public FetchDataIntentService() {
        super("FetchDataIntentService");
    }

    public static void startActionFetchDataFromServer(Context context) {
        Intent intent = new Intent(context, FetchDataIntentService.class);
        intent.setAction(ACTION_FETCH_DATA);
        context.startService(intent);
    }

    public void saveCategoriesList(JSONObject response) throws JSONException, ParseException {
        JSONArray categoriesJSON = response.getJSONArray("categories");
        for (int i = 0; i < categoriesJSON.length(); i++) {
            categoryList.add(Category.fromJson(categoriesJSON.getJSONObject(i)));
        }
        saveToDatabase("Categories");
    }

    public void saveLocationsList(JSONObject response) throws JSONException, ParseException {
        JSONArray locationsJSON = response.getJSONArray("locations");
        for (int i = 0; i < locationsJSON.length(); i++) {
            locationList.add(Location.fromJson(locationsJSON.getJSONObject(i)));
        }
        saveToDatabase("Locations");
    }

    public void saveCategoryLocationsList(JSONObject response) throws JSONException, ParseException {
        JSONArray categoryLocationsJSON = response.getJSONArray("category_locations");
        for (int i = 0; i < categoryLocationsJSON.length(); i++) {
            categoryLocationsList.add(CategoryLocations.fromJson(categoryLocationsJSON.getJSONObject(i)));
        }
        saveToDatabase("CategoryLocations");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_DATA.equals(action)) {
                handleActionFetchDataFromServer();
            }
        }
    }

    private void handleActionFetchDataFromServer() {
        AppController.getInstance().addToRequestQueue(VolleyRequests.createFetchCategoriesJsonObjectRequest(this));
        AppController.getInstance().addToRequestQueue(VolleyRequests.createFetchLocationsJsonObjectRequest(this));
        AppController.getInstance().addToRequestQueue(VolleyRequests.createFetchCategoryLocationsJsonObjectRequest(this));
    }

    private void saveToDatabase(String type) {
        switch (type) {
            case "Categories":
                DataParser.addMultipleCategories(this, categoryList);
                isCategoriesSaved = true;
                actionIfAllSaved();
                break;
            case "Locations":
                DataParser.addMultipleLocations(this, locationList);
                isLocationsSaved = true;
                actionIfAllSaved();
                break;
            case "CategoryLocations":
                DataParser.addMultipleCategoryLocations(this, categoryLocationsList);
                isCategoryLocationsSaved = true;
                actionIfAllSaved();
                break;
        }
    }

    private void actionIfAllSaved() {
        if (!SplashActivity.databaseHasData && isCategoriesSaved && isLocationsSaved && isCategoryLocationsSaved) {
            Intent intent = new Intent(FetchDataIntentService.this, LocationsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}