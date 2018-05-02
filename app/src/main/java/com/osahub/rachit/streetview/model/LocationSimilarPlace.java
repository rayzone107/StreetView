package com.osahub.rachit.streetview.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.Date;

/**
 * Created by Rachit Goyal on 29/04/18
 */

@Table(name = "LocationSimilarPlace")
public class LocationSimilarPlace {

    public static final String COLUMN_LOCATION_SIMILAR_PLACE_ID = "locationSimilarPlaceId";
    public static final String COLUMN_LOCATION_ID = "locationId";
    public static final String COLUMN_SIMILAR_LOCATION_ID = "similarLocationId";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";

    private transient Long id;

    @SerializedName("id")
    @Column(name = COLUMN_LOCATION_SIMILAR_PLACE_ID, unique = true)
    private int locationSimilarPlaceId;
    @SerializedName("location_id")
    @Column(name = COLUMN_LOCATION_ID)
    private int locationId;
    @SerializedName("similar_location_id")
    @Column(name = COLUMN_SIMILAR_LOCATION_ID)
    private int similarLocationId;
    @SerializedName("created_on")
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
    @SerializedName("updated_on")
    @Column(name = COLUMN_UPDATED_ON)
    private Date updatedOn;

    public LocationSimilarPlace() {
    }

    public LocationSimilarPlace(int locationSimilarPlaceId, int locationId, int similarLocationId, Date createdOn, Date updatedOn) {
        this.locationSimilarPlaceId = locationSimilarPlaceId;
        this.locationId = locationId;
        this.similarLocationId = similarLocationId;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public void updateObject(LocationSimilarPlace locationSimilarPlace) {
        this.locationSimilarPlaceId = locationSimilarPlace.getLocationSimilarPlaceId();
        this.locationId = locationSimilarPlace.getLocationId();
        this.similarLocationId = locationSimilarPlace.getSimilarLocationId();
        this.createdOn = locationSimilarPlace.getCreatedOn();
        this.updatedOn = locationSimilarPlace.getUpdatedOn();
    }

    public int getLocationSimilarPlaceId() {
        return locationSimilarPlaceId;
    }

    public void setLocationSimilarPlaceId(int locationSimilarPlaceId) {
        this.locationSimilarPlaceId = locationSimilarPlaceId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getSimilarLocationId() {
        return similarLocationId;
    }

    public void setSimilarLocationId(int similarLocationId) {
        this.similarLocationId = similarLocationId;
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
