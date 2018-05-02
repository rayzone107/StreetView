package com.osahub.rachit.streetview.modules.home.categoryfragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseFragment;
import com.osahub.rachit.streetview.modules.category.SingleCategoryActivity;
import com.osahub.rachit.streetview.modules.home.adapter.LocationListAdapter;
import com.osahub.rachit.streetview.modules.streetview.StreetViewActivity;
import com.osahub.rachit.streetview.utils.Constants;
import com.osahub.rachit.streetview.utils.Helper;
import com.osahub.rachit.streetview.utils.TextUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.fragment_holder)
    RelativeLayout mParentRL;

    @BindView(R.id.locations_rv)
    RecyclerView mLocationsRV;

    @BindView(R.id.background_iv)
    ImageView mBackgroundIV;

    @BindView(R.id.blur_iv)
    ImageView mBlurIV;

    @BindView(R.id.category_name_tv)
    TextView mCategoryName;

    @BindView(R.id.count_tv)
    TextView mCount;

    @BindView(R.id.category_header)
    LinearLayout mCategoryHeader;

    Category mCategory;
    List<Location> mLocationsArray = new ArrayList<>();
    private int mScrollX = 0;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance(int categoryId) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRAS.CATEGORY_ID, categoryId);
        categoryFragment.setArguments(args);
        return categoryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int categoryId = getArguments().getInt(Constants.EXTRAS.CATEGORY_ID);
            mCategory = mDatabaseHelper.mCategoryDbHelper.getCategoryById(categoryId);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, rootView);

        if (mCategory.getType().equals(Helper.CATEGORY_TYPE_SPECIAL)) {
            if (Build.VERSION.SDK_INT < 23) {
                mCategoryName.setTextAppearance(mContext, R.style.boldText);
            } else {
                mCategoryName.setTextAppearance(R.style.boldText);
            }

            if (!TextUtil.isEmpty(mCategory.getBackgroundUrl())) {
                Picasso.get().load(mCategory.getBackgroundUrl()).
                        fit().centerCrop().into(mBackgroundIV);
            }

            mLocationsRV.setPadding(0, Helper.convertDpToPixel(mContext, 12), 0, Helper.convertDpToPixel(mContext, 8));
            mLocationsArray.add(new Location(Helper.LOCATION_FRAGMENT_BLANK_TAG));

            mLocationsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mScrollX += dx;
                    if (mScrollX > 0 && mBlurIV.getVisibility() != View.VISIBLE) {
                        Blurry.with(mContext).radius(30).capture(mBackgroundIV).into(mBlurIV);
                        mBlurIV.setImageAlpha(0);
                        mBlurIV.setVisibility(View.VISIBLE);
                    }
                    mBlurIV.setImageAlpha(mScrollX > 255 ? 255 : mScrollX);
                }
            });
        }
        mCategoryName.setText(mCategory.getName());

        mCategoryHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleCategoryActivity.startActivity(mContext, mCategory.getCategoryId());
            }
        });

        List<Location> locations = mDatabaseHelper.mLocationDbHelper.getLimitedLocationsByCategoryId(mCategory.getCategoryId());

        if (locations != null && !locations.isEmpty()) {
            mLocationsArray.addAll(locations);
            setLocationsList();
        }
        return rootView;
    }

    public void setLocationsList() {
        LocationListAdapter mAdapter = new LocationListAdapter(mLocationsArray,
                new LocationListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Location location) {
                        startActivity(StreetViewActivity.getStartIntent(mContext, location.getLocationId()));
                    }
                });

        mLocationsRV.setAdapter(mAdapter);
        mLocationsRV.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                mContext, LinearLayoutManager.HORIZONTAL, false);

        mLocationsRV.setLayoutManager(mLayoutManager);
        mLocationsRV.setItemAnimator(new DefaultItemAnimator());

        mLocationsRV.addItemDecoration(new Helper.HorizontalSpaceItemDecoration(Helper.convertDpToPixel(mContext, 20)));
        mCount.setText(String.valueOf(mDatabaseHelper.mLocationDbHelper.getLocationCountByCategoryId(mCategory.getCategoryId())));

        YoYo.with(Techniques.SlideInLeft).duration(400).playOn(mLocationsRV);
    }
}