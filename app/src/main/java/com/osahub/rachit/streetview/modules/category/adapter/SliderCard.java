package com.osahub.rachit.streetview.modules.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.osahub.rachit.streetview.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

class SliderCard extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    ImageView mImageView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    SliderCard(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
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