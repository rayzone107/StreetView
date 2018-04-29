package com.osahub.rachit.streetview.modules.detail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.LocationImages;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rachit Goyal on 28/04/18
 */

public class HeaderImageAdapter extends PagerAdapter {

    private List<LocationImages> mLocationImages;
    private LayoutInflater mLayoutInflater;

    public HeaderImageAdapter(Context context, List<LocationImages> locationImages) {
        mLocationImages = locationImages;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mLocationImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.item_detail_header, container, false);
        ImageView imageView = view.findViewById(R.id.view_pager_iv);

        Picasso.get()
                .load(mLocationImages.get(position).getImageUrl())
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
