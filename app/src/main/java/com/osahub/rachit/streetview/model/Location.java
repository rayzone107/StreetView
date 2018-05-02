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
 * Location Bean Class
 * Created by Rachit on 22-Apr-16.
 */
@Table(name = "Locations")
public class Location {

    public static final String COLUMN_LOCATION_ID = "locationId";
    public static final String COLUMN_LOCATION_NAME = "locationName";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DESCRIPTION_SMALL = "descriptionSmall";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_BUILT_IN = "builtIn";
    public static final String COLUMN_BUILT_BY = "builtBy";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_THUMBNAIL_PATH = "thumbnailPath";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";
    public static final String COLUMN_IS_FAVOURITE = "isFavourite";

    private transient Long id;

    @SerializedName("id")
    @Column(name = COLUMN_LOCATION_ID, unique = true)
    private int locationId;
    @SerializedName("name")
    @Column(name = COLUMN_LOCATION_NAME)
    private String locationName;
    @SerializedName("description")
    @Column(name = COLUMN_DESCRIPTION)
    private String description;
    @SerializedName("description_small")
    @Column(name = COLUMN_DESCRIPTION_SMALL)
    private String descriptionSmall;
    @SerializedName("city")
    @Column(name = COLUMN_CITY)
    private String city;
    @SerializedName("state")
    @Column(name = COLUMN_STATE)
    private String state;
    @SerializedName("country")
    @Column(name = COLUMN_COUNTRY)
    private String country;
    @SerializedName("built_in")
    @Column(name = COLUMN_BUILT_IN)
    private String builtIn;
    @SerializedName("built_by")
    @Column(name = COLUMN_BUILT_BY)
    private String builtBy;
    @SerializedName("latitude")
    @Column(name = COLUMN_LATITUDE)
    private double latitude;
    @SerializedName("longitude")
    @Column(name = COLUMN_LONGITUDE)
    private double longitude;
    @SerializedName("thumbnail_path")
    @Column(name = COLUMN_THUMBNAIL_PATH)
    private String thumbnailPath;
    @SerializedName("created_on")
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
    @SerializedName("updated_on")
    @Column(name = COLUMN_UPDATED_ON)
    private Date updatedOn;
    @Column(name = COLUMN_IS_FAVOURITE)
    private boolean isFavourite;

    public Location() {
    }

    public Location(String name) {
        this.locationName = name;
    }

    public Location(int locationId, String locationName, String description, String descriptionSmall, String city, String state, String country,
                    String builtIn, String builtBy, double latitude, double longitude, String thumbnailPath, Date createdOn, Date updatedOn) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.description = description;
        this.descriptionSmall = descriptionSmall;
        this.city = city;
        this.state = state;
        this.country = country;
        this.builtIn = builtIn;
        this.builtBy = builtBy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbnailPath = thumbnailPath;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public static Location fromJson(JSONObject jsonObject) throws JSONException, ParseException {
        return new Location(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("description"),
                jsonObject.getString("description_small"),
                jsonObject.getString("city"),
                jsonObject.getString("state"),
                jsonObject.getString("country"),
                jsonObject.getString("built_in"),
                jsonObject.getString("built_by"),
                jsonObject.getDouble("latitude"),
                jsonObject.getDouble("longitude"),
                jsonObject.getString("thumbnail_path"),
                Helper.convertStringToDate(jsonObject.getString("created_on")),
                Helper.convertStringToDate(jsonObject.getString("updated_on")));
    }

    public void updateObject(Location location) {
        this.locationId = location.getLocationId();
        this.locationName = location.getLocationName();
        this.description = location.getDescription();
        this.descriptionSmall = location.getDescriptionSmall();
        this.city = location.getCity();
        this.state = location.getState();
        this.country = location.getCountry();
        this.builtIn = location.getBuiltIn();
        this.builtBy = location.getBuiltBy();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.thumbnailPath = location.getThumbnailPath();
        this.createdOn = location.getCreatedOn();
        this.updatedOn = location.getUpdatedOn();
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionSmall() {
        return descriptionSmall;
    }

    public void setDescriptionSmall(String descriptionSmall) {
        this.descriptionSmall = descriptionSmall;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(String builtIn) {
        this.builtIn = builtIn;
    }

    public String getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(String builtBy) {
        this.builtBy = builtBy;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}