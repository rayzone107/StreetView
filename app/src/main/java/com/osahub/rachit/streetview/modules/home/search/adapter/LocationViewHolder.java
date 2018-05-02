package com.osahub.rachit.streetview.modules.home.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Location;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rachit Goyal on 30/04/18
 */

class LocationViewHolder extends RecyclerView.ViewHolder {

    CircleImageView thumbnailCIV;
    ImageView favouriteIV;
    TextView locationNameTV;
    TextView locationDescTV;

    LocationViewHolder(View view) {
        super(view);
        thumbnailCIV = view.findViewById(R.id.thumbnail_civ);
        favouriteIV = view.findViewById(R.id.favourite_iv);
        locationNameTV = view.findViewById(R.id.location_name_tv);
        locationDescTV = view.findViewById(R.id.location_short_desc_tv);
    }

    void bind(final Location location, final SearchItemAdapter.OnItemClickListener onItemClickListener) {
        locationNameTV.setText(location.getLocationName());
        locationDescTV.setText(location.getDescriptionSmall());
        Picasso.get().load(location.getThumbnailPath())
                .fit().centerCrop().into(thumbnailCIV);
        favouriteIV.setVisibility(location.isFavourite() ? View.VISIBLE : View.GONE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onLocationClick(location);
            }
        });
    }
}
