package com.osahub.rachit.streetview.database;

import com.activeandroid.query.Select;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class CategoryDatabaseHelper implements DatabaseContract.CategoryContract {

    @Override
    public void addCategory(Category category) {
        category.save();
    }

    @Override
    public void addMultipleCategories(List<Category> categories) {
        for (Category category : categories) {
            category.save();
        }
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return new Select().from(Category.class).where(Category.COLUMN_CATEGORY_ID + " = ? ", categoryId).executeSingle();
    }

    @Override
    public Category getCategoryByName(String name) {
        return new Select().from(Category.class).where(Category.COLUMN_NAME + " = ? ", name).executeSingle();
    }

    @Override
    public List<Category> getCategoriesByIdsList(List<Integer> categoryIds) {
        List<Category> categories = new ArrayList<>();
        for (Integer id : categoryIds) {
            categories.add(getCategoryById(id));
        }
        return categories;
    }

    @Override
    public List<Category> getCategoriesByNamesList(List<String> names) {
        List<Category> categories = new ArrayList<>();
        for (String name : names) {
            categories.add(getCategoryByName(name));
        }
        return categories;
    }

    @Override
    public List<Category> getAllCategories() {
        return new Select().from(Category.class).execute();
    }

    @Override
    public void updateCategoryById(int categoryId, Category category) {
        Category originalCategory = new Select().from(Category.class).where(Category.COLUMN_CATEGORY_ID + " = ? ", categoryId).executeSingle();
        originalCategory.updateObject(category);
        originalCategory.save();
    }

    @Override
    public void deleteCategoryById(int categoryId) {
        Category category = new Select().from(Category.class).where(Category.COLUMN_CATEGORY_ID + " = ? ", categoryId).executeSingle();
        category.delete();
    }

    @Override
    public void deleteAllCategories() {
        Helper.clearTable(Category.class);
    }
}
