package com.osahub.rachit.streetview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.osahub.rachit.streetview.utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Category Bean Class
 * Created by Rachit on 23-Apr-16.
 */
@Table(name = "Categories")
public class Category {

    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_BACKGROUND_URL = "backgroundURL";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";

    private transient Long id;

    @Expose
    @SerializedName("id")
    @Column(name = COLUMN_CATEGORY_ID)
    private int categoryId;
    @Expose
    @SerializedName("name")
    @Column(name = COLUMN_NAME)
    private String name;
    @Expose
    @SerializedName("type")
    @Column(name = COLUMN_TYPE)
    private String type;
    @Expose
    @SerializedName("position")
    @Column(name = COLUMN_POSITION)
    private int position;
    @Expose
    @SerializedName("background_url")
    @Column(name = COLUMN_BACKGROUND_URL)
    private String backgroundUrl;
    @Expose
    @SerializedName("created_on")
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
    @Expose
    @SerializedName("updated_on")
    @Column(name = COLUMN_UPDATED_ON)
    private Date updatedOn;

    public Category() {
    }

    public Category(int id, String name, String type, int position, Date createdOn, Date updatedOn) {
        this.categoryId = id;
        this.name = name;
        this.type = type;
        this.position = position;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Category(int categoryId, String name, String type, int position, String backgroundUrl, Date createdOn, Date updatedOn) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.position = position;
        this.backgroundUrl = backgroundUrl;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public void updateObject(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.type = category.getType();
        this.position = category.getPosition();
        this.backgroundUrl = category.getBackgroundUrl();
        this.createdOn = category.getCreatedOn();
        this.updatedOn = category.getUpdatedOn();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
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
