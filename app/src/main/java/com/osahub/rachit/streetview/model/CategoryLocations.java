package com.osahub.rachit.streetview.model;

import android.database.Cursor;

import com.osahub.rachit.streetview.misc.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * CategoryLocation join bean class.
 * Created by Rachit on 30-May-16.
 */
public class CategoryLocations {

    public static final int COLUMN_ID = 1;
    public static final int COLUMN_CATEGORY_ID = 2;
    public static final int COLUMN_LOCATION_ID = 3;
    public static final int COLUMN_ORDER = 4;
    public static final int COLUMN_CREATED_ON = 5;
    public static final int COLUMN_UPDATED_ON = 6;

    int id;
    int categoryId;
    int locationId;
    int order;
    Date createdOn;
    Date updatedOn;

    public CategoryLocations() {
    }

    public CategoryLocations(int id, int categoryId, int locationId, int order, Date createdOn, Date updatedOn) {
        this.id = id;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.order = order;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public static CategoryLocations fromCursor(Cursor cursor) throws ParseException {
        return new CategoryLocations(
                cursor.getInt(COLUMN_ID),
                cursor.getInt(COLUMN_CATEGORY_ID),
                cursor.getInt(COLUMN_LOCATION_ID),
                cursor.getInt(COLUMN_ORDER),
                Helper.convertStringToDate(cursor.getString(COLUMN_CREATED_ON)),
                Helper.convertStringToDate(cursor.getString(COLUMN_UPDATED_ON)));
    }

    public static CategoryLocations fromJson(JSONObject jsonObject) throws JSONException, ParseException {
        return new CategoryLocations(
                jsonObject.getInt("id"),
                jsonObject.getInt("category_id"),
                jsonObject.getInt("location_id"),
                jsonObject.getInt("order"),
                Helper.convertStringToDate(jsonObject.getString("created_on")),
                Helper.convertStringToDate(jsonObject.getString("updated_on")));
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
