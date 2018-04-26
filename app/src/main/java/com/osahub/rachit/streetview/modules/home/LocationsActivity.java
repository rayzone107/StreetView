package com.osahub.rachit.streetview.modules.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.developerprofile.DeveloperProfileActivity;
import com.osahub.rachit.streetview.modules.home.categoryfragment.CategoryFragment;
import com.osahub.rachit.streetview.modules.home.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class LocationsActivity extends BaseActivity
        implements LocationsContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = "World Tour 3D: " + LocationsActivity.class.getSimpleName();
    private static final String SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT_TAG";

    private DrawerLayout mDrawer;
    private FragmentTransaction ft;
    private ProgressBar fragmentsProgressBar;
    private MaterialSearchView mSearchView;
    List<Category> mCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchView = findViewById(R.id.search_view);
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView mNavigationView = findViewById(R.id.nav_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        fragmentsProgressBar = findViewById(R.id.fragments_progress_bar);

        mCategories = mDatabaseHelper.mCategoryDbHelper.getAllCategories();

        Log.i(LOG_TAG, "Categories Fetched");
        setCategoriesList();

        setSearchListeners();
    }

    public void setCategoriesList() {
        fragmentsProgressBar.setVisibility(View.GONE);

        ft = getSupportFragmentManager().beginTransaction();
        for (Category category : mCategories) {
            if (mDatabaseHelper.mLocationDbHelper.getLocationCountByCategoryId(category.getCategoryId()) != 0) {
                CategoryFragment categoryFragmentOld = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(category.getName());
                if (categoryFragmentOld == null) {
                    CategoryFragment categoryFragment = new CategoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("category", category.getCategoryId());
                    categoryFragment.setArguments(bundle);
                    ft.add(R.id.fragment_holder, categoryFragment, category.getName());
                }
            }
        }
        ft.commit();
    }

    private void setSearchListeners() {
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
                SearchFragment searchFragment = new SearchFragment();
                ft.replace(R.id.search_fragment_container, searchFragment, SEARCH_FRAGMENT_TAG);
                ft.commit();
            }

            @Override
            public void onSearchViewClosed() {
                ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(SEARCH_FRAGMENT_TAG);
                if (fragment != null) {
                    ft.remove(fragment);
                }
                ft.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            Intent intent = new Intent(LocationsActivity.this, DeveloperProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_other_apps) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Rachit+Goyal"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_share) {
            otherApps();
        } else if (id == R.id.nav_rate) {
            rateApp();
        } else if (id == R.id.nav_email) {
            sendEmail();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void otherApps() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.osahub.rachit.streetview");
        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }

    protected void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
        }
    }

    protected void sendEmail() {
        Log.i(LOG_TAG, "Sending Email.");
        String[] TO = {"rachit@osahub.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "rachit@osahub.com", null));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "World Tour: Query");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail"));
            Log.i(LOG_TAG, "Email Sent.");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LocationsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_list, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }
}