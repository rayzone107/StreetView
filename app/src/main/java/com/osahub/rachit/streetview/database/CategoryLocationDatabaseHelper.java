package com.osahub.rachit.streetview.database;

import com.activeandroid.query.Select;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.CategoryLocations;

import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class CategoryLocationDatabaseHelper implements DatabaseContract.CategoryLocationContract {
    @Override
    public void addCategoryLocations(CategoryLocations categoryLocations) {
        categoryLocations.save();
    }

    @Override
    public void addMultipleCategoryLocations(List<CategoryLocations> categoryLocationsList) {
        for (CategoryLocations categoryLocations : categoryLocationsList) {
            categoryLocations.save();
        }
    }

    @Override
    public List<CategoryLocations> getCategoryLocationByCategoryId(int categoryId) {
        return new Select().from(CategoryLocations.class).where(CategoryLocations.COLUMN_CATEGORY_ID + " = ? ", categoryId).execute();
    }

    @Override
    public List<CategoryLocations> getAllCategoryLocations() {
        return new Select().from(CategoryLocations.class).execute();
    }

    @Override
    public void updateCategoryLocationById(int categoryLocationId, CategoryLocations categoryLocations) {
        CategoryLocations originalCategoryLocations = new Select().from(CategoryLocations.class).
                where(CategoryLocations.COLUMN_CATEGORY_LOCATION_ID + " = ? ", categoryLocationId).executeSingle();
        originalCategoryLocations.updateObject(categoryLocations);
        originalCategoryLocations.save();
    }

    @Override
    public void deleteCategoryLocationById(int categoryLocationId) {
        CategoryLocations categoryLocations = new Select().from(CategoryLocations.class).
                where(CategoryLocations.COLUMN_CATEGORY_LOCATION_ID + " = ? ", categoryLocationId).executeSingle();
        categoryLocations.delete();
    }

    @Override
    public void deleteAllCategoryLocations() {
        Helper.clearTable(CategoryLocations.class);
    }
}
