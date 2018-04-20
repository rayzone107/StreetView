package com.osahub.rachit.streetview.database;

import com.orm.query.Condition;
import com.orm.query.Select;
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
        return Select.from(Category.class).where(Condition.prop(Category.COLUMN_CATEGORY_ID).eq(categoryId)).list().get(0);
    }

    @Override
    public Category getCategoryByName(String name) {
        return Select.from(Category.class).where(Condition.prop(Category.COLUMN_CATEGORY_NAME).eq(name)).list().get(0);
    }

    @Override
    public List<Category> getCategoriesByIdsList(List<Integer> categoryIds) {
        String whereClause = " " + Category.COLUMN_CATEGORY_ID + " IN (";
        String whereArgs = "";
        for (Integer id : categoryIds) {
            whereClause = whereClause.concat("?,");
            whereArgs = whereArgs.concat(id + ",");
        }
        whereClause = whereClause.substring(0, whereClause.length() - 1).concat(") ");
        whereArgs = whereArgs.substring(0, whereArgs.length() - 1);

        return Category.find(Category.class, whereClause, whereArgs);
    }

    @Override
    public List<Category> getCategoriesByNamesList(List<String> names) {
        String whereClause = " " + Category.COLUMN_CATEGORY_NAME + " IN (";
        String whereArgs = "";
        for (String name : names) {
            whereClause = whereClause.concat("?,");
            whereArgs = whereArgs.concat(name + ",");
        }
        whereClause = whereClause.substring(0, whereClause.length() - 1).concat(") ");
        whereArgs = whereArgs.substring(0, whereArgs.length() - 1);

        return Category.find(Category.class, whereClause, whereArgs);
    }

    @Override
    public List<Category> getAllCategories() {
        return Select.from(Category.class).list();
    }

    @Override
    public void updateCategoryById(int categoryId, Category category) {
        Category originalCategory = Select.from(Category.class).where(Condition.prop(Category.COLUMN_CATEGORY_ID).eq(categoryId)).list().get(0);
        long id = originalCategory.getId();
        originalCategory = category;
        originalCategory.setId(id);
        originalCategory.save();
    }

    @Override
    public void deleteCategoryById(int categoryId) {
        Category category = Select.from(Category.class).where(Condition.prop(Category.COLUMN_CATEGORY_ID).eq(categoryId)).list().get(0);
        category.delete();
    }

    @Override
    public void deleteAllCategories() {
        Category.deleteAll(Category.class);
    }
}
