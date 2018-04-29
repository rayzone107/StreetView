package com.osahub.rachit.streetview.modules.mapview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.streetview.StreetViewActivity;
import com.osahub.rachit.streetview.utils.Constants;

import java.util.List;

public class MapViewActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private MapView mMapView;
    private ImageView mExitFullscreenIV;

    private Category mCategory;
    private List<Location> mLocationList;
    private int mCurrentPosition;

    private GoogleMap mGoogleMap;

    public static Intent getStartIntent(Context context, int categoryId, int selectedLocationPositon) {
        Intent intent = new Intent(context, MapViewActivity.class);
        intent.putExtra(Constants.EXTRAS.CATEGORY_ID, categoryId);
        intent.putExtra(Constants.EXTRAS.LOCATION_POSITION, selectedLocationPositon);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        int categoryId = getIntent().getIntExtra(Constants.EXTRAS.CATEGORY_ID, -1);
        if (categoryId != -1) {
            mCategory = mDatabaseHelper.mCategoryDbHelper.getCategoryById(categoryId);
            mLocationList = mDatabaseHelper.mLocationDbHelper.getLocationsByCategoryId(categoryId);
            mCurrentPosition = getIntent().getIntExtra(Constants.EXTRAS.LOCATION_POSITION, 0);
        } else {
            finish();
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mMapView = findViewById(R.id.map_view);
        ViewCompat.setTransitionName(mMapView, Constants.TRANSITION_KEYS.MAP_VIEW);

        mExitFullscreenIV = findViewById(R.id.exit_fullscreen_iv);
        mExitFullscreenIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        addAllMarkers();
        setMapLocation(mLocationList.get(mCurrentPosition));
    }

    private void addAllMarkers() {
        for (Location location : mLocationList) {
            LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(locationLatLng);
            markerOptions.title(location.getLocationName());
            mGoogleMap.setOnInfoWindowClickListener(this);
            Marker marker = mGoogleMap.addMarker(markerOptions);
            marker.setTag(location.getLocationId());
            marker.showInfoWindow();
        }
    }

    private void setMapLocation(Location location) {
        LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 16f));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        startActivity(StreetViewActivity.getStartIntent(this, (int) marker.getTag()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
