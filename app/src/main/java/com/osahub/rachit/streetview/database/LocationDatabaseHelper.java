package com.osahub.rachit.streetview.database;

import com.orm.query.Condition;
import com.orm.query.Select;
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
        return Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_ID).eq(locationId)).list().get(0);
    }

    @Override
    public Location getLocationByName(String name) {
        return Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_NAME).eq(name)).list().get(0);
    }

    @Override
    public List<Location> getLocationsByIdsList(List<Integer> locationIds) {
        String whereClause = " " + Location.COLUMN_LOCATION_ID + " IN (";
        String whereArgs = "";
        for (Integer id : locationIds) {
            whereClause = whereClause.concat("?,");
            whereArgs = whereArgs.concat(id + ",");
        }
        whereClause = whereClause.substring(0, whereClause.length() - 1).concat(") ");
        whereArgs = whereArgs.substring(0, whereArgs.length() - 1);

        return Location.find(Location.class, whereClause, whereArgs);
    }

    @Override
    public List<Location> getLocationsByNamesList(List<String> names) {
        String whereClause = " " + Location.COLUMN_LOCATION_NAME + " IN (";
        String whereArgs = "";
        for (String name : names) {
            whereClause = whereClause.concat("?,");
            whereArgs = whereArgs.concat(name + ",");
        }
        whereClause = whereClause.substring(0, whereClause.length() - 1).concat(") ");
        whereArgs = whereArgs.substring(0, whereArgs.length() - 1);

        return Location.find(Location.class, whereClause, whereArgs);
    }

    @Override
    public List<Location> getLocationsByCategoryId(int categoryId) {
        List<CategoryLocations> categoryLocationsList = Select.from(CategoryLocations.class).
                where(Condition.prop(CategoryLocations.COLUMN_CATEGORY_ID).eq(categoryId)).list();

        List<Integer> locationIds = new ArrayList<>();

        for (CategoryLocations categoryLocations : categoryLocationsList) {
            locationIds.add(categoryLocations.getLocationId());
        }
        return getLocationsByIdsList(locationIds);
    }

    @Override
    public int getLocationCountByCategoryId(int categoryId) {
        return getLocationsByCategoryId(categoryId).size();
    }

    @Override
    public List<Location> getLimitedLocationsByCategoryId(int categoryId) {
        List<CategoryLocations> categoryLocationsList = Select.from(CategoryLocations.class).
                where(Condition.prop(CategoryLocations.COLUMN_CATEGORY_ID).eq(categoryId)).
                limit(String.valueOf(Helper.MAX_PER_LIST_ON_HOME_SCREEN)).list();

        List<Integer> locationIds = new ArrayList<>();

        for (CategoryLocations categoryLocations : categoryLocationsList) {
            locationIds.add(categoryLocations.getLocationId());
        }
        return getLocationsByIdsList(locationIds);
    }

    @Override
    public void updateLocationById(int locationId, Location location) {
        Location originalLocation = Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_ID).eq(locationId)).list().get(0);
        long id = originalLocation.getId();
        originalLocation = location;
        originalLocation.setId(id);
        originalLocation.save();
    }

    @Override
    public void deleteLocationById(int locationId) {
        Location location = Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_ID).eq(locationId)).list().get(0);
        location.delete();
    }

    @Override
    public void deleteAllLocations() {
        Location.deleteAll(Location.class);
    }
}
