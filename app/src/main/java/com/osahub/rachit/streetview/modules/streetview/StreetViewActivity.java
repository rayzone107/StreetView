package com.osahub.rachit.streetview.modules.streetview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.detail.DetailActivity;

public class StreetViewActivity extends AppCompatActivity implements
        OnStreetViewPanoramaReadyCallback {

    private static final String LOG_TAG = "World Tour 3D: " + StreetViewActivity.class.getSimpleName();

    private Location mLocation;

    StreetViewPanoramaFragment mStreetViewPanoramaFragment;
    StreetViewPanoramaCamera mStreetViewPanoramaCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);

        if (!isNetworkAvailable()) {
            showDialogForNetwork();
        }

        mLocation = (Location) getIntent().getSerializableExtra("location");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(mLocation.getLocationName());
        toolbar.setNavigationIcon(R.drawable.street_view_icon);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            Intent intent = new Intent(StreetViewActivity.this, DetailActivity.class);
            intent.putExtra("location", mLocation);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
