package com.osahub.rachit.streetview.model;

import com.orm.SugarRecord;
import com.osahub.rachit.streetview.misc.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * CategoryLocation join bean class.
 * Created by Rachit on 30-May-16.
 */
public class CategoryLocations extends SugarRecord<CategoryLocations> {

    public static final String COLUMN_CATEGORY_LOCATION_ID = "CATEGORYLOCATIONID";
    public static final String COLUMN_CATEGORY_ID = "CATEGORYID";
    public static final String COLUMN_LOCATION_ID = "LOCATIONID";

    private int categoryLocationId;
    private int categoryId;
    private int locationId;
    private int order;
    private Date createdOn;
    private Date updatedOn;

    public CategoryLocations() {
    }

    public CategoryLocations(int id, int categoryId, int locationId, int order, Date createdOn, Date updatedOn) {
        this.categoryLocationId = id;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.order = order;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
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

    public int getCategoryLocationId() {
        return categoryLocationId;
    }

    public void setCategoryLocationId(int categoryLocationId) {
        this.categoryLocationId = categoryLocationId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
