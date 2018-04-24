package com.osahub.rachit.streetview.modules.category;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseFragment;
import com.osahub.rachit.streetview.modules.home.adapter.LocationListAdapter;
import com.osahub.rachit.streetview.modules.streetview.StreetViewActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment {

    private static final String LOG_TAG = "World Tour 3D: " + CategoryFragment.class.getSimpleName();

    Category mCategory;
    private RecyclerView mLocations;
    TextView mCategoryName, mCount;
    LinearLayout mCategoryHeader, mFragmentHolder;
    int mWidth, mHeight;

    List<Location> mLocationsArray = new ArrayList<>();

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = (Category) getArguments().getSerializable("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_category, container, false);

        mFragmentHolder = rootView.findViewById(R.id.fragment_holder);
        mCategoryName = rootView.findViewById(R.id.category_name);
        mCount = rootView.findViewById(R.id.count);
        mLocations = rootView.findViewById(R.id.locations);
        mCategoryHeader = rootView.findViewById(R.id.category_header);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mHeight = size.y;

        if (mCategory.getType().equals(Helper.CATEGORY_TYPE_SPECIAL)) {
            if (Build.VERSION.SDK_INT < 23) {
                mCategoryName.setTextAppearance(getActivity(), R.style.boldText);
            } else {
                mCategoryName.setTextAppearance(R.style.boldText);
            }

            if (mCategory.getCategoryId() == 1) {
                mLocations.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_fragment_most_popular));
            } else if (mCategory.getCategoryId() == 2) {
                mLocations.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_fragment_most_visited));
            }

            mLocations.setPadding(0, Helper.convertDpToPixel(getActivity(), 12), 0, Helper.convertDpToPixel(getActivity(), 8));
            mLocationsArray.add(new Location(Helper.LOCATION_FRAGMENT_BLANK_TAG));
        }
        mCategoryName.setText(mCategory.getName());

        mCategoryHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(Helper.CATEGORY_EXTRA, mCategory.getCategoryId());
                startActivity(intent);
            }
        });

        mLocationsArray.addAll(mDatabaseHelper.mLocationDbHelper.getLimitedLocationsByCategoryId(mCategory.getCategoryId()));

        setLocationsList();
        return rootView;
    }

    public void setLocationsList() {
        LocationListAdapter mAdapter = new LocationListAdapter(getActivity(), mLocationsArray,
                new LocationListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Location location) {
                        Intent intent = new Intent(getActivity(), StreetViewActivity.class);
                        intent.putExtra("location", location.getLocationId());
                        startActivity(intent);
                    }
                });

        mLocations.setAdapter(mAdapter);
        mLocations.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                mContext, LinearLayoutManager.HORIZONTAL, false);

        mLocations.setLayoutManager(mLayoutManager);
        mLocations.setItemAnimator(new DefaultItemAnimator());


        if (mWidth < 320 && mHeight < 480) {
            mLocations.addItemDecoration(new Helper.HorizontalSpaceItemDecoration(10));
        } else if (mWidth < 480 && mHeight < 800) {
            mLocations.addItemDecoration(new Helper.HorizontalSpaceItemDecoration(15));
        } else if (mWidth < 720 && mHeight < 1280) {
            mLocations.addItemDecoration(new Helper.HorizontalSpaceItemDecoration(20));
        } else if (mWidth < 1080 && mHeight < 1920) {
            mLocations.addItemDecoration(new Helper.HorizontalSpaceItemDecoration(30));
        } else if (mWidth < 1440 && mHeight < 2560) {
            mLocations.addItemDecoration(new Helper.HorizontalSpaceItemDecoration(40));
        } else {
            mLocations.addItemDecoration(new Helper.HorizontalSpaceItemDecoration(50));
        }
        mCount.setText(String.valueOf(mDatabaseHelper.mLocationDbHelper.getLocationCountByCategoryId(mCategory.getCategoryId())));
    }
}