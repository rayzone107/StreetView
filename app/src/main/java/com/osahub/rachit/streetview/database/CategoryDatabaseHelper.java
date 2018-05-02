package com.osahub.rachit.streetview.database;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.osahub.rachit.streetview.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class CategoryDatabaseHelper implements DatabaseContract.CategoryContract {

    @Override
    public void saveCategory(Category category) {
        SugarRecord.save(category);
    }

    @Override
    public void addMultipleCategories(List<Category> categories) {
        SugarRecord.saveInTx(categories);
    }

    @Override
    public void updateOrInsertCategory(Category category) {
        Category storedCategory = Select.from(Category.class)
                .where(Condition.prop(Category.COLUMN_CATEGORY_ID)
                        .eq(category.getCategoryId())).first();
        if (storedCategory != null) {
            storedCategory.updateObject(category);
            saveCategory(storedCategory);
        } else {
            saveCategory(category);
        }
    }

    @Override
    public void updateOrInsertMultipleCategories(List<Category> categories) {
        for (Category category : categories) {
            updateOrInsertCategory(category);
        }
    }

    @Override
    public int getCategoryCount() {
        return Select.from(Category.class).list().size();
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return Select.from(Category.class).where(Condition.prop(Category.COLUMN_CATEGORY_ID).eq(categoryId)).first();
    }

    @Override
    public Category getCategoryByName(String name) {
        return Select.from(Category.class).where(Condition.prop(Category.COLUMN_NAME).eq(name)).first();
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
        return Select.from(Category.class).list();
    }

    @Override
    public List<Category> getAllCategoriesThatContainString(String searchValue) {
        return Select.from(Category.class).where(Condition.prop(Category.COLUMN_NAME).like("%" + searchValue + "%")).list();
    }

    @Override
    public void updateCategoryById(int categoryId, Category category) {
        Category originalCategory = Select.from(Category.class).where(Condition.prop(Category.COLUMN_CATEGORY_ID).eq(categoryId)).first();
        originalCategory.updateObject(category);
        saveCategory(originalCategory);
    }

    @Override
    public void deleteCategoryById(int categoryId) {
        Category category = Select.from(Category.class).where(Condition.prop(Category.COLUMN_CATEGORY_ID).eq(categoryId)).first();
        SugarRecord.delete(category);
    }

    @Override
    public void deleteAllCategories() {
        SugarRecord.deleteAll(Category.class);
    }
}
