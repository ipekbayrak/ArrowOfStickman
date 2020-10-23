package com.kardelenapp.arrowofstickman;

import android.content.Context;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.kardelenapp.arrowofstickman.Interfaces.InterstitialAdsShow;

import static com.google.android.gms.internal.zzagr.runOnUiThread;


/**
 * Created by mustafa on 11/7/2017.
 */

public class AdsController implements InterstitialAdsShow{
    String bannerAdsTEST = "ca-app-pub-3940256099942544/6300978111";
    String InterstitialAdsTEST = "ca-app-pub-3940256099942544/1033173712";
    String Rewarded_Video_TEST = "ca-app-pub-3940256099942544/5224354917";
    String Native_Advanced_TEST = "ca-app-pub-3940256099942544/2247696110";
    String Native_Express_Small_TEST = ":ca-app-pub-3940256099942544/2793859312";
    String Native_Express_Large_TEST = "ca-app-pub-3940256099942544/2177258514";

    String fullscreeenads = "ca-app-pub-3312738864772003/6016611306";
    String videoads = "ca-app-pub-3312738864772003/5957380486";

    Context context;

    public AdsController(Context context) {
        this.context = context;
    }


    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAdListener listener;
    public void RevardedVideoAdsPrepare(){

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);



        listener = new RewardedVideoAdListener() {
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
                loadRevardedVideo();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                loadRevardedVideo();
            }
        };



        loadRevardedVideo();


    }

    public void InterstitialAdsPrepare(){


        mInterstitialAd = new InterstitialAd(context);

        if (BuildConfig.DEBUG) {
            mInterstitialAd.setAdUnitId(InterstitialAdsTEST);
        }
        else {
            mInterstitialAd.setAdUnitId(fullscreeenads);
        }


        InterstitialLoad();



        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                InterstitialLoad();
            }
        });
    }

    public void showInterstatialAds(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

    }

    public void InterstitialLoad(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

    }
    public void loadRevardedVideo(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRewardedVideoAd.setRewardedVideoAdListener(listener);

                if (BuildConfig.DEBUG) {
                    mRewardedVideoAd.loadAd(Rewarded_Video_TEST, new AdRequest.Builder().build());
                }
                else {
                    mRewardedVideoAd.loadAd(videoads, new AdRequest.Builder().build());
                }


            }
        });

    }


    public void  showRevardedVideo(){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mRewardedVideoAd.isLoaded())
                    {mRewardedVideoAd.show();}
                }
            });
    }







    @Override
    public void showInterstatial() {
        showInterstatialAds();
    }

    @Override
    public void prepareInterstatial() {
        InterstitialAdsPrepare();
    }

    @Override
    public void showRewardAds() {
        showRevardedVideo();
    }

    @Override
    public void prepareRewardAds() {
        RevardedVideoAdsPrepare();
    }

}
