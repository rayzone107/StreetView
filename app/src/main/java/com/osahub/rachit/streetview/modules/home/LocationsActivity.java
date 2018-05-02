package com.osahub.rachit.streetview.modules.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.misc.searchview.MySearchView;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.category.SingleCategoryActivity;
import com.osahub.rachit.streetview.modules.developerprofile.DeveloperProfileActivity;
import com.osahub.rachit.streetview.modules.home.categoryfragment.CategoryFragment;
import com.osahub.rachit.streetview.modules.home.search.SearchFragment;
import com.osahub.rachit.streetview.server.NetworkRequest;
import com.osahub.rachit.streetview.service.FetchDataIntentService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsActivity extends BaseActivity implements LocationsContract.View,
        OnNavigationItemSelectedListener {
    private static final String SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT_TAG";

    @BindView(R.id.loading_parent_rl)
    RelativeLayout mLoadingRL;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.fragments_progress_bar)
    ProgressBar fragmentsProgressBar;

    @BindView(R.id.search_view)
    MySearchView mMySearchView;

    private LocationsContract.Presenter mPresenter;
    private List<Category> mCategories = new ArrayList<>();
    private SearchFragment mSearchFragment;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        FetchDataIntentService.startService(mContext);

        mMySearchView.setHint("Search Locations");
        mMySearchView.setHintTextColor(Color.BLACK);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        mPresenter = new LocationsPresenter(this, mDatabaseHelper, new NetworkRequest());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    public void changeViewState(ViewState viewState) {
        switch (viewState) {
            case LOADING:
                mLoadingRL.setVisibility(View.VISIBLE);
                break;
            case DATA:
                mLoadingRL.setVisibility(View.GONE);
                break;
            case ERROR:
                mLoadingRL.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showData(List<Category> categories) {
        mCategories = categories;
        setCategoriesList();
        setSearchListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setCategoriesList() {
        fragmentsProgressBar.setVisibility(View.GONE);

        ft = getSupportFragmentManager().beginTransaction();
        for (Category category : mCategories) {
            if (mDatabaseHelper.mLocationDbHelper.getLocationCountByCategoryId(category.getCategoryId()) != 0) {
                CategoryFragment categoryFragmentOld = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(category.getName());
                if (categoryFragmentOld == null) {
                    CategoryFragment categoryFragment = CategoryFragment.newInstance(category.getCategoryId());
                    ft.add(R.id.fragment_holder, categoryFragment, category.getName());
                }
            }
        }
        ft.commit();
    }

    private void setSearchListeners() {
        mMySearchView.setOnQueryTextListener(new MySearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mSearchFragment != null) {
                    mSearchFragment.changeSearchText(newText);
                }
                return false;
            }
        });

        mMySearchView.setOnSearchViewListener(new MySearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                mNestedScrollView.setVisibility(View.GONE);
                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
                mSearchFragment = SearchFragment.newInstance();
                ft.replace(R.id.search_fragment_container, mSearchFragment, SEARCH_FRAGMENT_TAG);
                ft.commit();
            }

            @Override
            public void onSearchViewClosed() {
                mNestedScrollView.setVisibility(View.VISIBLE);
                ft = getSupportFragmentManager().beginTransaction();
                if (mSearchFragment != null && mSearchFragment.isAdded()) {
                    ft.remove(mSearchFragment);
                }
                ft.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_list, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mMySearchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_favourites:
                SingleCategoryActivity.startActivity(LocationsActivity.this);
                break;
            case R.id.nav_profile:
                DeveloperProfileActivity.startActivity(LocationsActivity.this);
                break;
            case R.id.nav_other_apps:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Rachit+Goyal")));
                break;
            case R.id.nav_share:
                shareApps();
                break;
            case R.id.nav_rate:
                rateApp();
                break;
            case R.id.nav_email:
                sendEmail();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareApps() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.osahub.rachit.streetview");
        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }

    private void rateApp() {
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

    private void sendEmail() {
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
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LocationsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (mMySearchView.isSearchOpen()) {
            mMySearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
