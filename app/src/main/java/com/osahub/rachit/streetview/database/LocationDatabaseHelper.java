package com.osahub.rachit.streetview.database;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.osahub.rachit.streetview.model.CategoryLocation;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImage;
import com.osahub.rachit.streetview.model.LocationSimilarPlace;
import com.osahub.rachit.streetview.utils.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 20/04/18
 */

public class LocationDatabaseHelper implements DatabaseContract.LocationContract {

    @Override
    public void saveLocation(Location location) {
        SugarRecord.save(location);
    }

    @Override
    public void addMultipleLocations(List<Location> locations) {
        SugarRecord.saveInTx(locations);
    }

    @Override
    public void updateOrInsertLocation(Location location) {
        Location storedLocation = Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_ID).
                eq(location.getLocationId())).first();
        if (storedLocation != null) {
            storedLocation.updateObject(location);
            saveLocation(storedLocation);
        } else {
            saveLocation(location);
        }
    }

    @Override
    public void updateOrInsertMultipleLocations(List<Location> locations) {
        for (Location location : locations) {
            updateOrInsertLocation(location);
        }
    }

    @Override
    public int getLocationCount() {
        return Select.from(Location.class).list().size();
    }

    @Override
    public Location getLocationById(int locationId) {
        return Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_ID).eq(locationId)).first();
    }

    @Override
    public Location getLocationByName(String name) {
        return Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_NAME).eq(name)).first();
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

    /**
     * @param categoryId - Find by category ID
     * @param limit      - Set limit, -1 = no limit
     * @return ArrayList of Location IDs
     */
    @Override
    public ArrayList<Integer> getLocationIdsByCategoryId(int categoryId, int limit) {
        Select<CategoryLocation> select = Select.from(CategoryLocation.class).
                where(Condition.prop(CategoryLocation.COLUMN_CATEGORY_ID).eq(categoryId)).
                orderBy(CategoryLocation.COLUMN_POSITION);
        if (limit > 0) {
            select.limit(String.valueOf(limit));
        }
        List<CategoryLocation> categoryLocationList = select.list();

        ArrayList<Integer> locationIds = new ArrayList<>();
        for (CategoryLocation categoryLocation : categoryLocationList) {
            locationIds.add(categoryLocation.getLocationId());
        }
        return locationIds;
    }

    @Override
    public List<Location> getLocationsByCategoryId(int categoryId) {
        return getLocationsByIdsList(getLocationIdsByCategoryId(categoryId, -1));
    }

    @Override
    public int getLocationCountByCategoryId(int categoryId) {
        List<Location> locations = getLocationsByCategoryId(categoryId);
        return locations == null ? 0 : locations.size();
    }

    @Override
    public List<Location> getLimitedLocationsByCategoryId(int categoryId) {
        return getLocationsByIdsList(getLocationIdsByCategoryId(categoryId, Helper.MAX_PER_LIST_ON_HOME_SCREEN));
    }

    @Override
    public List<Location> getAllLocationsThatContainString(String searchValue) {
        String searchField = "%" + searchValue + "%";
        return Select.from(Location.class)
                .where(Condition.prop(Location.COLUMN_LOCATION_NAME).like(searchField))
                .or(Condition.prop(Location.COLUMN_CITY).like(searchField))
                .or(Condition.prop(Location.COLUMN_STATE).like(searchField))
                .or(Condition.prop(Location.COLUMN_COUNTRY).like(searchField))
                .or(Condition.prop(Location.COLUMN_BUILT_BY).like(searchField))
                .list();
    }

    @Override
    public void updateLocationById(int locationId, Location location) {
        Location originalLocation = Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_ID).eq(locationId)).first();
        originalLocation.updateObject(location);
        saveLocation(originalLocation);
    }

    @Override
    public void deleteLocationById(int locationId) {
        Location location = Select.from(Location.class).where(Condition.prop(Location.COLUMN_LOCATION_ID).eq(locationId)).first();
        SugarRecord.delete(location);
    }

    @Override
    public void deleteAllLocations() {
        SugarRecord.deleteAll(Location.class);
    }

    @Override
    public void addLocationImages(List<LocationImage> locationImages) {
        SugarRecord.saveInTx(locationImages);
    }

    @Override
    public void updateOrInsertMultipleLocationImages(List<LocationImage> locationImages) {
        for (LocationImage locationImage : locationImages) {
            LocationImage storedLocationImage = Select.from(LocationImage.class)
                    .where(Condition.prop(LocationImage.COLUMN_LOCATION_IMAGE_ID)
                            .eq(locationImage.getLocationImageId())).first();
            if (storedLocationImage != null) {
                storedLocationImage.updateObject(locationImage);
                SugarRecord.save(storedLocationImage);
            } else {
                SugarRecord.save(locationImage);
            }
        }
    }

    @Override
    public void updateOrInsertMultipleSimilarPlaces(List<LocationSimilarPlace> locationSimilarPlaces) {
        for (LocationSimilarPlace locationSimilarPlace : locationSimilarPlaces) {
            LocationSimilarPlace storedLocationSimilarPlace = Select.from(LocationSimilarPlace.class)
                    .where(Condition.prop(LocationSimilarPlace.COLUMN_LOCATION_SIMILAR_PLACE_ID)
                            .eq(locationSimilarPlace.getLocationSimilarPlaceId())).first();
            if (storedLocationSimilarPlace != null) {
                storedLocationSimilarPlace.updateObject(locationSimilarPlace);
                SugarRecord.save(storedLocationSimilarPlace);
            } else {
                SugarRecord.save(locationSimilarPlace);
            }
        }
    }

    @Override
    public List<Location> getAllFavouritedLocations() {
        return Select.from(Location.class).where(Condition.prop(Location.COLUMN_IS_FAVOURITE).eq(1)).list();
    }

    @Override
    public ArrayList<Integer> getAllFavouritedLocationIds() {
        List<Location> locationList = getAllFavouritedLocations();

        ArrayList<Integer> locationIds = new ArrayList<>();
        for (Location location : locationList) {
            locationIds.add(location.getLocationId());
        }
        return locationIds;
    }

    @Override
    public boolean checkIfFavouritedLocation(int locationId) {
        Location location = Select.from(Location.class).where(Condition.prop(Location.COLUMN_IS_FAVOURITE).eq(1)).first();
        return location != null;
    }

    @Override
    public void markLocationAsFavourite(int locationId, boolean isFavourite) {
        Location location = getLocationById(locationId);
        location.setFavourite(isFavourite);
        saveLocation(location);
    }
}
