package com.osahub.rachit.streetview.server;

import android.net.Uri;

import com.osahub.rachit.streetview.utils.Constants;

/**
 * Created by Rachit Goyal on 29/04/18
 */

class NetworkURL {

    private static final String BASE_URL = "http://rachitgoyal.com/php/world_tour/";

    static String createFetchCategoriesURL() {
        final String API_URL = BASE_URL + "get_all_categories.php";
        return Uri.parse(API_URL).toString();
    }

    static String createFetchLocationsURL() {
        final String API_BASE_URL = BASE_URL + "get_all_locations.php";
        return Uri.parse(API_BASE_URL).toString();
    }

    static String createFetchCategoryLocationsURL() {
        final String API_BASE_URL = BASE_URL + "get_all_category_locations.php";
        return Uri.parse(API_BASE_URL).toString();
    }

    static String createFetchLocationImagesURL(int locationId) {
        final String API_BASE_URL = BASE_URL + "get_location_images.php";
        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon().
                appendQueryParameter(Constants.QUERY_PARAMETERS.LOCATION_ID, String.valueOf(locationId)).build();
        return builtUri.toString();
    }

    static String createFetchLocationSimilarPlacesURL(int locationId) {
        final String API_BASE_URL = BASE_URL + "get_location_similar_places.php";
        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon().
                appendQueryParameter(Constants.QUERY_PARAMETERS.LOCATION_ID, String.valueOf(locationId)).build();
        return builtUri.toString();
    }

    static String createFetchLocationByIdURL(int locationId) {
        final String API_BASE_URL = BASE_URL + "get_location_by_id.php";
        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon().
                appendQueryParameter(Constants.QUERY_PARAMETERS.LOCATION_ID, String.valueOf(locationId)).build();
        return builtUri.toString();
    }

    static String createFetchCategoryByIdURL(int locationId) {
        final String API_BASE_URL = BASE_URL + "get_category_by_id.php";
        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon().
                appendQueryParameter(Constants.QUERY_PARAMETERS.LOCATION_ID, String.valueOf(locationId)).build();
        return builtUri.toString();
    }

    static String createFetchLocationsByCategoryIdURL(int categoryID) {
        final String API_BASE_URL = BASE_URL + "get_locations_by_category_id.php";
        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon().
                appendQueryParameter(Constants.QUERY_PARAMETERS.CATEGORY_ID, String.valueOf(categoryID)).build();
        return builtUri.toString();
    }

    static String createAddRequestedLocationURL(String name) {
        final String API_BASE_URL = BASE_URL + "add_requested_location.php";
        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon().
                appendQueryParameter(Constants.QUERY_PARAMETERS.NAME, name).build();
        return builtUri.toString();
    }

    static String createGetRequestedLocationLatLngURL(String name) {
        String baseURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
        String api = "&key=AIzaSyDknW1CpDxTEn5VwEAt1fbic2OjYRKMzlw";

        Uri builtUri = Uri.parse(baseURL).buildUpon().appendQueryParameter("address", name).appendPath(api).build();
        return builtUri.toString();
    }
}
