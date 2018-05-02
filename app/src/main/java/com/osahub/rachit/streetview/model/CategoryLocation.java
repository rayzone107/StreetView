package com.osahub.rachit.streetview.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.osahub.rachit.streetview.utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * CategoryLocation join bean class.
 * Created by Rachit on 30-May-16.
 */
@Table(name = "CategoryLocation")
public class CategoryLocation {

    public static final String COLUMN_CATEGORY_LOCATION_ID = "categoryLocationId";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_LOCATION_ID = "locationId";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";

    private transient Long id;

    @SerializedName("id")
    @Column(name = COLUMN_CATEGORY_LOCATION_ID)
    private int categoryLocationId;
    @SerializedName("category_id")
    @Column(name = COLUMN_CATEGORY_ID)
    private int categoryId;
    @SerializedName("location_id")
    @Column(name = COLUMN_LOCATION_ID)
    private int locationId;
    @SerializedName("position")
    @Column(name = COLUMN_POSITION)
    private int position;
    @SerializedName("created_on")
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
    @SerializedName("updated_on")
    @Column(name = COLUMN_UPDATED_ON)
    private Date updatedOn;

    public CategoryLocation() {
    }

    public CategoryLocation(int id, int categoryId, int locationId, int position, Date createdOn, Date updatedOn) {
        this.categoryLocationId = id;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.position = position;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public void updateObject(CategoryLocation categoryLocation) {
        this.categoryLocationId = categoryLocation.getCategoryLocationId();
        this.categoryId = categoryLocation.getCategoryId();
        this.locationId = categoryLocation.getLocationId();
        this.position = categoryLocation.getPosition();
        this.createdOn = categoryLocation.getCreatedOn();
        this.updatedOn = categoryLocation.getUpdatedOn();
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
