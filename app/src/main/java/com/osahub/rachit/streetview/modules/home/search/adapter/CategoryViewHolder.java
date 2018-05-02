package com.osahub.rachit.streetview.modules.home.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.model.Category;
import com.osahub.rachit.streetview.utils.Constants;

/**
 * Created by Rachit Goyal on 30/04/18
 */

class CategoryViewHolder extends RecyclerView.ViewHolder {

    TextView categoryNameTV;
    ImageView categoryIconIV;

    CategoryViewHolder(View view) {
        super(view);

        categoryNameTV = view.findViewById(R.id.category_name_tv);
        categoryIconIV = view.findViewById(R.id.category_icon_iv);
    }

    void bind(final Category category, final SearchItemAdapter.OnItemClickListener onItemClickListener) {
        switch (category.getType()) {
            case Constants.CATEGORY_TYPE.COUNTRY:
                categoryIconIV.setImageResource(R.drawable.ic_globe);
                break;
            case Constants.CATEGORY_TYPE.SPECIAL:
                categoryIconIV.setImageResource(R.drawable.ic_map);
                break;
        }
        categoryNameTV.setText(category.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onCategoryClick(category);
            }
        });
    }
}
