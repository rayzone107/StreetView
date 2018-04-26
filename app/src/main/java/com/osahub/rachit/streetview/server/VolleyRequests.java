package com.osahub.rachit.streetview.server;

import android.net.Uri;
import android.util.Log;

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

    private static final String LOG_TAG = "World Tour 3D: " + VolleyRequests.class.getSimpleName();

    /**
     * Create the string URL to fetch categories from server.
     *
     * @return URL String
     */
    private static String createFetchCategoriesURL() {
        final String API_BASE_URL = "http://rachitgoyal.com/php/world_tour/get_all_categories2.php";

        Uri builtUri = Uri.parse(API_BASE_URL);

        Log.d(LOG_TAG, "Categories QUERY URI: " + builtUri.toString());
        return builtUri.toString();
    }

    /**
     * Create a JsonObjectRequest object for the Volley queue to fetch categories from server.
     *
     * @param context Application Context
     * @return JsonObjectRequest object for categories JSON
     */
    public static JsonObjectRequest createFetchCategoriesJsonObjectRequest(final FetchDataIntentService context) {
        return new JsonObjectRequest(Request.Method.GET, createFetchCategoriesURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            context.saveCategoriesList(response);
                        } catch (JSONException | ParseException e) {
                            context.showError();
                        }
                        Log.d(LOG_TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.e(LOG_TAG, "Something went wrong!");
                error.printStackTrace();
                context.showError();
            }
        });
    }

    /**
     * Create the string URL to fetch all locations from server.
     *
     * @return URL String
     */
    private static String createFetchLocationsURL() {
        final String API_BASE_URL = "http://rachitgoyal.com/php/world_tour/get_all_locations2.php";

        Uri builtUri = Uri.parse(API_BASE_URL);

        Log.d(LOG_TAG, "Locations QUERY URI: " + builtUri.toString());
        return builtUri.toString();
    }

    /**
     * Create a JsonObjectRequest object for the Volley queue to fetch all locations from server.
     *
     * @param context Application Context
     * @return JsonObjectRequest object for locations JSON
     */
    public static JsonObjectRequest createFetchLocationsJsonObjectRequest(final FetchDataIntentService context) {
        return new JsonObjectRequest(Request.Method.GET, createFetchLocationsURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            context.saveLocationsList(response);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d(LOG_TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.e(LOG_TAG, "Something went wrong!");
                error.printStackTrace();
            }
        });
    }

    /**
     * Create the string URL to fetch all category_locations from server.
     *
     * @return URL String
     */
    private static String createFetchCategoryLocationsURL() {
        final String API_BASE_URL = "http://rachitgoyal.com/php/world_tour/get_all_category_locations.php";

        Uri builtUri = Uri.parse(API_BASE_URL);

        Log.d(LOG_TAG, "Categories Locations QUERY URI: " + builtUri.toString());
        return builtUri.toString();
    }

    /**
     * Create a JsonObjectRequest object for the Volley queue to fetch all category_locations from server.
     *
     * @param context Application Context
     * @return JsonObjectRequest object for movies JSON
     */
    public static JsonObjectRequest createFetchCategoryLocationsJsonObjectRequest(final FetchDataIntentService context) {
        return new JsonObjectRequest(Request.Method.GET, createFetchCategoryLocationsURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            context.saveCategoryLocationsList(response);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d(LOG_TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.e(LOG_TAG, "Something went wrong!");
                error.printStackTrace();
            }
        });
    }

    /**
     * Create the string URL to fetch all category_locations from server.
     *
     * @return URL String
     */
    private static String createFetchLocationImagesURL(int locationId) {
        final String API_BASE_URL = "http://rachitgoyal.com/php/world_tour/get_all_location_images.php";

        Uri builtUri = Uri.parse(API_BASE_URL);
        builtUri.buildUpon().appendQueryParameter("locationid", String.valueOf(locationId));

        Log.d(LOG_TAG, "Location Images QUERY URI: " + builtUri.toString());
        return builtUri.toString();
    }

    /**
     * Create a JsonObjectRequest object for the Volley queue to fetch all category_locations from server.
     *
     * @param context Application Context
     * @return JsonObjectRequest object for movies JSON
     */
    public static JsonObjectRequest createFetchLocationImagesJsonObjectRequest(final FetchDataIntentService context, int locationId) {
        return new JsonObjectRequest(Request.Method.GET, createFetchLocationImagesURL(locationId), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            context.saveCategoryLocationsList(response);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d(LOG_TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.e(LOG_TAG, "Something went wrong!");
                error.printStackTrace();
            }
        });
    }
}