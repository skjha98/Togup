package com.sgsj.togup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class DashboardFragment extends Fragment {
    private EditText memail, mpassword, mphone,mconfirm,mname;
    private Button showAdButton;
    private InterstitialAd fullAd;
    private TextView countClick, countImpression;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int click, impression;
    private RewardedVideoAd mRewardedVideoAd;
    private DatabaseReference mdatabase;

    //Banner
    private AdView topAd, center1Ad, center2Ad, bottomAd;

    @Override
    public void onResume() {
        super.onResume();
        update2firebase(click, impression);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        MobileAds.initialize(view.getContext(),getString(R.string.adMobID));

        //Banners Start-Up
        topAd = view.findViewById(R.id.dashboard_ad_top);
        center1Ad = view.findViewById(R.id.dashboard_ad_center1);
        center2Ad = view.findViewById(R.id.dashboard_ad_center2);
        bottomAd = view.findViewById(R.id.dashboard_ad_bottom);
        AdRequest adRequestBanner = new AdRequest.Builder().build();
        topAd.loadAd(adRequestBanner);
        center1Ad.loadAd(adRequestBanner);
        center2Ad.loadAd(adRequestBanner);
        bottomAd.loadAd(adRequestBanner);

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
                update2firebase(click,impression);
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

    private void update2firebase(int click, int impression){
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        if(current_user == null){
            Toast.makeText(getActivity(), "Pta nhi kya chal rha h", Toast.LENGTH_SHORT).show();

        }else {
            String uid = current_user.getUid();

            mdatabase = FirebaseDatabase.getInstance().getReference().child("RECORDS").child(uid).child(todayDate());
            HashMap<String, String> usermap = new HashMap<>();
            int a = click;
            int b = impression;
            usermap.put("CLICK", String.valueOf(a));
            usermap.put("IMPRESSION", String.valueOf(b));

            mdatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }
                }
            });
        }



    }

    String todayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
