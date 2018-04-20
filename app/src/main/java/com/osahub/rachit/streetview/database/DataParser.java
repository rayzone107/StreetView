package com.osahub.rachit.streetview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.osahub.rachit.streetview.database.DataContract.CategoriesEntry;
import com.osahub.rachit.streetview.database.DataContract.CategoryLocationsEntry;
import com.osahub.rachit.streetview.database.DataContract.LocationsEntry;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.CategoryLocations;
import com.osahub.rachit.streetview.model.Location;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser Class for Database to provide easy Database access and modification methods.
 * Created by Rachit on 30-May-16.
 */
public class DataParser {

    private static final String LOG_TAG = "World Tour 3D: " + DataParser.class.getSimpleName();

    /*
        CATEGORY TABLE METHODS - Start
     */

    public static boolean addCategory(Context context, Category category) {
        Cursor categoryCursor = context.getContentResolver().query(
                CategoriesEntry.CONTENT_URI,
                null,
                CategoriesEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(category.getId())},
                null);

        if (categoryCursor != null && categoryCursor.moveToFirst()) {
            /*try {
                int dateCompare = Helper.convertStringToDate(categoryCursor.getString(Category.COLUMN_UPDATED_ON))
                        .compareTo(category.getUpdatedOn());
                if (dateCompare < 0) {*/
                    updateCategoryById(context, category, category.getId());
//                }
                categoryCursor.close();
            /*} catch (ParseException e) {
                e.printStackTrace();
            }*/
            return false;
        } else {
            Uri insertedUri = context.getContentResolver().insert(CategoriesEntry.CONTENT_URI,
                    Helper.generateContentValuesFromCategoryObject(category));
            Log.i(LOG_TAG, "Category inserted" + insertedUri);
            return true;
        }
    }

    public static boolean addMultipleCategories(Context context, List<Category> categoryList) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        for (Category category : categoryList) {
            Cursor categoryCursor = context.getContentResolver().query(
                    CategoriesEntry.CONTENT_URI,
                    null,
                    CategoriesEntry.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(category.getId())},
                    null);
            if (categoryCursor != null && categoryCursor.moveToFirst()) {
               /* try {
                    int dateCompare = Helper.convertStringToDate(categoryCursor.getString(Category.COLUMN_UPDATED_ON))
                            .compareTo(category.getUpdatedOn());
                    if (dateCompare < 0) {*/
                        updateCategoryById(context, category, category.getId());
                    /*}*/
                    categoryCursor.close();
                /*} catch (ParseException e) {
                    e.printStackTrace();
                }*/
            } else {
                contentValuesList.add(Helper.generateContentValuesFromCategoryObject(category));
            }
        }
        int categoriesInserted = context.getContentResolver().bulkInsert(CategoriesEntry.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
        Log.i(LOG_TAG, "Number of Categories inserted" + categoriesInserted);
        return categoriesInserted > 0;
    }

    public static Category getCategoryById(Context context, int id) {
        Category category = new Category();
        Cursor categoryCursor = context.getContentResolver().query(CategoriesEntry.CONTENT_URI, null,
                CategoriesEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if (categoryCursor != null) {
            if (categoryCursor.moveToFirst()) {
                do {
                    try {
                        category = Category.fromCursor(categoryCursor);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (categoryCursor.moveToNext());
            }
            categoryCursor.close();
        } else {
            Log.d(LOG_TAG, "Can't find category with id = " + id);
        }
        return category;
    }

    public static Category getCategoryByName(Context context, String name) {
        Category category = new Category();
        Cursor categoryCursor = context.getContentResolver().query(CategoriesEntry.CONTENT_URI, null,
                CategoriesEntry.COLUMN_NAME + " = ?", new String[]{name}, null);

        if (categoryCursor != null) {
            if (categoryCursor.moveToFirst()) {
                do {
                    try {
                        category = Category.fromCursor(categoryCursor);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (categoryCursor.moveToNext());
            }
            categoryCursor.close();
        } else {
            Log.d(LOG_TAG, "Can't find category with name = " + name);
        }
        return category;
    }

    public static List<Category> getAllCategories(Context context) {
        List<Category> categoryList = new ArrayList<>();
        Cursor categoryCursor = context.getContentResolver().query(CategoriesEntry.CONTENT_URI, null,
                null, null, Helper.QUERY_SORT_ORDER_ASC);

        if (categoryCursor != null) {
            if (categoryCursor.moveToFirst()) {
                do {
                    try {
                        categoryList.add(Category.fromCursor(categoryCursor));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (categoryCursor.moveToNext());
            }
            categoryCursor.close();
        } else {
            Log.d(LOG_TAG, "No Categories exist");
        }
        return categoryList;
    }

    public static boolean updateCategoryById(Context context, Category newCategory, int categoryID) {
        int updated = context.getContentResolver().update(CategoriesEntry.CONTENT_URI,
                Helper.generateContentValuesFromCategoryObject(newCategory),
                CategoriesEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(categoryID)});

        Log.i(LOG_TAG, "Category Updated " + updated);

        return updated != 0;
    }

    public static boolean deleteCategoryById(Context context, int categoryID) {
        int updated = context.getContentResolver().delete(CategoriesEntry.CONTENT_URI,
                CategoriesEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(categoryID)});

        Log.i(LOG_TAG, "Category Deleted " + updated);

        return updated != 0;
    }

    /*
        CATEGORY TABLE METHODS - End
     */

    /*
        LOCATION TABLE METHODS - Start
     */

    public static boolean addLocation(Context context, Location location) {
        Cursor locationCursor = context.getContentResolver().query(
                LocationsEntry.CONTENT_URI,
                null,
                LocationsEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(location.getId())},
                null);

        if (locationCursor != null && locationCursor.moveToFirst()) {
            /*try {
                int dateCompare = Helper.convertStringToDate(locationCursor.getString(Location.COLUMN_UPDATED_ON)).compareTo(location.getUpdatedOn());
                if (dateCompare < 0) {*/
                    updateLocationById(context, location, location.getId());
                /*}*/
                locationCursor.close();
            /*} catch (ParseException e) {
                e.printStackTrace();
            }*/
            return false;
        } else {
            Uri insertedUri = context.getContentResolver().insert(LocationsEntry.CONTENT_URI,
                    Helper.generateContentValuesFromLocationObject(location));
            Log.i(LOG_TAG, "Location inserted" + insertedUri);
            return true;
        }
    }

    public static boolean addMultipleLocations(Context context, List<Location> locationList) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        for (Location location : locationList) {
            Cursor locationCursor = context.getContentResolver().query(
                    LocationsEntry.CONTENT_URI,
                    null,
                    LocationsEntry.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(location.getId())},
                    null);
            if (locationCursor != null && locationCursor.moveToFirst()) {
                /*try {
                    int dateCompare = Helper.convertStringToDate(locationCursor.getString(Location.COLUMN_UPDATED_ON)).compareTo(location.getUpdatedOn());
                    if (dateCompare < 0) {*/
                        updateLocationById(context, location, location.getId());
                    /*}*/
                    locationCursor.close();
                /*} catch (ParseException e) {
                    e.printStackTrace();
                }*/
            } else {
                contentValuesList.add(Helper.generateContentValuesFromLocationObject(location));
            }
        }
        int locationsInserted = context.getContentResolver().bulkInsert(LocationsEntry.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
        Log.i(LOG_TAG, "Number of Locations inserted" + locationsInserted);
        return locationsInserted > 0;
    }

    public static Location getLocationById(Context context, int id) {
        Location location = new Location();
        Cursor locationCursor = context.getContentResolver().query(LocationsEntry.CONTENT_URI, null,
                LocationsEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if (locationCursor != null) {
            if (locationCursor.moveToFirst()) {
                do {
                    try {
                        location = Location.fromCursor(locationCursor);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (locationCursor.moveToNext());
            }
            locationCursor.close();
        } else {
            Log.d(LOG_TAG, "Can't find location with id = " + id);
        }
        return location;
    }

    public static Location getLocationByName(Context context, String name) {
        Location location = new Location();
        Cursor locationCursor = context.getContentResolver().query(LocationsEntry.CONTENT_URI, null,
                LocationsEntry.COLUMN_LOCATION_NAME + " = ?", new String[]{name}, null);

        if (locationCursor != null) {
            if (locationCursor.moveToFirst()) {
                do {
                    try {
                        location = Location.fromCursor(locationCursor);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (locationCursor.moveToNext());
            }
            locationCursor.close();
        } else {
            Log.d(LOG_TAG, "Can't find location with name = " + name);
        }
        return location;
    }

    public static List<Location> getMultipleLocationsByNames(Context context, List<String> locationNames) {
        List<Location> locationList = new ArrayList<>();
        for (String locationName : locationNames) {
            Cursor locationCursor = context.getContentResolver().query(LocationsEntry.CONTENT_URI, null,
                    LocationsEntry.COLUMN_LOCATION_NAME + " = ?", new String[]{locationName}, null);

            if (locationCursor != null) {
                if (locationCursor.moveToFirst()) {
                    do {
                        try {
                            locationList.add(Location.fromCursor(locationCursor));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } while (locationCursor.moveToNext());
                }
                locationCursor.close();
            } else {
                Log.d(LOG_TAG, "Can't find location with name = " + locationName);
            }
        }
        Log.d(LOG_TAG, "Fetched " + locationList.size() + " locations from local DB");
        return locationList;
    }

    public static List<Location> getMultipleLocationsByIds(Context context, List<Integer> locationIds) {
        List<Location> locationList = new ArrayList<>();
        for (Integer locationId : locationIds) {
            Cursor locationCursor = context.getContentResolver().query(LocationsEntry.CONTENT_URI, null,
                    LocationsEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(locationId)}, null);

            if (locationCursor != null) {
                if (locationCursor.moveToFirst()) {
                    do {
                        try {
                            locationList.add(Location.fromCursor(locationCursor));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } while (locationCursor.moveToNext());
                }
                locationCursor.close();
            } else {
                Log.d(LOG_TAG, "Can't find location with ID = " + locationId);
            }
        }
        Log.d(LOG_TAG, "Fetched " + locationList.size() + " locations from local DB");
        return locationList;
    }

    public static List<Integer> getLocationIdsByCategoryId(Context context, int categoryId) {
        List<Integer> locationIds = new ArrayList<>();
        Cursor categoryLocationsCursor = context.getContentResolver().query(CategoryLocationsEntry.CONTENT_URI, null,
                CategoryLocationsEntry.COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)}, Helper.QUERY_SORT_ORDER_ASC);

        if (categoryLocationsCursor != null) {
            if (categoryLocationsCursor.moveToFirst()) {
                do {
                    locationIds.add(categoryLocationsCursor.getInt(CategoryLocations.COLUMN_LOCATION_ID));
                } while (categoryLocationsCursor.moveToNext());
            }
            categoryLocationsCursor.close();
        } else {
            Log.d(LOG_TAG, "No locations found for category with ID = " + categoryId);
        }
        return locationIds;
    }

    public static List<Location> getLocationsByCategoryId(Context context, int categoryId) {
        List<Integer> locationIds = getLocationIdsByCategoryId(context, categoryId);
        return getMultipleLocationsByIds(context, locationIds);
    }

    public static int getLocationsCountByCategoryId(Context context, int categoryId) {
        List<Integer> locationIds = getLocationIdsByCategoryId(context, categoryId);
        return locationIds.size();
    }

    public static List<Integer> getLimitedLocationIdsByCategoryId(Context context, int categoryId) {
        List<Integer> locationIds = new ArrayList<>();
        Cursor categoryLocationsCursor = context.getContentResolver().query(CategoryLocationsEntry.CONTENT_URI.buildUpon()
                        .appendQueryParameter(DataProvider.QUERY_PARAMETER_LIMIT,
                                String.valueOf(Helper.MAX_PER_LIST_ON_HOME_SCREEN)).build(), null,
                CategoryLocationsEntry.COLUMN_CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)}, Helper.QUERY_SORT_ORDER_ASC);

        if (categoryLocationsCursor != null) {
            if (categoryLocationsCursor.moveToFirst()) {
                do {
                    locationIds.add(categoryLocationsCursor.getInt(CategoryLocations.COLUMN_LOCATION_ID));
                } while (categoryLocationsCursor.moveToNext());
            }
            categoryLocationsCursor.close();
        } else {
            Log.d(LOG_TAG, "No locations found for category with ID = " + categoryId);
        }
        return locationIds;
    }

    public static List<Location> getLimitedLocationsByCategoryId(Context context, int categoryId) {
        List<Integer> locationIds = getLimitedLocationIdsByCategoryId(context, categoryId);
        return getMultipleLocationsByIds(context, locationIds);
    }

    public static boolean updateLocationById(Context context, Location newLocation, int locationID) {
        int updated = context.getContentResolver().update(LocationsEntry.CONTENT_URI,
                Helper.generateContentValuesFromLocationObject(newLocation),
                LocationsEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(locationID)});

        Log.i(LOG_TAG, "Location Updated " + updated);

        return updated != 0;
    }

    public static boolean deleteLocationById(Context context, int locationID) {
        int updated = context.getContentResolver().delete(LocationsEntry.CONTENT_URI,
                LocationsEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(locationID)});

        Log.i(LOG_TAG, "Category Deleted " + updated);

        return updated != 0;
    }

    /*
        LOCATION TABLE METHODS - End
     */

    /*
        CATEGORY_LOCATION TABLE METHODS - Start
     */

    public static boolean addCategoryLocations(Context context, CategoryLocations categoryLocations) {
        Cursor categoryLocationsCursor = context.getContentResolver().query(
                CategoryLocationsEntry.CONTENT_URI,
                null,
                CategoryLocationsEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(categoryLocations.getId())},
                null);

        if (categoryLocationsCursor != null && categoryLocationsCursor.moveToFirst()) {
            /*try {
                int dateCompare = Helper.convertStringToDate(categoryLocationsCursor.getString(CategoryLocations.COLUMN_UPDATED_ON))
                        .compareTo(categoryLocations.getUpdatedOn());
                if (dateCompare < 0) {*/
                    updateCategoryLocationsById(context, categoryLocations, categoryLocations.getId());
                /*}*/
                categoryLocationsCursor.close();
            /*} catch (ParseException e) {
                e.printStackTrace();
            }*/
            return false;
        } else {
            Uri insertedUri = context.getContentResolver().insert(CategoryLocationsEntry.CONTENT_URI,
                    Helper.generateContentValuesFromCategoryLocationsObject(categoryLocations));
            Log.i(LOG_TAG, "Category_Location inserted" + insertedUri);
            return true;
        }
    }

    public static boolean addMultipleCategoryLocations(Context context, List<CategoryLocations> categoryLocationsList) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        for (CategoryLocations categoryLocations : categoryLocationsList) {
            Cursor categoryLocationsCursor = context.getContentResolver().query(
                    CategoryLocationsEntry.CONTENT_URI,
                    null,
                    CategoryLocationsEntry.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(categoryLocations.getId())},
                    null);
            if (categoryLocationsCursor != null && categoryLocationsCursor.moveToFirst()) {
                /*try {
                    int dateCompare = Helper.convertStringToDate(categoryLocationsCursor.getString(CategoryLocations.COLUMN_UPDATED_ON))
                            .compareTo(categoryLocations.getUpdatedOn());
                    if (dateCompare < 0) {*/
                        updateCategoryLocationsById(context, categoryLocations, categoryLocations.getId());
//                    }
                    categoryLocationsCursor.close();
                /*} catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }*/
            } else {
                contentValuesList.add(Helper.generateContentValuesFromCategoryLocationsObject(categoryLocations));
            }
        }
        int categoryLocationsInserted = context.getContentResolver().bulkInsert(CategoryLocationsEntry.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
        Log.i(LOG_TAG, "Number of Category_Locations inserted" + categoryLocationsInserted);
        return categoryLocationsInserted > 0;
    }

    public static List<CategoryLocations> getCategoryLocationsByCategoryId(Context context, int categoryId) {
        List<CategoryLocations> categoryLocationsList = new ArrayList<>();
        Cursor categoryLocationsCursor = context.getContentResolver().query(CategoryLocationsEntry.CONTENT_URI, null,
                CategoryLocationsEntry.COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)}, Helper.QUERY_SORT_ORDER_ASC);

        if (categoryLocationsCursor != null) {
            if (categoryLocationsCursor.moveToFirst()) {
                do {
                    try {
                        categoryLocationsList.add(CategoryLocations.fromCursor(categoryLocationsCursor));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (categoryLocationsCursor.moveToNext());
            }
            categoryLocationsCursor.close();
        } else {
            Log.d(LOG_TAG, "No locations found for category with ID = " + categoryId);
        }
        return categoryLocationsList;
    }

    public static List<CategoryLocations> getAllCategoryLocations(Context context) {
        List<CategoryLocations> categoryLocationsList = new ArrayList<>();
        Cursor categoryLocationsCursor = context.getContentResolver().query(CategoryLocationsEntry.CONTENT_URI, null,
                null, null, null);

        if (categoryLocationsCursor != null) {
            if (categoryLocationsCursor.moveToFirst()) {
                do {
                    try {
                        categoryLocationsList.add(CategoryLocations.fromCursor(categoryLocationsCursor));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (categoryLocationsCursor.moveToNext());
            }
            categoryLocationsCursor.close();
        } else {
            Log.d(LOG_TAG, "No data in category_locations table.");
        }
        return categoryLocationsList;
    }

    public static boolean updateCategoryLocationsById(Context context, CategoryLocations newCategoryLocation, int categoryLocationID) {
        int updated = context.getContentResolver().update(CategoryLocationsEntry.CONTENT_URI,
                Helper.generateContentValuesFromCategoryLocationsObject(newCategoryLocation),
                CategoryLocationsEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(categoryLocationID)});

        Log.i(LOG_TAG, "Category_Location Updated with ID " + updated);

        return updated != 0;
    }

    public static boolean deleteCategoryLocationsById(Context context, int categoryLocationID) {
        int updated = context.getContentResolver().delete(CategoryLocationsEntry.CONTENT_URI,
                CategoryLocationsEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(categoryLocationID)});

        Log.i(LOG_TAG, "Category Deleted " + updated);

        return updated != 0;
    }

    /*
        CATEGORY_LOCATION TABLE METHODS - End
     */
}