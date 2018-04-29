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
}
