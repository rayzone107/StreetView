package com.osahub.rachit.streetview.modules.splash;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.osahub.rachit.streetview.R;
import com.osahub.rachit.streetview.modules.base.BaseActivity;
import com.osahub.rachit.streetview.modules.home.receiver.DataUpdatedBroadcastReceiver;
import com.osahub.rachit.streetview.modules.home.receiver.DataUpdatedBroadcastReceiver.DataSetListener;
import com.osahub.rachit.streetview.service.FetchDataIntentService;
import com.osahub.rachit.streetview.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity implements SplashContract.View, DataSetListener {

    @BindView(R.id.error_layout)
    LinearLayout mErrorLayout;

    @BindView(R.id.error_lav)
    LottieAnimationView mErrorLAV;

    @BindView(R.id.error_message)
    TextView mErrorMessage;

    @BindView(R.id.error_btn)
    Button mErrorBtn;

    private SplashContract.Presenter mPresenter;
    private DataUpdatedBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mReceiver = new DataUpdatedBroadcastReceiver(this);

        mPresenter = new SplashPresenter(this, mDatabaseHelper);
        mPresenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(Constants.ACTION.DATA_UPDATED));
        FetchDataIntentService.startService(mContext);
    }

    @Override
    public void changeViewState(ViewState viewState) {
        switch (viewState) {
            case LOADING:
                mErrorLayout.setVisibility(View.GONE);
                break;
            case DATA:
                mErrorLayout.setVisibility(View.GONE);
                break;
            case ERROR:
                mErrorLayout.setVisibility(View.VISIBLE);
                mErrorLAV.setVisibility(View.VISIBLE);
                mErrorLAV.setAnimation(R.raw.error_message);
                mErrorLAV.playAnimation();
                mErrorBtn.setVisibility(View.VISIBLE);
                mErrorBtn.setText(R.string.retry);
                mErrorMessage.setText(R.string.could_not_connect);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    @OnClick(R.id.error_btn)
    public void onRetryClick(View view) {
        mPresenter.onCreate();
    }

    @Override
    public void startLocationActivity() {
        FetchDataIntentService.startService(mContext);
        finish();
    }

    @Override
    public void onDataSet() {
        startLocationActivity();
    }

    @Override
    public void onError() {
        changeViewState(ViewState.ERROR);
    }
}