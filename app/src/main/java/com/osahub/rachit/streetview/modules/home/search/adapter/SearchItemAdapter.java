package com.osahub.rachit.streetview.modules.home.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;

import java.util.List;

/**
 * Created by Rachit Goyal on 30/04/18
 */

public class SearchItemAdapter extends RecyclerView.Adapter {

    private final int CATEGORY_HEADER_TYPE = 0;
    private final int CATEGORY_TYPE = 1;
    private final int LOCATION_HEADER_TYPE = 2;
    private final int LOCATION_TYPE = 3;

    private List<Category> mCategoryList;
    private List<Location> mLocationList;
    private OnItemClickListener mListener;

    public SearchItemAdapter(List<Category> categories, List<Location> locations, OnItemClickListener onItemClickListener) {
        mCategoryList = categories;
        mLocationList = locations;
        mListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case CATEGORY_HEADER_TYPE:
            case LOCATION_HEADER_TYPE:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_search_header, parent, false));
            case CATEGORY_TYPE:
                return new CategoryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_search_category, parent, false));
            default:
                return new LocationViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_search_location, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case CATEGORY_HEADER_TYPE:
                ((HeaderViewHolder) holder).bind(true);
                break;
            case LOCATION_HEADER_TYPE:
                ((HeaderViewHolder) holder).bind(false);
                break;
            case CATEGORY_TYPE:
                ((CategoryViewHolder) holder).bind(mCategoryList.get(position - 1), mListener);
                break;
            case LOCATION_TYPE:
                ((LocationViewHolder) holder).bind(mLocationList.get(position - mCategoryList.size() -
                        (mCategoryList.isEmpty() ? 1 : 2)), mListener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (!mCategoryList.isEmpty()) {
            size += mCategoryList.size() + 1;
        }
        if (!mLocationList.isEmpty()) {
            size += mLocationList.size() + 1;
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (!mCategoryList.isEmpty() && position == 0) {
            return CATEGORY_HEADER_TYPE;
        } else if (mCategoryList.isEmpty() && !mLocationList.isEmpty() && position == 0) {
            return LOCATION_HEADER_TYPE;
        } else if (position < mCategoryList.size() + 1) {
            return CATEGORY_TYPE;
        } else if (!mCategoryList.isEmpty() && !mLocationList.isEmpty() && position == mCategoryList.size() + 1) {
            return LOCATION_HEADER_TYPE;
        } else {
            return LOCATION_TYPE;
        }
    }

    public interface OnItemClickListener {
        void onCategoryClick(Category category);

        void onLocationClick(Location location);
    }
}
