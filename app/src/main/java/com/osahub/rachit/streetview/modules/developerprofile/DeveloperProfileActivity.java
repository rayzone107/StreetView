package com.osahub.rachit.streetview.modules.developerprofile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.modules.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeveloperProfileActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DeveloperProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_profile);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openWebsite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rachitgoyal.com"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.gaze_away_rl)
    public void onGazeAwayClick(View view) {
        openURL("https://play.google.com/store/apps/details?id=com.rachitgoyal.screentimer&hl=en");
    }

    @OnClick(R.id.world_tour_rl)
    public void onWorldTourClick(View view) {

    }

    @OnClick(R.id.upgrad_rl)
    public void onUpGradClick(View view) {
        openURL("https://play.google.com/store/apps/details?id=com.upgrad.student");
    }

    @OnClick(R.id.show_time_rl)
    public void onShowTimeClick(View view) {
        openURL("https://play.google.com/store/apps/details?id=com.osahub.rachit.showtime&hl=en");
    }

    @OnClick(R.id.duration_view_rl)
    public void onDurationViewClick(View view) {
        openURL("https://play.google.com/store/apps/details?id=com.rachitgoyal.durationview");
    }

    private void openURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.phone_tv)
    public void onPhoneClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+919717982464"));
        startActivity(intent);
    }

    @OnClick(R.id.email_tv)
    public void onEmailClick(View view) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"rachitgoy@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Regarding World Tour 3D");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }

    @OnClick(R.id.website_tv)
    public void onWebsiteClick(View view) {
        openURL("http://www.rachitgoyal.com");
    }
}
