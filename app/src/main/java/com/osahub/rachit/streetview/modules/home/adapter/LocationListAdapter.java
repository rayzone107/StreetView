package com.osahub.rachit.streetview.modules.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.Location;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for Location List on LocationsActivity
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
        public TextView name;
        TextView desc;
        public ImageView image;
        ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.location_name);
            desc = view.findViewById(R.id.location_description);
            image = view.findViewById(R.id.location_image);
            progressBar = view.findViewById(R.id.progress_bar);
        }

        void bind(final Location location, final OnItemClickListener onItemClickListener) {
            if (!location.getLocationName().equals(Helper.LOCATION_FRAGMENT_BLANK_TAG)) {
                name.setText(location.getLocationName());
                name.setSelected(true);
                desc.setText(location.getDescriptionSmall());
                desc.setSelected(true);
                progressBar.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(location.getThumbnailPath())
                        .fit()
                        .into(image, new Callback() {
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