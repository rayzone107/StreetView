package com.osahub.rachit.streetview.modules.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.osahub.rachit.streetview.misc.readmore.ReadMoreTextView;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.model.LocationImage;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.detail.adapter.HeaderImageAdapter;
import com.osahub.rachit.streetview.modules.detail.similarplaces.SimilarPlacesFragment;
import com.osahub.rachit.streetview.modules.mapview.MapViewActivity;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.utils.Constants;
import com.osahub.rachit.streetview.utils.TextUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends BaseActivity implements DetailContract.View, OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.share_iv)
    ImageView mShareIV;

    @BindView(R.id.like_lav)
    LottieAnimationView mLikeLAV;

    @BindView(R.id.header_view_pager)
    AutoViewPager mHeaderViewPager;

    @BindView(R.id.loading_parent_fl)
    FrameLayout mLoadingParentFL;

    @BindView(R.id.loading_lav)
    LottieAnimationView mLoadingLAV;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.name_tv)
    TextView mLocationNameTV;

    @BindView(R.id.thumbnail_civ)
    CircleImageView mThumbnailCIV;

    @BindView(R.id.city_tv)
    TextView mCityTV;

    @BindView(R.id.built_in_tv)
    TextView mBuiltInTV;

    @BindView(R.id.built_by_tv)
    TextView mBuiltByTV;

    @BindView(R.id.description_tv)
    ReadMoreTextView mDescriptionTV;

    @BindView(R.id.map_view)
    MapView mMapView;

    @BindView(R.id.map_overlay)
    View mMapOverlay;

    @BindView(R.id.similar_places_ll)
    LinearLayout mSimilarPlacesLL;

    private Location mLocation;
    private DetailContract.Presenter mPresenter;
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
        ButterKnife.bind(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        ViewCompat.setTransitionName(mMapView, Constants.TRANSITION_KEYS.MAP_VIEW);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                showLottie(R.raw.plane);
                mAppBar.setVisibility(View.GONE);
                mNestedScrollView.setVisibility(View.GONE);
                break;
            case DATA:
                mLoadingParentFL.setVisibility(View.GONE);
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
        Window window = DetailActivity.this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mLocation = location;
        mMapOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MapViewActivity.getStartIntent(DetailActivity.this,
                        new ArrayList<>(Collections.singletonList(mLocation.getLocationId())), 0);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(DetailActivity.this, mMapView, Constants.TRANSITION_KEYS.MAP_VIEW);
                startActivity(intent, options.toBundle());
            }
        });

        isFavourite = location.isFavourite();
        mLikeLAV.setProgress(isFavourite ? 1 : 0);
        mLikeLAV.setSpeed(isFavourite ? -1 : 1);

        mLikeLAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavourite = !isFavourite;
                mDatabaseHelper.mLocationDbHelper.markLocationAsFavourite(mLocation.getLocationId(), isFavourite);
                mLikeLAV.setSpeed(isFavourite ? 1 : -1);
                mLikeLAV.playAnimation();
                Snackbar.make(mNestedScrollView, getString(isFavourite ? R.string.added_to_favourites :
                        R.string.removed_from_favourites), Snackbar.LENGTH_SHORT).show();
            }
        });

        Picasso.get().load(mLocation.getThumbnailPath()).fit().centerCrop().into(mThumbnailCIV);
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
        mLoadingParentFL.setVisibility(View.VISIBLE);
        mLoadingLAV.setAnimation(resourceId);
        mLoadingLAV.setRepeatMode(LottieDrawable.INFINITE);
        mLoadingLAV.playAnimation();
    }

    @Override
    public void showImages(List<LocationImage> locationImages) {
        HeaderImageAdapter headerImageAdapter = new HeaderImageAdapter(mContext, locationImages);
        mHeaderViewPager.setAdapter(headerImageAdapter);
        mHeaderViewPager.startAutoScroll();
        setData();
    }

    @Override
    public void showSimilarPlaces(ArrayList<Integer> similarPlaces) {
        mSimilarPlacesLL.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SimilarPlacesFragment similarPlacesFragment = SimilarPlacesFragment.newInstance(similarPlaces);
        fragmentTransaction.replace(R.id.similar_container, similarPlacesFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void hideSimilarPlaces() {
        mSimilarPlacesLL.setVisibility(View.GONE);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showThumbnailInImages() {
        List<LocationImage> locationImages = new ArrayList<>();
        locationImages.add(new LocationImage(mLocation.getLocationId(), mLocation.getThumbnailPath()));
        HeaderImageAdapter headerImageAdapter = new HeaderImageAdapter(mContext, locationImages);
        mHeaderViewPager.setAdapter(headerImageAdapter);
        setData();
    }

    private void setData() {
        mLocationNameTV.setText(mLocation.getLocationName());

        String cityStateCountry = mLocation.getCity() + (mLocation.getCity().isEmpty() ? "" : ", ")
                + mLocation.getState() + (mLocation.getState().isEmpty() ? "" : ", ")
                + mLocation.getCountry();
        mCityTV.setText(cityStateCountry);
        mBuiltInTV.setText(TextUtil.isEmpty(mLocation.getBuiltIn()) ? "" : mLocation.getBuiltIn());
        mBuiltByTV.setText(TextUtil.isEmpty(mLocation.getBuiltBy()) ? "" : mLocation.getBuiltBy());
        mDescriptionTV.setText(mLocation.getDescription(), TextView.BufferType.NORMAL);
    }
}