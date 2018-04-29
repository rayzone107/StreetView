package com.osahub.rachit.streetview.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.osahub.rachit.streetview.misc.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * CategoryLocation join bean class.
 * Created by Rachit on 30-May-16.
 */
@Table(name = "CategoryLocations")
public class CategoryLocations extends Model {

    public static final String COLUMN_CATEGORY_LOCATION_ID = "categoryLocationId";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_LOCATION_ID = "locationId";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";

    @Column(name = COLUMN_CATEGORY_LOCATION_ID)
    private int categoryLocationId;
    @Column(name = COLUMN_CATEGORY_ID)
    private int categoryId;
    @Column(name = COLUMN_LOCATION_ID)
    private int locationId;
    @Column(name = COLUMN_POSITION)
    private int position;
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
    @Column(name = COLUMN_UPDATED_ON)
    private Date updatedOn;

    public CategoryLocations() {
    }

    public CategoryLocations(int id, int categoryId, int locationId, int position, Date createdOn, Date updatedOn) {
        this.categoryLocationId = id;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.position = position;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public static CategoryLocations fromJson(JSONObject jsonObject) throws JSONException, ParseException {
        return new CategoryLocations(
                jsonObject.getInt("id"),
                jsonObject.getInt("category_id"),
                jsonObject.getInt("location_id"),
                jsonObject.getInt("position"),
                Helper.convertStringToDate(jsonObject.getString("created_on")),
                Helper.convertStringToDate(jsonObject.getString("updated_on")));
    }

    public void updateObject(CategoryLocations categoryLocations) {
        this.categoryLocationId = categoryLocations.getCategoryLocationId();
        this.categoryId = categoryLocations.getCategoryId();
        this.locationId = categoryLocations.getLocationId();
        this.position = categoryLocations.getPosition();
        this.createdOn = categoryLocations.getCreatedOn();
        this.updatedOn = categoryLocations.getUpdatedOn();
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
