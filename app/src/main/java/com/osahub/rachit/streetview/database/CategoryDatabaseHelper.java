package com.osahub.rachit.streetview.database;

import com.activeandroid.query.Select;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.Category;

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
        if (categoryIds.isEmpty()) {
            return null;
        } else {
            String whereClause = " " + Category.COLUMN_CATEGORY_ID + " IN (";
            Integer[] whereArgs = new Integer[categoryIds.size()];
            for (int i = 0; i < categoryIds.size(); i++) {
                Integer id = categoryIds.get(i);
                whereClause = whereClause.concat("?,");
                whereArgs[i] = id;
            }
            whereClause = whereClause.substring(0, whereClause.length() - 1).concat(") ");
            return new Select().from(Category.class).where(whereClause, whereArgs).execute();
        }
    }

    @Override
    public List<Category> getCategoriesByNamesList(List<String> names) {
        if (names.isEmpty()) {
            return null;
        } else {
            String whereClause = " " + Category.COLUMN_NAME + " IN (";
            String[] whereArgs = new String[names.size()];
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                whereClause = whereClause.concat("?,");
                whereArgs[i] = name;
            }
            whereClause = whereClause.substring(0, whereClause.length() - 1).concat(") ");
            return new Select().from(Category.class).where(whereClause, whereArgs).execute();
        }
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
