package com.osahub.rachit.streetview.database;

import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.CategoryLocations;
import com.osahub.rachit.streetview.model.Location;

import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public interface DatabaseContract {

    interface CategoryContract {

        void addCategory(Category category);

        void addMultipleCategories(List<Category> categories);

        Category getCategoryById(int categoryId);

        Category getCategoryByName(String name);

        List<Category> getCategoriesByIdsList(List<Integer> categoryIds);

        List<Category> getCategoriesByNamesList(List<String> names);

        List<Category> getAllCategories();

        void updateCategoryById(int categoryId, Category category);

        void deleteCategoryById(int categoryId);

        void deleteAllCategories();
    }

    interface LocationContract {

        void addLocation(Location location);

        void addMultipleLocations(List<Location> locations);

        Location getLocationById(int locationId);

        Location getLocationByName(String name);

        List<Location> getLocationsByIdsList(List<Integer> locationIds);

        List<Location> getLocationsByNamesList(List<String> names);

        List<Location> getLocationsByCategoryId(int categoryId);

        int getLocationCountByCategoryId(int categoryId);

        List<Location> getLimitedLocationsByCategoryId(int categoryId);

        void updateLocationById(int locationId, Location location);

        void deleteLocationById(int locationId);

        void deleteAllLocations();
    }

    interface CategoryLocationContract {

        void addCategoryLocations(CategoryLocations categoryLocations);

        void addMultipleCategoryLocations(List<CategoryLocations> categoryLocationsList);

        List<CategoryLocations> getCategoryLocationByCategoryId(int categoryId);

        List<CategoryLocations> getAllCategoryLocations();

        void updateCategoryLocationById(int categoryLocationId, CategoryLocations categoryLocations);

        void deleteCategoryLocationById(int categoryLocationId);

        void deleteAllCategoryLocations();

    }
}
