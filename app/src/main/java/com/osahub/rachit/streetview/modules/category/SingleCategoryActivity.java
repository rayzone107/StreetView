package com.osahub.rachit.streetview.modules.category;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.category.adapter.SliderAdapter;
import com.osahub.rachit.streetview.modules.mapview.MapViewActivity;
import com.osahub.rachit.streetview.modules.streetview.StreetViewActivity;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.utils.Constants;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleCategoryActivity extends BaseActivity implements SingleCategoryContract.View,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String MAP_VIEW_FRAGMENT_TAG = "MapViewFragmentTag";

    @BindView(R.id.error_layout)
    LinearLayout mErrorLayout;
    @BindView(R.id.error_lav)
    LottieAnimationView mErrorLAV;
    @BindView(R.id.error_message)
    TextView mErrorMessageTV;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.parent_rl)
    RelativeLayout mParentRL;
    @BindView(R.id.location_list_rv)
    RecyclerView mLocationListRV;
    @BindView(R.id.category_name_tv)
    TextView mCategoryNameTV;
    @BindView(R.id.location_name_tv)
    TextView mLocationNameTV;
    @BindView(R.id.location_short_desc_tv)
    TextView mLocationDescTV;
    @BindView(R.id.map_view)
    MapView mMapView;

    private SingleCategoryContract.Presenter mPresenter;
    private SliderAdapter sliderAdapter;
    private CardSliderLayoutManager mLayoutManger;
    private List<Location> mLocationList;
    private int mCurrentPosition;
    private GoogleMap mGoogleMap;
    private List<Marker> mMarkerList = new ArrayList<>();
    private int mCategoryId;
    private String mCategoryName;
    private boolean mIsMapReady;

    public static void startActivity(Context context, int categoryId) {
        Intent intent = new Intent(context, SingleCategoryActivity.class);
        intent.putExtra(Constants.EXTRAS.CATEGORY_ID, categoryId);
        context.startActivity(intent);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SingleCategoryActivity.class);
        intent.putExtra(Constants.EXTRAS.SHOW_FAVOURITES, true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);
        ButterKnife.bind(this);

        mPresenter = new SingleCategoryPresenter(this, mDatabaseHelper, new NetworkRequest());

        boolean showFavourites = getIntent().getBooleanExtra(Constants.EXTRAS.SHOW_FAVOURITES, false);
        mCategoryId = getIntent().getIntExtra(Constants.EXTRAS.CATEGORY_ID, -1);
        mPresenter.fetchData(showFavourites, mCategoryId);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        ViewCompat.setTransitionName(mMapView, Constants.TRANSITION_KEYS.MAP_VIEW);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    public void changeViewState(ViewState viewState) {
        switch (viewState) {
            case LOADING:
                mErrorLayout.setVisibility(View.VISIBLE);
                mParentRL.setVisibility(View.GONE);
                break;
            case DATA:
                mErrorLayout.setVisibility(View.GONE);
                mParentRL.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                mErrorLayout.setVisibility(View.VISIBLE);
                mParentRL.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick(R.id.fullscreen_iv)
    public void onFullscreenClick(View view) {
        Intent intent = MapViewActivity.getStartIntent(SingleCategoryActivity.this,
                mPresenter.getLocationIds(), mCurrentPosition);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(SingleCategoryActivity.this, mMapView, Constants.TRANSITION_KEYS.MAP_VIEW);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
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
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mIsMapReady = true;
        if (mLocationList != null && !mLocationList.isEmpty()) {
            displayData();
        }
    }


    @Override
    public void showData(String categoryName, List<Location> locationList) {
        mLocationList = locationList;
        mCategoryName = categoryName;
        if (mIsMapReady) {
            displayData();
        }
    }

    @Override
    public void showError(@RawRes int rawRes, @StringRes int messageRes) {
        mErrorLAV.setVisibility(View.VISIBLE);
        mErrorLAV.setAnimation(rawRes);
        mErrorLAV.playAnimation();
        mErrorMessageTV.setText(messageRes);
    }

    @Override
    public void changeToolbarTitle(String title) {
        mCategoryNameTV.setText(title);
    }

    private void displayData() {
        addAllMarkers();
        setInitialData();
        initRecyclerView();
    }

    private void addAllMarkers() {
        for (Location location : mLocationList) {
            LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(locationLatLng);
            markerOptions.title(location.getLocationName());
            Marker marker = mGoogleMap.addMarker(markerOptions);
            marker.setTag(location.getLocationId());
            mMarkerList.add(marker);
        }
    }

    private void setInitialData() {
        Location location = mLocationList.get(0);
        mCategoryNameTV.setText(mCategoryName);
        mLocationNameTV.setText(location.getLocationName());
        mLocationDescTV.setText(location.getDescriptionSmall());
        setMapLocation(location);
    }

    private void initRecyclerView() {
        sliderAdapter = new SliderAdapter(mLocationList, new OnCardClickListener());
        mLocationListRV.setAdapter(sliderAdapter);
        mLocationListRV.setHasFixedSize(true);

        mLocationListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        mLayoutManger = (CardSliderLayoutManager) mLocationListRV.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(mLocationListRV);
    }

    private void onActiveCardChange() {
        final int pos = mLayoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == mCurrentPosition) {
            return;
        }

        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {

        final Location location = mLocationList.get(pos);

        YoYo.with(Techniques.SlideOutLeft).duration(300).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.FadeOut).duration(300).playOn(mLocationNameTV);
            }
        }).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.SlideInRight).duration(300).onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        YoYo.with(Techniques.FadeIn).duration(300).playOn(mLocationNameTV);
                        mLocationNameTV.setText(location.getLocationName());
                    }
                }).playOn(mLocationNameTV);
            }
        }).playOn(mLocationNameTV);

        YoYo.with(Techniques.SlideOutLeft).duration(300).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.FadeOut).duration(300).playOn(mLocationDescTV);
            }
        }).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.SlideInRight).duration(300).onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        YoYo.with(Techniques.FadeIn).duration(300).playOn(mLocationDescTV);
                        mLocationDescTV.setText(location.getDescriptionSmall());
                    }
                }).playOn(mLocationDescTV);
            }
        }).playOn(mLocationDescTV);
        setMapLocation(location);
        mCurrentPosition = pos;
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm = (CardSliderLayoutManager) mLocationListRV.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = mLocationListRV.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {
                startActivity(StreetViewActivity.getStartIntent(SingleCategoryActivity.this, mLocationList.get(clickedPosition).getLocationId()));
            } else if (clickedPosition > activeCardPosition) {
                mLocationListRV.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }
        }
    }

    private void setMapLocation(Location location) {
        LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 16f));

        for (Marker marker : mMarkerList) {
            if (location.getLocationId() == (int) marker.getTag()) {
                marker.showInfoWindow();
                break;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int locationId = (int) marker.getTag();
        for (int i = 0; i < mLocationList.size(); i++) {
            if (mLocationList.get(i).getLocationId() == locationId) {
                mLocationListRV.smoothScrollToPosition(i);
                onActiveCardChange(i);
                break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MAP_VIEW_FRAGMENT_TAG);
        if (fragment != null && fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
