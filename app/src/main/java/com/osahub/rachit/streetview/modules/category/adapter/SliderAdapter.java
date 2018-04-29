package com.osahub.rachit.streetview.modules.category.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Location;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderCard> {

    private final List<Location> mLocations;
    private final View.OnClickListener mListener;

    public SliderAdapter(List<Location> locations, View.OnClickListener listener) {
        this.mLocations = locations;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public SliderCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_slider_card, parent, false);

        if (mListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(view);
                }
            });
        }

        return new SliderCard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderCard holder, int position) {
        holder.setContent(mLocations.get(position).getThumbnailPath());
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

}
