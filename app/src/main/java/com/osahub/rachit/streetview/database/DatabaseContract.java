package com.osahub.rachit.streetview.database;

import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.CategoryLocation;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImage;
import com.osahub.rachit.streetview.model.LocationSimilarPlace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public interface DatabaseContract {

    interface CategoryContract {

        void saveCategory(Category category);

        void addMultipleCategories(List<Category> categories);

        void updateOrInsertCategory(Category category);

        void updateOrInsertMultipleCategories(List<Category> categories);

        int getCategoryCount();

        Category getCategoryById(int categoryId);

        Category getCategoryByName(String name);

        List<Category> getCategoriesByIdsList(List<Integer> categoryIds);

        List<Category> getCategoriesByNamesList(List<String> names);

        List<Category> getAllCategories();

        List<Category> getAllCategoriesThatContainString(String searchValue);

        void updateCategoryById(int categoryId, Category category);

        void deleteCategoryById(int categoryId);

        void deleteAllCategories();
    }

    interface LocationContract {

        void saveLocation(Location location);

        void addMultipleLocations(List<Location> locations);

        void updateOrInsertLocation(Location location);

        void updateOrInsertMultipleLocations(List<Location> locations);

        int getLocationCount();

        Location getLocationById(int locationId);

        Location getLocationByName(String name);

        List<Location> getLocationsByIdsList(List<Integer> locationIds);

        List<Location> getLocationsByNamesList(List<String> names);

        ArrayList<Integer> getLocationIdsByCategoryId(int categoryId, int limit);

        List<Location> getLocationsByCategoryId(int categoryId);

        int getLocationCountByCategoryId(int categoryId);

        List<Location> getLimitedLocationsByCategoryId(int categoryId);

        List<Location> getAllLocationsThatContainString(String searchValue);

        void updateLocationById(int locationId, Location location);

        void deleteLocationById(int locationId);

        void deleteAllLocations();

        void addLocationImages(List<LocationImage> locationImages);

        void updateOrInsertMultipleLocationImages(List<LocationImage> locationImages);

        void updateOrInsertMultipleSimilarPlaces(List<LocationSimilarPlace> locationSimilarPlaces);

        List<Location> getAllFavouritedLocations();

        ArrayList<Integer> getAllFavouritedLocationIds();

        boolean checkIfFavouritedLocation(int locationId);

        void markLocationAsFavourite(int locationId, boolean isFavourite);
    }

    interface CategoryLocationContract {

        void saveCategoryLocation(CategoryLocation categoryLocation);

        void addMultipleCategoryLocations(List<CategoryLocation> categoryLocationList);

        void updateOrInsertMultipleCategoryLocations(List<CategoryLocation> categoryLocations);

        int getCategoryLocationCount();

        List<CategoryLocation> getCategoryLocationByCategoryId(int categoryId);

        List<CategoryLocation> getAllCategoryLocations();

        void updateCategoryLocationById(int categoryLocationId, CategoryLocation categoryLocation);

        void deleteCategoryLocationById(int categoryLocationId);

        void deleteAllCategoryLocations();

    }
}
