package com.osahub.rachit.streetview.modules.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.osahub.rachit.streetview.utils.Constants;

/**
 * Created by Rachit Goyal on 02/05/18
 */

public class DataUpdatedBroadcastReceiver extends BroadcastReceiver {

    private DataSetListener mListener;

    public DataUpdatedBroadcastReceiver(DataSetListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isSuccessful = intent.getBooleanExtra(Constants.EXTRAS.DATA_SET, false);
        if (isSuccessful) {
            mListener.onDataSet();
        } else {
            mListener.onError();
        }
    }

    public interface DataSetListener {
        void onDataSet();

        void onError();
    }
}
