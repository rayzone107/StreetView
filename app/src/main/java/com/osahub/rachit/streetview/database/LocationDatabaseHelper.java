package com.osahub.rachit.streetview.database;

import com.activeandroid.query.Select;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.CategoryLocations;
import com.osahub.rachit.streetview.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class LocationDatabaseHelper implements DatabaseContract.LocationContract {

    @Override
    public void addLocation(Location location) {
        location.save();
    }

    @Override
    public void addMultipleLocations(List<Location> locations) {
        for (Location location : locations) {
            location.save();
        }
    }

    @Override
    public Location getLocationById(int locationId) {
        return new Select().from(Location.class).where(Location.COLUMN_LOCATION_ID + " = ? ", locationId).executeSingle();
    }

    @Override
    public Location getLocationByName(String name) {
        return new Select().from(Location.class).where(Location.COLUMN_LOCATION_NAME + " = ? ", name).executeSingle();
    }

    @Override
    public List<Location> getLocationsByIdsList(List<Integer> locationIds) {
        List<Location> locations = new ArrayList<>();
        for (Integer id : locationIds) {
            locations.add(getLocationById(id));
        }
        return locations;
    }

    @Override
    public List<Location> getLocationsByNamesList(List<String> names) {
        List<Location> locations = new ArrayList<>();
        for (String name : names) {
            locations.add(getLocationByName(name));
        }
        return locations;
    }

    @Override
    public List<Location> getLocationsByCategoryId(int categoryId) {
        List<CategoryLocations> categoryLocationsList = new Select().from(CategoryLocations.class).
                where(CategoryLocations.COLUMN_CATEGORY_ID + " = ? ", categoryId).execute();

        List<Integer> locationIds = new ArrayList<>();
        for (CategoryLocations categoryLocations : categoryLocationsList) {
            locationIds.add(categoryLocations.getLocationId());
        }
        return getLocationsByIdsList(locationIds);
    }

    @Override
    public int getLocationCountByCategoryId(int categoryId) {
        List<Location> locations = getLocationsByCategoryId(categoryId);
        return locations == null ? 0 : locations.size();
    }

    @Override
    public List<Location> getLimitedLocationsByCategoryId(int categoryId) {
        List<CategoryLocations> categoryLocationsList = new Select().from(CategoryLocations.class).
                where(CategoryLocations.COLUMN_CATEGORY_ID + " = ? ", categoryId).
                orderBy(CategoryLocations.COLUMN_POSITION).
                limit(String.valueOf(Helper.MAX_PER_LIST_ON_HOME_SCREEN)).execute();

        List<Integer> locationIds = new ArrayList<>();
        for (CategoryLocations categoryLocations : categoryLocationsList) {
            locationIds.add(categoryLocations.getLocationId());
        }
        return getLocationsByIdsList(locationIds);
    }

    @Override
    public void updateLocationById(int locationId, Location location) {
        Location originalLocation = new Select().from(Location.class).where(Location.COLUMN_LOCATION_ID + " = ? ", locationId).executeSingle();
        originalLocation.updateObject(location);
        originalLocation.save();
    }

    @Override
    public void deleteLocationById(int locationId) {
        Location location = new Select().from(Location.class).where(Location.COLUMN_LOCATION_ID + " = ? ", locationId).executeSingle();
        location.delete();
    }

    @Override
    public void deleteAllLocations() {
        Helper.clearTable(Location.class);
    }
}
