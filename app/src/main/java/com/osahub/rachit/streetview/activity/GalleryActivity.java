package com.osahub.rachit.streetview.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.misc.HackyViewPager;
import com.osahub.rachit.streetview.misc.Helper;
import com.osahub.rachit.streetview.model.Location;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<ImageView> mDots;
    private List<String> mImageLinks;
    FloatingActionButton mFab;
    int mCurrentImage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Location mLocation = (Location) getIntent().getSerializableExtra("location");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mLocation.getLocationName() + " Gallery");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFab = findViewById(R.id.fab);

        mImageLinks = Helper.generateImageLinksArrayFromLinksString(mLocation.getImageLinks());

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareItem(mImageLinks.get(0));
            }
        });

        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new SamplePagerAdapter());
        addDots(mViewPager.getCurrentItem());
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageLinks.size();
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, final int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.CENTER);
            final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
            Picasso.get()
                    .load(mImageLinks.get(position))
                    .placeholder(ContextCompat.getDrawable(getApplicationContext(), R.drawable.progress_animation_white))
                    .into(photoView, new Callback() {
                        @Override
                        public void onSuccess() {
                            attacher.update();
                            mCurrentImage = position;
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

    }

    public void addDots(final int currentItem) {
        mDots = new ArrayList<>();
        LinearLayout dotsLayout = findViewById(R.id.dots);

        for (int i = 0; i < mImageLinks.size(); i++) {
            final ImageView dot = new ImageView(this);
            dot.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_dark));
            dot.setContentDescription(String.valueOf(i));
            if (i == 0) {
                dot.setPadding(15, 15, 7, 15);
            } else if (i == mImageLinks.size() - 1) {
                dot.setPadding(7, 15, 15, 15);
            } else {
                dot.setPadding(7, 15, 7, 15);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int dotPosition = Integer.valueOf(dot.getContentDescription().toString());
                    mViewPager.setCurrentItem(dotPosition, true);
                    selectDot(dotPosition);
                }
            });

            dotsLayout.addView(dot, params);

            mDots.add(dot);
        }

        selectDot(currentItem);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                selectDot(position);
                mFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareItem(mImageLinks.get(position));
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void selectDot(int idx) {
        for (int i = 0; i < mImageLinks.size(); i++) {
            int drawableId = (i == idx) ? (R.drawable.dot_white) : (R.drawable.dot_dark);
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), drawableId);
            mDots.get(i).setImageDrawable(drawable);
        }
    }

    public void shareItem(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, Helper.getLocalBitmapUri(GalleryActivity.this, bitmap));
                startActivityForResult(Intent.createChooser(i, "Share Image"), 0);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}