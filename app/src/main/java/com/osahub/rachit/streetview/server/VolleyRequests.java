package com.osahub.rachit.streetview.server;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Class that contains methods to create Volley Requests
 * Created by Rachit on 29-May-16.
 */
public class VolleyRequests {

    public static JsonObjectRequest createFetchCategoriesJsonObjectRequest(final VolleyResponse volleyResponse) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchCategoriesURL(), null,
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

    public static JsonObjectRequest createFetchLocationsJsonObjectRequest(final VolleyResponse volleyResponse) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchLocationsURL(), null,
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

    public static JsonObjectRequest createFetchCategoryLocationsJsonObjectRequest(final VolleyResponse volleyResponse) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchCategoryLocationsURL(), null,
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

    static JsonObjectRequest createFetchCategoryByIdJsonObjectRequest(final VolleyResponse volleyResponse, int categoryId) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchCategoryByIdURL(categoryId), null,
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

    static JsonObjectRequest createFetchLocationsByCategoryIdJsonObjectRequest(final VolleyResponse volleyResponse, int categoryId) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createFetchLocationsByCategoryIdURL(categoryId), null,
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

    static JsonObjectRequest createAddRequestedLocationJsonObjectRequest(final VolleyResponse volleyResponse, String name) {
        return new JsonObjectRequest(Request.Method.GET, NetworkURL.createAddRequestedLocationURL(name), null,
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