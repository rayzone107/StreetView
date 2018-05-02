package com.osahub.rachit.streetview.modules.detail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.LocationImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rachit Goyal on 28/04/18
 */

public class HeaderImageAdapter extends PagerAdapter {

    private List<LocationImage> mLocationImages;
    private LayoutInflater mLayoutInflater;

    public HeaderImageAdapter(Context context, List<LocationImage> locationImages) {
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
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar);

        Picasso.get()
                .load(mLocationImages.get(position).getImageUrl())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

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
