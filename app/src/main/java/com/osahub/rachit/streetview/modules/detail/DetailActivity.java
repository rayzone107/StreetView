package com.osahub.rachit.streetview.modules.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.gallery.GalleryActivity;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DetailActivity extends BaseActivity {

    public static final String BUNDLE_IMAGE_ID = "BUNDLE_IMAGE_ID";
    Location mLocation;
    ImageView mImageView;
    ImageButton mGallery;
    TextView mDescription, mCityStateCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mLocation = (Location) getIntent().getSerializableExtra("location");

        Toolbar toolbar = findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(mLocation.getLocationName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImageView = findViewById(R.id.image);
        mGallery = findViewById(R.id.gallery);
        mCityStateCountry = findViewById(R.id.city_state_country);
        mDescription = findViewById(R.id.description);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;

        int resizeWidth = (int) ((dpWidth - 32) * density);
        int resizeHeight = (int) ((736 * (dpWidth / 1024)) * density);

        List<String> imagePaths = Arrays.asList(mLocation.getImageLinks().split("\\s*,\\s*"));

        Picasso.get()
                .load(imagePaths.get(new Random().nextInt(imagePaths.size() - 1) + 1))
                .resize(resizeWidth, resizeHeight)
                .placeholder(R.drawable.progress_animation2)
                .into(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, GalleryActivity.class);
                intent.putExtra("location", mLocation.getLocationId());
                startActivity(intent);
            }
        });

        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, GalleryActivity.class);
                intent.putExtra("location", mLocation.getLocationId());
                startActivity(intent);
            }
        });

        String city, state, country, cityStateCountry;
        country = mLocation.getCountry().equals("") ? "" : mLocation.getCountry();
        if (country.equals("")) {
            state = mLocation.getState().equals("") ? "" : mLocation.getState();
        } else {
            state = mLocation.getState().equals("") ? "" : mLocation.getState() + ", ";
        }

        if (state.equals("") && country.equals("")) {
            city = mLocation.getCity().equals("") ? "" : mLocation.getCity();
        } else {
            city = mLocation.getCity().equals("") ? "" : mLocation.getCity() + ", ";
        }

        cityStateCountry = city + state + country;
        // TODO: Improve to below code.
        /*cityStateCountry = mLocation.getCity() + (mLocation.getCity().isEmpty() ? "" : ", ")
                + mLocation.getState() + (mLocation.getState().isEmpty() ? "" : ", ")
                + mLocation.getCountry();*/

        mCityStateCountry.setText(cityStateCountry);
        mDescription.setText(String.format("%s\n\n\n\n", mLocation.getDescription()));
    }
}