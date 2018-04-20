package com.osahub.rachit.streetview.database;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.osahub.rachit.streetview.model.Category;
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
        return Select.from(CategoryLocations.class).where(Condition.prop(CategoryLocations.COLUMN_CATEGORY_ID).eq(categoryId)).list();
    }

    @Override
    public List<CategoryLocations> getAllCategoryLocations() {
        return Select.from(CategoryLocations.class).list();
    }

    @Override
    public void updateCategoryLocationById(int categoryLocationId, CategoryLocations categoryLocations) {
        CategoryLocations originalCategoryLocations = Select.from(CategoryLocations.class).
                where(Condition.prop(CategoryLocations.COLUMN_CATEGORY_LOCATION_ID).eq(categoryLocationId)).list().get(0);
        long id = originalCategoryLocations.getId();
        originalCategoryLocations = categoryLocations;
        originalCategoryLocations.setId(id);
        originalCategoryLocations.save();
    }

    @Override
    public void deleteCategoryLocationById(int categoryLocationId) {
        CategoryLocations categoryLocations = Select.from(CategoryLocations.class).
                where(Condition.prop(CategoryLocations.COLUMN_CATEGORY_LOCATION_ID).eq(categoryLocationId)).list().get(0);
        categoryLocations.delete();
    }

    @Override
    public void deleteAllCategoryLocations() {
        CategoryLocations.deleteAll(CategoryLocations.class);
    }
}
