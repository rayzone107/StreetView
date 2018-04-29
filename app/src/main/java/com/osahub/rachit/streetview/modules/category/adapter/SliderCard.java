package com.osahub.rachit.streetview.modules.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.osahub.rachit.streetview.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

class SliderCard extends RecyclerView.ViewHolder {

    private int viewWidth = 0;

    private final ImageView mImageView;
    private final ProgressBar mProgressBar;

    SliderCard(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.image);
        mProgressBar = itemView.findViewById(R.id.progress_bar);
    }

    void setContent(final String imageUrl) {
        mProgressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop(Gravity.CENTER)
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }
}