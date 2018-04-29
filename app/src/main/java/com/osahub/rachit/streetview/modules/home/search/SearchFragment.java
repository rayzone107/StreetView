package com.osahub.rachit.streetview.modules.home.search;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.modules.base.BaseFragment;

public class SearchFragment extends BaseFragment {

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    public void changeSearchText(String newText) {
        Toast.makeText(mContext, "Change Search - " + newText, Toast.LENGTH_SHORT).show();
    }
}
