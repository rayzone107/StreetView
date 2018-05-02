package com.osahub.rachit.streetview.modules.home.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.model.Location;
import com.osahub.rachit.streetview.modules.base.BaseActivity.ViewState;
import com.osahub.rachit.streetview.modules.base.BaseFragment;
import com.osahub.rachit.streetview.modules.category.SingleCategoryActivity;
import com.osahub.rachit.streetview.modules.home.search.adapter.SearchItemAdapter;
import com.osahub.rachit.streetview.modules.streetview.StreetViewActivity;
import com.osahub.rachit.streetview.server.NetworkRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment implements SearchContract.View, SearchItemAdapter.OnItemClickListener {

    @BindView(R.id.parent_rl)
    RelativeLayout mParentRL;
    @BindView(R.id.search_list_rv)
    RecyclerView mSearchListRV;
    @BindView(R.id.error_layout)
    LinearLayout mErrorLayout;
    @BindView(R.id.error_icon)
    ImageView mErrorIcon;
    @BindView(R.id.error_lav)
    LottieAnimationView mErrorLAV;
    @BindView(R.id.error_message)
    TextView mErrorMessage;
    @BindView(R.id.error_btn)
    Button mErrorButton;

    private SearchContract.Presenter mPresenter;
    private SearchItemAdapter mAdapter;
    private List<Category> mSearchedCategories = new ArrayList<>();
    private List<Location> mSearchedLocations = new ArrayList<>();
    private String mSearchString;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchPresenter(this, mDatabaseHelper, new NetworkRequest());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new SearchItemAdapter(mSearchedCategories, mSearchedLocations, this);
        mSearchListRV.setAdapter(mAdapter);
        mSearchListRV.setLayoutManager(new LinearLayoutManager(mContext));
        changeViewState(ViewState.LOADING);
        return view;
    }

    public void changeSearchText(String newText) {
        mSearchString = newText;
        mPresenter.searchStringChange(newText);
    }

    @Override
    public void changeViewState(ViewState viewState) {
        switch (viewState) {
            case LOADING:
                mSearchListRV.setVisibility(View.GONE);
                mErrorLayout.setVisibility(View.VISIBLE);
                mErrorLAV.setVisibility(View.GONE);
                mErrorIcon.setVisibility(View.VISIBLE);
                mErrorIcon.setImageResource(R.drawable.ic_location_search);
                mErrorMessage.setText(R.string.search_by_name_or_country);
                mErrorButton.setVisibility(View.GONE);
                break;
            case DATA:
                mSearchListRV.setVisibility(View.VISIBLE);
                mErrorLayout.setVisibility(View.GONE);
                break;
            case ERROR:
                mSearchListRV.setVisibility(View.GONE);
                mErrorLayout.setVisibility(View.VISIBLE);
                mErrorIcon.setVisibility(View.GONE);
                mErrorLAV.setVisibility(View.VISIBLE);
                mErrorLAV.setAnimation(R.raw.empty);
                mErrorLAV.playAnimation();
                mErrorMessage.setText(R.string.couldnt_find_location_error_message);
                mErrorButton.setVisibility(View.VISIBLE);
                mErrorButton.setText(getString(R.string.request_location));
                break;
        }
    }

    @Override
    public void updateSearchedList(List<Category> searchedCategoryList, List<Location> searchedLocationList) {
        mErrorLayout.setVisibility(View.GONE);
        mSearchedCategories.clear();
        mSearchedLocations.clear();
        mSearchedCategories.addAll(searchedCategoryList);
        mSearchedLocations.addAll(searchedLocationList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRequestResponse(boolean isSuccessful) {
        Snackbar.make(mParentRL, getString(isSuccessful ? R.string.request_location_success :
                R.string.request_location_failed), Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.error_btn)
    public void onErrorButtonClick(View view) {
        mPresenter.onRequestLocationClicked(mSearchString);
    }

    @Override
    public void onCategoryClick(Category category) {
        SingleCategoryActivity.startActivity(mContext, category.getCategoryId());
    }

    @Override
    public void onLocationClick(Location location) {
        startActivity(StreetViewActivity.getStartIntent(mContext, location.getLocationId()));
    }
}
