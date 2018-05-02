package com.osahub.rachit.streetview.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.Date;

/**
 * Created by Rachit Goyal on 28/04/18
 */

@Table(name = "LocationImage")
public class LocationImage {

    public static final String COLUMN_LOCATION_IMAGE_ID = "locationImageId";
    public static final String COLUMN_LOCATION_ID = "locationId";
    public static final String COLUMN_IMAGE_URL = "imageUrl";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";

    private transient Long id;

    @SerializedName("id")
    @Column(name = COLUMN_LOCATION_IMAGE_ID, unique = true)
    private int locationImageId;
    @SerializedName("location_id")
    @Column(name = COLUMN_LOCATION_ID)
    private int locationId;
    @SerializedName("image_url")
    @Column(name = COLUMN_IMAGE_URL)
    private String imageUrl;
    @SerializedName("created_on")
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
    @SerializedName("updated_on")
    @Column(name = COLUMN_UPDATED_ON)
    private Date updatedOn;

    public LocationImage() {
    }

    public LocationImage(int locationId, String imageUrl) {
        this.locationId = locationId;
        this.imageUrl = imageUrl;
    }

    public LocationImage(int locationImageId, int locationId, String imageUrl, Date createdOn, Date updatedOn) {
        this.locationImageId = locationImageId;
        this.locationId = locationId;
        this.imageUrl = imageUrl;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public void updateObject(LocationImage locationImage) {
        this.locationImageId = locationImage.getLocationImageId();
        this.locationId = locationImage.getLocationId();
        this.imageUrl = locationImage.getImageUrl();
        this.createdOn = locationImage.getCreatedOn();
        this.updatedOn = locationImage.getUpdatedOn();
    }

    public int getLocationImageId() {
        return locationImageId;
    }

    public void setLocationImageId(int locationImageId) {
        this.locationImageId = locationImageId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
