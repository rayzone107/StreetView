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
 * Category Bean Class
 * Created by Rachit on 23-Apr-16.
 */
@Table(name = "Categories")
public class Category extends Model {

    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_CREATED_ON = "createdOn";
    public static final String COLUMN_UPDATED_ON = "updatedOn";

    @Column(name = COLUMN_CATEGORY_ID)
    private int categoryId;
    @Column(name = COLUMN_NAME)
    private String name;
    @Column(name = COLUMN_TYPE)
    private String type;
    @Column(name = COLUMN_POSITION)
    private int position;
    @Column(name = COLUMN_CREATED_ON)
    private Date createdOn;
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

    public static Category fromJson(JSONObject jsonObject) throws JSONException, ParseException {
        return new Category(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("type"),
                jsonObject.getInt("order"),
                Helper.convertStringToDate(jsonObject.getString("created")),
                Helper.convertStringToDate(jsonObject.getString("updated")));
    }

    public void updateObject(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.type = category.getType();
        this.position = category.getPosition();
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
