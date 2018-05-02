package com.osahub.rachit.streetview.modules.detail.similarplaces;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.utils.Helper;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseFragment;
import com.osahub.rachit.streetview.modules.home.adapter.LocationListAdapter;
import com.osahub.rachit.streetview.modules.streetview.StreetViewActivity;
import com.osahub.rachit.streetview.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarPlacesFragment extends BaseFragment {

    @BindView(R.id.locations_rv)
    RecyclerView mLocationsRV;

    List<Location> mLocationsArray = new ArrayList<>();

    public SimilarPlacesFragment() {
    }

    public static SimilarPlacesFragment newInstance(ArrayList<Integer> locationIds) {
        SimilarPlacesFragment categoryFragment = new SimilarPlacesFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(Constants.EXTRAS.LOCATION_ID_LIST, locationIds);
        categoryFragment.setArguments(args);
        return categoryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList<Integer> locationIds = getArguments().getIntegerArrayList(Constants.EXTRAS.LOCATION_ID_LIST);
            mLocationsArray = mDatabaseHelper.mLocationDbHelper.getLocationsByIdsList(locationIds);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_similar_places, container, false);
        ButterKnife.bind(this, rootView);

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

        return rootView;
    }
}
