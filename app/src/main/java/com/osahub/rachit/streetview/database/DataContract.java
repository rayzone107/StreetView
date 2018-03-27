package com.osahub.rachit.streetview.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class for Database
 * Created by Rachit on 30-May-16.
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.osahub.rachit.worldtour";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CATEGORIES = "categories";
    public static final String PATH_LOCATIONS = "locations";
    public static final String PATH_CATEGORY_LOCATIONS = "category_locations";

    public static final class CategoriesEntry implements BaseColumns {

        public static final String TABLE_NAME = "categories";

        public static final String COLUMN_ID = "category_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ORDER = "order_num";
        public static final String COLUMN_CREATED_ON = "created_on";
        public static final String COLUMN_UPDATED_ON = "updated_on";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORIES).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORIES;

        public static Uri buildCategoriesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriFromId(int id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String getIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class LocationsEntry implements BaseColumns {

        public static final String TABLE_NAME = "locations";

        public static final String COLUMN_ID = "location_id";
        public static final String COLUMN_LOCATION_NAME = "location_name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DESCRIPTION_SMALL = "description_small";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_IMAGE_PATH = "image_path";
        public static final String COLUMN_THUMBNAIL_PATH = "thumbnail_path";
        public static final String COLUMN_CREATED_ON = "created_on";
        public static final String COLUMN_UPDATED_ON = "updated_on";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATIONS).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATIONS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATIONS;

        public static Uri buildLocationsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriFromId(int id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String getIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class CategoryLocationsEntry implements BaseColumns {

        public static final String TABLE_NAME = "category_locations";

        public static final String COLUMN_ID = "category_locations_id";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_LOCATION_ID = "location_id";
        public static final String COLUMN_ORDER = "order_num";
        public static final String COLUMN_CREATED_ON = "created_on";
        public static final String COLUMN_UPDATED_ON = "updated_on";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY_LOCATIONS).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY_LOCATIONS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY_LOCATIONS;

        public static Uri buildCategoryLocationsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriFromId(int id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String getIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

}
