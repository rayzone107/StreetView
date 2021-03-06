package com.osahub.rachit.streetview.modules.streetview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.detail.DetailActivity;
import com.osahub.rachit.streetview.utils.Constants;

public class StreetViewActivity extends BaseActivity implements
        OnStreetViewPanoramaReadyCallback {

    private AppBarLayout mAppBarLayout;

    private Location mLocation;

    StreetViewPanoramaFragment mStreetViewPanoramaFragment;
    StreetViewPanoramaCamera mStreetViewPanoramaCamera;

    public static Intent getStartIntent(Context context, int locationId) {
        Intent intent = new Intent(context, StreetViewActivity.class);
        intent.putExtra(Constants.EXTRAS.LOCATION_ID, locationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);
        Window window = StreetViewActivity.this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.bringToFront();

        if (!isNetworkAvailable()) {
            showDialogForNetwork();
        }

        int locationId = getIntent().getIntExtra(Constants.EXTRAS.LOCATION_ID, 0);
        if (locationId != 0) {
            mLocation = mDatabaseHelper.mLocationDbHelper.getLocationById(locationId);
        } else {
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mLocation.getLocationName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStreetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().
                findFragmentById(R.id.streetViewPanorama);
        mStreetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void showDialogForNetwork() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));
        dialogBuilder.setIcon(R.drawable.internet_icon);
        dialogBuilder.setTitle("Internet Connection Required");
        dialogBuilder.setMessage("An Internet Connection is required to access the panaromic view. Please connect to Mobile Data or Wifi Network");
        dialogBuilder.setPositiveButton("Wifi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 1);
            }
        });
        dialogBuilder.setNegativeButton("Mobile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS), 1);
            }
        });
        dialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        dialogBuilder.setCancelable(true);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
        mStreetViewPanoramaCamera = new StreetViewPanoramaCamera.Builder()
                .bearing(180)
                .build();
        streetViewPanorama.animateTo(mStreetViewPanoramaCamera, 5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_street_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            startActivityForResult(DetailActivity.getStartIntent(StreetViewActivity.this, mLocation.getLocationId()),
                    Constants.REQUEST_CODE.STREET_VIEW_DETAIL);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE.STREET_VIEW_DETAIL) {
            if (resultCode == RESULT_OK) {
                int locationId = data.getIntExtra(Constants.EXTRAS.LOCATION_ID, -1);
            }
        }
    }
}
