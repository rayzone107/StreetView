package com.osahub.rachit.streetview.server;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.osahub.rachit.streetview.service.FetchDataIntentService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Class that contains methods to create Volley Requests
 * Created by Rachit on 29-May-16.
 */
public class VolleyRequests {

    public static JsonObjectRequest createFetchCategoriesJsonObjectRequest(final FetchDataIntentService context) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchCategoriesURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            context.saveCategoriesList(response);
                        } catch (JSONException | ParseException e) {
                            context.showError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                context.showError();
            }
        });
    }

    public static JsonObjectRequest createFetchLocationsJsonObjectRequest(final FetchDataIntentService context) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchLocationsURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            context.saveLocationsList(response);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    public static JsonObjectRequest createFetchCategoryLocationsJsonObjectRequest(final FetchDataIntentService context) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchCategoryLocationsURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            context.saveCategoryLocationsList(response);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    static JsonObjectRequest createFetchLocationImagesJsonObjectRequest(final VolleyResponse volleyResponse, int locationId) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchLocationImagesURL(locationId), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyResponse.onData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponse.onError(error);
            }
        });
    }

    static JsonObjectRequest createFetchLocationSimilarPlacesJsonObjectRequest(final VolleyResponse volleyResponse, int locationId) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchLocationSimilarPlacesURL(locationId), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyResponse.onData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponse.onError(error);
            }
        });
    }

    static JsonObjectRequest createFetchLocationByIdJsonObjectRequest(final VolleyResponse volleyResponse, int locationId) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchLocationByIdURL(locationId), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyResponse.onData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponse.onError(error);
            }
        });
    }
}