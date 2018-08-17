package com.sgsj.togup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class DashboardFragment extends Fragment {

    private Button showAdButton;
    private InterstitialAd fullAd;
    private TextView countClick, countImpression;
    private int click, impression;
    private RewardedVideoAd mRewardedVideoAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        MobileAds.initialize(view.getContext(),getString(R.string.adMobID));

        showAdButton = view.findViewById(R.id.dashboard_get_ad_button);
        fullAd = new InterstitialAd(view.getContext());
        fullAd.setAdUnitId(getString(R.string.interstitialAd));
        fullAd.loadAd(new AdRequest.Builder().build());

        click = 0;
        impression = 0;

        countClick = view.findViewById(R.id.dashboard_variable_counter_click);
        countImpression = view.findViewById(R.id.dashboard_variable_counter_impression);
        countClick.setText(click+"");
        countImpression.setText(impression+"");


        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullAd.isLoaded()) {
                    fullAd.show();
                } else {
                    Toast.makeText(view.getContext(), "Ad not loaded, Check your internet connection", Toast.LENGTH_SHORT ).show();
                }
            }
        });

        fullAd.setAdListener(new AdListener(){

            @Override
            public void onAdLeftApplication() {
                click++;
                super.onAdLeftApplication();
            }

            @Override
            public void onAdClosed() {
                impression++;
                countClick.setText(click+ "");
                countImpression.setText(impression + "");
                fullAd.loadAd(new AdRequest.Builder().build());
                super.onAdClosed();

            }
        });

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(view.getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                mRewardedVideoAd.loadAd(getString(R.string.rewardedVideoId), new AdRequest.Builder().build());
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });

        mRewardedVideoAd.loadAd(getString(R.string.rewardedVideoId), new AdRequest.Builder().build());
        if (mRewardedVideoAd.isLoaded() && (click == 9||impression == 10)) {
            mRewardedVideoAd.show();
        }


        return view;
    }

}
