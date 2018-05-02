package com.osahub.rachit.streetview.utils;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class Constants {

    public interface EXTRAS {
        String CATEGORY_ID = "category_id";
        String LOCATION_ID = "location_id";
        String LOCATION_POSITION = "location_position";
        String LOCATION_ID_LIST = "location_id_list";
        String SHOW_FAVOURITES = "show_favourites";
        String DATA_SET = "data_set";
    }

    public interface TRANSITION_KEYS {
        String MAP_VIEW = "mapView";
    }

    public interface QUERY_PARAMETERS {
        String LOCATION_ID = "location_id";
        String CATEGORY_ID = "category_id";
        String NAME = "name";
    }

    public interface JSON_PROPERTIES {
        String LOCATION_IMAGES = "location_images";
        String LOCATION_SIMILAR_PLACES = "location_similar_places";
        String LOCATION = "location";
        String CATEGORY = "category";
        String LOCATIONS = "locations";
        String CATEGORIES = "categories";
        String CATEGORY_LOCATIONS = "category_locations";
    }

    public interface REQUEST_CODE {
        int STREET_VIEW_DETAIL = 100;
    }

    public interface CATEGORY_TYPE {
        String SPECIAL = "special";
        String COUNTRY = "country";
    }

    public interface ACTION {
        String DATA_UPDATED = "data_updated";
    }
}
