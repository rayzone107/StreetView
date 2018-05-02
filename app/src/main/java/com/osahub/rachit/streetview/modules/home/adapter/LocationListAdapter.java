package com.osahub.rachit.streetview.modules.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.utils.Helper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for Location List on LocationsActivity2
 * Created by Rachit on 22-Apr-16.
 */
public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    private List<Location> mLocations;
    private final OnItemClickListener onItemClickListener;

    public LocationListAdapter(List<Location> locationList, OnItemClickListener itemListener) {
        mLocations = locationList;
        onItemClickListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView descTV;
        ImageView imageIV;
        ImageView favouriteIV;
        ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);
            nameTV = view.findViewById(R.id.location_name);
            descTV = view.findViewById(R.id.location_description);
            imageIV = view.findViewById(R.id.location_image);
            favouriteIV = view.findViewById(R.id.favourite_iv);
            progressBar = view.findViewById(R.id.progress_bar);
        }

        void bind(final Location location, final OnItemClickListener onItemClickListener) {
            if (!location.getLocationName().equals(Helper.LOCATION_FRAGMENT_BLANK_TAG)) {
                nameTV.setText(location.getLocationName());
                nameTV.setSelected(true);
                descTV.setText(location.getDescriptionSmall());
                descTV.setSelected(true);
                favouriteIV.setVisibility(location.isFavourite() ? View.VISIBLE : View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(location.getThumbnailPath())
                        .fit()
                        .into(imageIV, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(location);
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_row_layout, parent, false);
            return new ViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_blank_layout, parent, false);
            return new ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mLocations.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mLocations.get(position).getLocationName().equals(Helper.LOCATION_FRAGMENT_BLANK_TAG)) {
            return 0;
        } else {
            return 1;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Location location);
    }
}