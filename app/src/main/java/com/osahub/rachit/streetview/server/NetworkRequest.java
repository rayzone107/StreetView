package com.osahub.rachit.streetview.server;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.osahub.rachit.streetview.AppController;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImages;
import com.osahub.rachit.streetview.model.LocationSimilarPlaces;
import com.osahub.rachit.streetview.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by Rachit Goyal on 29/04/18
 */

public class NetworkRequest {

    private Gson mGson;

    public NetworkRequest() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        mGson = builder.create();
    }

    public void getLocationImages(int locationId, final NetworkResponse networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationImagesJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<LocationImages> locationImages =
                                    mGson.fromJson(response.getJSONArray(Constants.JSON_PROPERTIES.LOCATION_IMAGES).toString(),
                                            new TypeToken<List<LocationImages>>() {
                                            }.getType());
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

    public void getLocationSimilarPlaces(int locationId, final NetworkResponse networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationSimilarPlacesJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            List<LocationSimilarPlaces> locationSimilarPlaces =
                                    mGson.fromJson(response.getJSONArray(Constants.JSON_PROPERTIES.LOCATION_SIMILAR_PLACES).toString(),
                                            new TypeToken<List<LocationSimilarPlaces>>() {
                                            }.getType());
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

    public void getLocationById(int locationId, final NetworkResponse<Location> networkResponse) {
        AppController.getInstance().addToRequestQueue(VolleyRequests.
                createFetchLocationByIdJsonObjectRequest(new VolleyResponse() {
                    @Override
                    public void onData(JSONObject response) {
                        try {
                            Location location =
                                    mGson.fromJson(response.getJSONObject(Constants.JSON_PROPERTIES.LOCATION_SIMILAR_PLACES).toString(),
                                            new TypeToken<List<Location>>() {
                                            }.getType());
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
}
