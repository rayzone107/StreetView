package com.osahub.rachit.streetview.database;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.osahub.rachit.streetview.model.CategoryLocation;

import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class CategoryLocationDatabaseHelper implements DatabaseContract.CategoryLocationContract {
    @Override
    public void saveCategoryLocation(CategoryLocation categoryLocation) {
        SugarRecord.save(categoryLocation);
    }

    @Override
    public void addMultipleCategoryLocations(List<CategoryLocation> categoryLocationList) {
        SugarRecord.saveInTx(categoryLocationList);
    }

    @Override
    public void updateOrInsertMultipleCategoryLocations(List<CategoryLocation> categoryLocationList) {
        for (CategoryLocation categoryLocation : categoryLocationList) {
            CategoryLocation storedCategoryLocation = Select.from(CategoryLocation.class).where(
                    Condition.prop(CategoryLocation.COLUMN_CATEGORY_LOCATION_ID).eq(
                            categoryLocation.getCategoryLocationId())).first();
            if (storedCategoryLocation != null) {
                storedCategoryLocation.updateObject(categoryLocation);
                saveCategoryLocation(storedCategoryLocation);
            } else {
                saveCategoryLocation(categoryLocation);
            }
        }
    }

    @Override
    public int getCategoryLocationCount() {
        return Select.from(CategoryLocation.class).list().size();
    }

    @Override
    public List<CategoryLocation> getCategoryLocationByCategoryId(int categoryId) {
        return Select.from(CategoryLocation.class)
                .where(Condition.prop(CategoryLocation.COLUMN_CATEGORY_ID).eq(categoryId)).list();
    }

    @Override
    public List<CategoryLocation> getAllCategoryLocations() {
        return Select.from(CategoryLocation.class).list();
    }

    @Override
    public void updateCategoryLocationById(int categoryLocationId, CategoryLocation categoryLocation) {
        CategoryLocation originalCategoryLocation = Select.from(CategoryLocation.class).
                where(Condition.prop(CategoryLocation.COLUMN_CATEGORY_LOCATION_ID).eq(categoryLocationId)).first();
        originalCategoryLocation.updateObject(categoryLocation);
        saveCategoryLocation(originalCategoryLocation);
    }

    @Override
    public void deleteCategoryLocationById(int categoryLocationId) {
        CategoryLocation categoryLocation = Select.from(CategoryLocation.class).
                where(Condition.prop(CategoryLocation.COLUMN_CATEGORY_LOCATION_ID).eq(categoryLocationId)).first();
        SugarRecord.delete(categoryLocation);
    }

    @Override
    public void deleteAllCategoryLocations() {
        SugarRecord.deleteAll(CategoryLocation.class);
    }
}
