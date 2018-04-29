package com.osahub.rachit.streetview.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Rachit Goyal on 29/04/18
 */

@Table(name = "LocationSimilarPlaces")
public class LocationSimilarPlaces extends Model {

    public static final String COLUMN_LOCATION_SIMILAR_PLACE_ID = "locationSimilarPlaceId";
    public static final String COLUMN_LOCATION_ID = "locationId";
    public static final String COLUMN_SIMILAR_LOCATION_ID = "similarLocationId";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";

    @SerializedName("id")
    @Column(name = COLUMN_LOCATION_SIMILAR_PLACE_ID, unique = true)
    private int locationImageId;
    @SerializedName("location_id")
    @Column(name = COLUMN_LOCATION_ID)
    private int locationId;
    @SerializedName("similar_location_id")
    @Column(name = COLUMN_SIMILAR_LOCATION_ID)
    private String imageUrl;
    @SerializedName("created_on")
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
    @SerializedName("updated_on")
    @Column(name = COLUMN_UPDATED_ON)
    private Date updatedOn;
}
