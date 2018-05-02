package com.osahub.rachit.streetview.modules.home.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.osahub.rachit.streetview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rachit Goyal on 01/05/18
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView headerTV;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerTV = itemView.findViewById(R.id.header_tv);
    }

    void bind(boolean isCategory) {
        headerTV.setText(isCategory ? R.string.categories : R.string.locations);
    }
}
