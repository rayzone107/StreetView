package com.osahub.rachit.streetview.server;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.osahub.rachit.streetview.AppController;
import com.osahub.rachit.streetview.database.DatabaseHelper;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.CategoryLocation;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImage;
import com.osahub.rachit.streetview.model.LocationSimilarPlace;
import com.osahub.rachit.streetview.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by Rachit Goyal on 29/04/18
 */

public class NetworkRequest {

    private Gson mGson;
    private DatabaseHelper mDatabaseHelper = new DatabaseHelper();

    public NetworkRequest() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        mGson = builder.create();
    }

    public void getAllCategories(final NetworkResponse<List<Category>> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchCategoriesJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<Category> categories = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.CATEGORIES).toString(),
                                    new TypeToken<List<Category>>() {
                                    }.getType());
                            mDatabaseHelper.mCategoryDbHelper.updateOrInsertMultipleCategories(categories);
                            networkResponse.onData(categories);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }));
    }

    public void getAllCategories() {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchCategoriesJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<Category> categories = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.CATEGORIES).toString(),
                                    new TypeToken<List<Category>>() {
                                    }.getType());
                            mDatabaseHelper.mCategoryDbHelper.updateOrInsertMultipleCategories(categories);
                        } catch (JSONException ignored) {
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                    }
                }));
    }

    public void getAllLocations(final NetworkResponse<List<Location>> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationsJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<Location> locations = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.LOCATIONS).toString(),
                                    new TypeToken<List<Location>>() {
                                    }.getType());
                            mDatabaseHelper.mLocationDbHelper.updateOrInsertMultipleLocations(locations);
                            networkResponse.onData(locations);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }));
    }

    public void getAllLocations() {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationsJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<Location> locations = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.LOCATIONS).toString(),
                                    new TypeToken<List<Location>>() {
                                    }.getType());
                            mDatabaseHelper.mLocationDbHelper.updateOrInsertMultipleLocations(locations);
                        } catch (JSONException ignored) {
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                    }
                }));
    }

    public void getAllCategoryLocations(final NetworkResponse<List<CategoryLocation>> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchCategoryLocationsJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<CategoryLocation> categoryLocations = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.CATEGORY_LOCATIONS).toString(),
                                    new TypeToken<List<CategoryLocation>>() {
                                    }.getType());
                            mDatabaseHelper.mCategoryLocationDatabaseHelper.updateOrInsertMultipleCategoryLocations(categoryLocations);
                            networkResponse.onData(categoryLocations);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }));
    }

    public void getAllCategoryLocations() {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchCategoryLocationsJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<CategoryLocation> categoryLocations = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.CATEGORY_LOCATIONS).toString(),
                                    new TypeToken<List<CategoryLocation>>() {
                                    }.getType());
                            mDatabaseHelper.mCategoryLocationDatabaseHelper.updateOrInsertMultipleCategoryLocations(categoryLocations);
                        } catch (JSONException ignored) {
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                    }
                }));
    }

    public void getCategoryById(int categoryId, final NetworkResponse<Category> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchCategoryByIdJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            Category category = mGson.fromJson(response.getJSONObject(
                                    Constants.JSON_PROPERTIES.CATEGORY).toString(),
                                    new TypeToken<Category>() {
                                    }.getType());
                            mDatabaseHelper.mCategoryDbHelper.updateOrInsertCategory(category);
                            networkResponse.onData(category);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }, categoryId));
    }

    public void getLocationsByCategoryId(int categoryId, final NetworkResponse<List<Location>> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationsByCategoryIdJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<Location> locationList = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.LOCATIONS).toString(),
                                    new TypeToken<List<Location>>() {
                                    }.getType());
                            mDatabaseHelper.mLocationDbHelper.updateOrInsertMultipleLocations(locationList);
                            networkResponse.onData(locationList);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }, categoryId));
    }

    public void getLocationById(int locationId, final NetworkResponse<Location> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationByIdJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            Location location = mGson.fromJson(response.getJSONObject(
                                    Constants.JSON_PROPERTIES.LOCATION).toString(),
                                    new TypeToken<Location>() {
                                    }.getType());
                            mDatabaseHelper.mLocationDbHelper.updateOrInsertLocation(location);
                            networkResponse.onData(location);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }, locationId));
    }

    public void getLocationImages(int locationId, final NetworkResponse<List<LocationImage>> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationImagesJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<LocationImage> locationImages = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.LOCATION_IMAGES).toString(),
                                    new TypeToken<List<LocationImage>>() {
                                    }.getType());
                            mDatabaseHelper.mLocationDbHelper.updateOrInsertMultipleLocationImages(locationImages);
                            networkResponse.onData(locationImages);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }, locationId));
    }

    public void getLocationSimilarPlaces(int locationId, final NetworkResponse<List<LocationSimilarPlace>> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationSimilarPlacesJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<LocationSimilarPlace> locationSimilarPlaces = mGson.fromJson(response.getJSONArray(
                                    Constants.JSON_PROPERTIES.LOCATION_SIMILAR_PLACES).toString(),
                                    new TypeToken<List<LocationSimilarPlace>>() {
                                    }.getType());
                            mDatabaseHelper.mLocationDbHelper.updateOrInsertMultipleSimilarPlaces(locationSimilarPlaces);
                            networkResponse.onData(locationSimilarPlaces);
                        } catch (JSONException e) {
                            networkResponse.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }, locationId));
    }

    public void addRequestedLocation(String name, final NetworkResponse<Void> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createAddRequestedLocationJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        networkResponse.onData(null);
                    }

                    @Override
                    public void onError(VolleyError e) {
                        networkResponse.onError();
                    }
                }, name));
    }
}
