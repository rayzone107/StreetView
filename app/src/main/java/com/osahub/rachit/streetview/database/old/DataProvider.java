package com.osahub.rachit.streetview.database.old;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Content Provider Class
 * Created by Rachit on 30-May-16.
 */
public class DataProvider extends ContentProvider {

    private static final String LOG_TAG = "World Tour 3D: " + DataProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DataDbHelper mOpenHelper;

    static final int CATEGORIES = 100;
    static final int CATEGORIES_WITH_ID = 101;
    static final int LOCATIONS = 200;
    static final int LOCATIONS_WITH_ID = 201;
    static final int CATEGORY_LOCATIONS = 300;
    static final int CATEGORY_LOCATIONS_WITH_ID = 301;

    public static final String QUERY_PARAMETER_LIMIT = "limit";

    private static final String sCategoriesIdSelection = DataContract.CategoriesEntry.TABLE_NAME + "." + DataContract.CategoriesEntry._ID + " = ? ";
    private static final String sLocationsIdSelection = DataContract.LocationsEntry.TABLE_NAME + "." + DataContract.LocationsEntry._ID + " = ? ";
    private static final String sCategoryLocationsIdSelection = DataContract.CategoryLocationsEntry.TABLE_NAME + "." + DataContract.CategoryLocationsEntry._ID + " = ? ";
    private static final SQLiteQueryBuilder sCategoriesQueryBuilder, sLocationsQueryBuilder, sCategoryLocationsQueryBuilder;

    static {
        sCategoriesQueryBuilder = new SQLiteQueryBuilder();
        sCategoriesQueryBuilder.setTables(DataContract.CategoriesEntry.TABLE_NAME);
        sLocationsQueryBuilder = new SQLiteQueryBuilder();
        sLocationsQueryBuilder.setTables(DataContract.LocationsEntry.TABLE_NAME);
        sCategoryLocationsQueryBuilder = new SQLiteQueryBuilder();
        sCategoryLocationsQueryBuilder.setTables(DataContract.CategoryLocationsEntry.TABLE_NAME);
    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_CATEGORIES, CATEGORIES);
        matcher.addURI(authority, DataContract.PATH_CATEGORIES + "/*", CATEGORIES_WITH_ID);

        matcher.addURI(authority, DataContract.PATH_LOCATIONS, LOCATIONS);
        matcher.addURI(authority, DataContract.PATH_LOCATIONS + "/*", LOCATIONS_WITH_ID);

        matcher.addURI(authority, DataContract.PATH_CATEGORY_LOCATIONS, CATEGORY_LOCATIONS);
        matcher.addURI(authority, DataContract.PATH_CATEGORY_LOCATIONS + "/*", CATEGORY_LOCATIONS_WITH_ID);

        return matcher;
    }

    private Cursor getCategoriesById(Uri uri, String[] projection, String sortOrder) {
        String categorySetting = DataContract.CategoriesEntry.getIdFromUri(uri);

        return sCategoriesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sCategoriesIdSelection,
                new String[]{categorySetting},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getLocationsById(Uri uri, String[] projection, String sortOrder) {
        String locationSetting = DataContract.LocationsEntry.getIdFromUri(uri);

        return sLocationsQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sLocationsIdSelection,
                new String[]{locationSetting},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCategoryLocationsById(Uri uri, String[] projection, String sortOrder) {
        String categoryLocationsSetting = DataContract.CategoryLocationsEntry.getIdFromUri(uri);

        return sCategoryLocationsQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sCategoryLocationsIdSelection,
                new String[]{categoryLocationsSetting},
                null,
                null,
                sortOrder
        );
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DataDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CATEGORIES:
                return DataContract.CategoriesEntry.CONTENT_TYPE;
            case CATEGORIES_WITH_ID:
                return DataContract.CategoriesEntry.CONTENT_ITEM_TYPE;
            case LOCATIONS:
                return DataContract.LocationsEntry.CONTENT_TYPE;
            case LOCATIONS_WITH_ID:
                return DataContract.LocationsEntry.CONTENT_ITEM_TYPE;
            case CATEGORY_LOCATIONS:
                return DataContract.CategoryLocationsEntry.CONTENT_TYPE;
            case CATEGORY_LOCATIONS_WITH_ID:
                return DataContract.CategoryLocationsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        String limit = uri.getQueryParameter(QUERY_PARAMETER_LIMIT);
        switch (sUriMatcher.match(uri)) {
            // "categories/#"
            case CATEGORIES_WITH_ID: {
                retCursor = getCategoriesById(uri, projection, sortOrder);
                break;
            }
            // categories
            case CATEGORIES: {
                if (limit == null) {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DataContract.CategoriesEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder
                    );
                } else {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DataContract.CategoriesEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder,
                            limit
                    );
                }

                break;
            }
            // "locations/#"
            case LOCATIONS_WITH_ID: {
                retCursor = getLocationsById(uri, projection, sortOrder);
                break;
            }
            // locations
            case LOCATIONS: {
                if (limit == null) {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DataContract.LocationsEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder
                    );
                } else {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DataContract.LocationsEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder,
                            limit
                    );
                }
                break;
            }
            // "category_locations/#"
            case CATEGORY_LOCATIONS_WITH_ID: {
                retCursor = getCategoryLocationsById(uri, projection, sortOrder);
                break;
            }
            // category_locations
            case CATEGORY_LOCATIONS: {
                if (limit == null) {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DataContract.CategoryLocationsEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder
                    );
                } else {
                    retCursor = mOpenHelper.getReadableDatabase().query(
                            DataContract.CategoryLocationsEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder,
                            limit
                    );
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case CATEGORIES: {
                long _id = db.insert(DataContract.CategoriesEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DataContract.CategoriesEntry.buildCategoriesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LOCATIONS: {
                long _id = db.insert(DataContract.LocationsEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DataContract.LocationsEntry.buildLocationsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CATEGORY_LOCATIONS: {
                long _id = db.insert(DataContract.CategoryLocationsEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DataContract.CategoryLocationsEntry.buildCategoryLocationsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // if selection == null, it deletes all rows
        if (null == selection) selection = "1";
        switch (match) {
            case CATEGORIES:
                rowsDeleted = db.delete(
                        DataContract.CategoriesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case LOCATIONS:
                rowsDeleted = db.delete(
                        DataContract.LocationsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORY_LOCATIONS:
                rowsDeleted = db.delete(
                        DataContract.CategoryLocationsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case CATEGORIES:
                rowsUpdated = db.update(DataContract.CategoriesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CATEGORIES_WITH_ID:
                rowsUpdated = db.update(DataContract.CategoriesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case LOCATIONS:
                rowsUpdated = db.update(DataContract.LocationsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case LOCATIONS_WITH_ID:
                rowsUpdated = db.update(DataContract.LocationsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CATEGORY_LOCATIONS:
                rowsUpdated = db.update(DataContract.CategoryLocationsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CATEGORY_LOCATIONS_WITH_ID:
                rowsUpdated = db.update(DataContract.CategoryLocationsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case CATEGORIES:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DataContract.CategoriesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case LOCATIONS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DataContract.LocationsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case CATEGORY_LOCATIONS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DataContract.CategoryLocationsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

}
