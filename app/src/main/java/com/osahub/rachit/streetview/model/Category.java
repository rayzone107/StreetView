package com.osahub.rachit.streetview.model;

import com.orm.SugarRecord;
import com.osahub.rachit.streetview.misc.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Category Bean Class
 * Created by Rachit on 23-Apr-16.
 */
public class Category extends SugarRecord<Category> {

    public static final String COLUMN_CATEGORY_ID = "CATEGORYID";
    public static final String COLUMN_CATEGORY_NAME = "NAME";

    private int categoryId;
    private String name;
    private String type;
    private int order;
    private Date createdOn;
    private Date updatedOn;

    public Category() {
    }

    public Category(int id, String name, String type, int order, Date createdOn, Date updatedOn) {
        this.categoryId = id;
        this.name = name;
        this.type = type;
        this.order = order;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;

    }

    /*public static Category fromCursor(Cursor cursor) throws ParseException {
        return new Category(
                cursor.getInt(COLUMN_ID),
                cursor.getString(COLUMN_NAME),
                cursor.getString(COLUMN_TYPE),
                cursor.getInt(COLUMN_ORDER),
                Helper.convertStringToDate(cursor.getString(COLUMN_CREATED_ON)),
                Helper.convertStringToDate(cursor.getString(COLUMN_UPDATED_ON)));
    }*/

    public static Category fromJson(JSONObject jsonObject) throws JSONException, ParseException {
        return new Category(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("type"),
                jsonObject.getInt("order"),
                Helper.convertStringToDate(jsonObject.getString("created")),
                Helper.convertStringToDate(jsonObject.getString("updated")));
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
