package com.osahub.rachit.streetview.modules.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.misc.autoscroll.AutoViewPager;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImages;
import com.osahub.rachit.streetview.model.LocationSimilarPlaces;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.detail.adapter.HeaderImageAdapter;
import com.osahub.rachit.streetview.modules.gallery.GalleryActivity;
import com.osahub.rachit.streetview.modules.home.categoryfragment.CategoryItemFragment;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.utils.Constants;

import java.util.List;

public class DetailActivity extends BaseActivity implements DetailContract.View, OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ImageView mShareIV;
    private LottieAnimationView mLikeLAV;
    private AutoViewPager mHeaderViewPager;
    private LottieAnimationView mLoadingLAV;
    private NestedScrollView mNestedScrollView;
    private TextView mLocationNameTV;
    private TextView mCityTV;
    private TextView mBuiltInTV;
    private TextView mBuiltByTV;
    private TextView mDescriptionTV;
    private MapView mMapView;
    private FrameLayout mSimilarContainerFL;

    private Location mLocation;
    private List<LocationImages> mLocationImages;
    private HeaderImageAdapter mHeaderImageAdapter;
    private DetailContract.Presenter mPresenter;
    private FragmentTransaction mFragmentTransaction;

    private GoogleMap mGoogleMap;


    private boolean isFavourite;

    public static Intent getStartIntent(Context context, int locationId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.EXTRAS.LOCATION_ID, locationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        Window window = DetailActivity.this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        mAppBar = findViewById(R.id.app_bar);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mToolbar = findViewById(R.id.toolbar);
        mShareIV = findViewById(R.id.share_iv);
        mLikeLAV = findViewById(R.id.like_lav);
        mHeaderViewPager = findViewById(R.id.header_view_pager);
        mLoadingLAV = findViewById(R.id.loading_lav);
        mNestedScrollView = findViewById(R.id.scroll);
        mLocationNameTV = findViewById(R.id.name_tv);
        mCityTV = findViewById(R.id.city_tv);
        mBuiltInTV = findViewById(R.id.built_in_tv);
        mBuiltByTV = findViewById(R.id.built_by_tv);
        mDescriptionTV = findViewById(R.id.description_tv);
        mSimilarContainerFL = findViewById(R.id.similar_container);
        mMapView = findViewById(R.id.map_view);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int color = (mCollapsingToolbarLayout.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(mCollapsingToolbarLayout)) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);

                mToolbar.getNavigationIcon().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                mShareIV.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        });

        mPresenter = new DetailPresenter(this, new NetworkRequest(), mDatabaseHelper);
        int locationId = getIntent().getIntExtra(Constants.EXTRAS.LOCATION_ID, -1);
        mPresenter.fetchLocation(locationId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void changeViewState(ViewState viewState) {
        switch (viewState) {
            case LOADING:
                showLottie(R.raw.world_locations);
                mAppBar.setVisibility(View.GONE);
                mNestedScrollView.setVisibility(View.GONE);
                break;
            case DATA:
                mLoadingLAV.setVisibility(View.GONE);
                mLoadingLAV.cancelAnimation();
                mAppBar.setVisibility(View.VISIBLE);
                mNestedScrollView.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                mAppBar.setExpanded(false);
                showLottie(R.raw.error_message);
                mAppBar.setVisibility(View.VISIBLE);
                mNestedScrollView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void setLocation(Location location) {
        mLocation = location;


        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHeaderViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(GalleryActivity.getStartIntent(mContext, mLocation.getLocationId(), mHeaderViewPager.getCurrentItem()));
            }
        });

        mLikeLAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLikeLAV.setSpeed(isFavourite ? -1 : 1);
                mLikeLAV.playAnimation();
                isFavourite = !isFavourite;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng locationLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 16f));
        addMarker(locationLatLng);
    }

    private void addMarker(LatLng locationLatLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(locationLatLng);
        markerOptions.title(mLocation.getLocationName());
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.setTag(mLocation.getLocationId());
        marker.showInfoWindow();
    }

    private void showLottie(@RawRes int resourceId) {
        mLoadingLAV.setVisibility(View.VISIBLE);
        mLoadingLAV.setAnimation(resourceId);
        mLoadingLAV.setRepeatMode(LottieDrawable.INFINITE);
        mLoadingLAV.playAnimation();
    }

    @Override
    public void showImages(List<LocationImages> locationImages) {
        mLocationImages = locationImages;
        mHeaderImageAdapter = new HeaderImageAdapter(mContext, locationImages);
        mHeaderViewPager.setAdapter(mHeaderImageAdapter);
        mHeaderViewPager.startAutoScroll();

        mLocationNameTV.setText(mLocation.getLocationName());

        String cityStateCountry = mLocation.getCity() + (mLocation.getCity().isEmpty() ? "" : ", ")
                + mLocation.getState() + (mLocation.getState().isEmpty() ? "" : ", ")
                + mLocation.getCountry();

        mCityTV.setText(cityStateCountry);
        mDescriptionTV.setText(mLocation.getDescription());

        changeViewState(ViewState.DATA);
    }

    @Override
    public void showSimilarPlaces(List<LocationSimilarPlaces> similarPlaces) {
        mSimilarContainerFL.setVisibility(View.VISIBLE);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        CategoryItemFragment categoryItemFragment = CategoryItemFragment.newInstance(1);
        mFragmentTransaction.add(R.id.similar_container, categoryItemFragment, "category");
        mFragmentTransaction.commit();
    }

    @Override
    public void hideSimilarPlaces() {
        mSimilarContainerFL.setVisibility(View.GONE);
    }
}