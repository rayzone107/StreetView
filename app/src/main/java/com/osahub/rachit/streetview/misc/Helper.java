package com.osahub.rachit.streetview.misc;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.osahub.rachit.streetview.database.DataContract.CategoriesEntry;
import com.osahub.rachit.streetview.database.DataContract.CategoryLocationsEntry;
import com.osahub.rachit.streetview.database.DataContract.LocationsEntry;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.CategoryLocations;
import com.osahub.rachit.streetview.model.Location;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Helper Class for generic constants and methods
 * Created by Rachit on 22-Apr-16.
 */
public class Helper {

    private static final String LOG_TAG = "World Tour 3D: " + Helper.class.getSimpleName();

    public static final int MAX_PER_LIST_ON_HOME_SCREEN = 10;
    public static final String CATEGORY_EXTRA = "category";

    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd hh:mm:ss";

    public static final String QUERY_SORT_ORDER_ASC = "order_num ASC";
    public static final String QUERY_SORT_ORDER_DESC = "order_num DESC";

    public static final String CATEGORY_TYPE_SPECIAL = "special";

    public static final String LOCATION_FRAGMENT_BLANK_TAG = "BLANK";

    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault());
        return sdf.parse(date);
    }

    public static String convertDateToString(Date date) {
        return (new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())).format(date);
    }

    public static String urlGenerator(String name, int position) {
        return ("http://rachitgoyal.com/images/world_tour/high_res/location_images/" + name + "/" + name + "_" + position + ".jpg");
    }

    public static class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mHorizontalSpaceWidth;

        public HorizontalSpaceItemDecoration(int mHorizontalSpaceWidth) {
            this.mHorizontalSpaceWidth = mHorizontalSpaceWidth;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.left = mHorizontalSpaceWidth;
            } else {
                outRect.right = mHorizontalSpaceWidth;
                outRect.left = mHorizontalSpaceWidth;
            }
        }
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    public static Uri getLocalBitmapUri(Context context, Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image.jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static ContentValues generateContentValuesFromCategoryObject(Category category) {
        ContentValues categoryValues = new ContentValues();
        categoryValues.put(CategoriesEntry.COLUMN_ID, category.getId());
        categoryValues.put(CategoriesEntry.COLUMN_NAME, category.getName());
        categoryValues.put(CategoriesEntry.COLUMN_TYPE, category.getType());
        categoryValues.put(CategoriesEntry.COLUMN_ORDER, category.getOrder());
        categoryValues.put(CategoriesEntry.COLUMN_CREATED_ON, Helper.convertDateToString(category.getCreatedOn()));
        categoryValues.put(CategoriesEntry.COLUMN_UPDATED_ON, Helper.convertDateToString(category.getUpdatedOn()));
        return categoryValues;
    }

    public static ContentValues generateContentValuesFromLocationObject(Location location) {
        ContentValues locationValues = new ContentValues();
        locationValues.put(LocationsEntry.COLUMN_ID, location.getId());
        locationValues.put(LocationsEntry.COLUMN_LOCATION_NAME, location.getLocationName());
        locationValues.put(LocationsEntry.COLUMN_DESCRIPTION, location.getDescription());
        locationValues.put(LocationsEntry.COLUMN_DESCRIPTION_SMALL, location.getDescriptionSmall());
        locationValues.put(LocationsEntry.COLUMN_CITY, location.getCity());
        locationValues.put(LocationsEntry.COLUMN_STATE, location.getState());
        locationValues.put(LocationsEntry.COLUMN_COUNTRY, location.getCountry());
        locationValues.put(LocationsEntry.COLUMN_LATITUDE, location.getLatitude());
        locationValues.put(LocationsEntry.COLUMN_LONGITUDE, location.getLongitude());
        locationValues.put(LocationsEntry.COLUMN_IMAGE_PATH, location.getImageLinks());
        locationValues.put(LocationsEntry.COLUMN_THUMBNAIL_PATH, location.getThumbnailPath());
        locationValues.put(LocationsEntry.COLUMN_CREATED_ON, Helper.convertDateToString(location.getCreatedOn()));
        locationValues.put(LocationsEntry.COLUMN_UPDATED_ON, Helper.convertDateToString(location.getUpdatedOn()));
        return locationValues;
    }

    public static ContentValues generateContentValuesFromCategoryLocationsObject(CategoryLocations categoryLocations) {
        ContentValues categoryValues = new ContentValues();
        categoryValues.put(CategoryLocationsEntry.COLUMN_ID, categoryLocations.getId());
        categoryValues.put(CategoryLocationsEntry.COLUMN_CATEGORY_ID, categoryLocations.getCategoryId());
        categoryValues.put(CategoryLocationsEntry.COLUMN_LOCATION_ID, categoryLocations.getLocationId());
        categoryValues.put(CategoryLocationsEntry.COLUMN_ORDER, categoryLocations.getOrder());
        categoryValues.put(CategoryLocationsEntry.COLUMN_CREATED_ON, Helper.convertDateToString(categoryLocations.getCreatedOn()));
        categoryValues.put(CategoryLocationsEntry.COLUMN_UPDATED_ON, Helper.convertDateToString(categoryLocations.getUpdatedOn()));
        return categoryValues;
    }

    public static List<String> generateImageLinksArrayFromLinksString(String imageLinks) {
        List<String> imageLinksList = Arrays.asList(imageLinks.split("\\s*,\\s*"));
        Collections.sort(imageLinksList);
        return imageLinksList;
    }

    public static int convertDpToPixel(Context context, int dpMeasure) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpMeasure,
                context.getResources().getDisplayMetrics()
        );
    }
}