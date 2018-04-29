package com.osahub.rachit.streetview.modules.developerprofile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.modules.base.BaseActivity;

public class DeveloperProfileActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DeveloperProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openWebsite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.osahub.com/about-us.html"));
        startActivity(browserIntent);
    }
}
