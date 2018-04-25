package com.osahub.rachit.streetview.modules.category;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.category.adapter.CategoryLocationListAdapter;
import com.osahub.rachit.streetview.modules.streetview.StreetViewActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity {

    Category mCategory;
    RecyclerView mLocations;
    private GridLayoutManager mGridLayoutManager;
    List<Location> mLocationsArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        int categoryId = getIntent().getIntExtra(Helper.CATEGORY_EXTRA, -1);
        if (categoryId != -1) {
            mCategory = mDatabaseHelper.mCategoryDbHelper.getCategoryById(categoryId);
        } else {
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mCategory.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLocations = findViewById(R.id.locations);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mGridLayoutManager = new GridLayoutManager(CategoryActivity.this, 3);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridLayoutManager = new GridLayoutManager(CategoryActivity.this, 5);
        }

        mLocationsArray = mDatabaseHelper.mLocationDbHelper.getLocationsByCategoryId(mCategory.getCategoryId());

        if (mLocationsArray != null && !mLocationsArray.isEmpty()) {
            setLocationsList();
        }
    }

    public void setLocationsList() {
        CategoryLocationListAdapter mAdapter = new CategoryLocationListAdapter(CategoryActivity.this, mLocationsArray,
                new CategoryLocationListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Location location) {
                        Intent intent = new Intent(CategoryActivity.this, StreetViewActivity.class);
                        intent.putExtra("location", location.getId());
                        startActivity(intent);
                    }
                });
        mLocations.setHasFixedSize(true);
        mLocations.setLayoutManager(mGridLayoutManager);
        mLocations.setItemAnimator(new DefaultItemAnimator());
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLocations.addItemDecoration(new Helper.GridSpacingItemDecoration(3, 10, true));
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLocations.addItemDecoration(new Helper.GridSpacingItemDecoration(5, 10, true));
        }
        mLocations.setAdapter(mAdapter);
    }
}