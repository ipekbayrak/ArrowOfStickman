package com.kardelenapp.arrowofstickman.desktop;


import com.badlogic.gdx.Gdx;

import com.kardelenapp.arrowofstickman.Interfaces.InterstitialAdsShow;


/**
 * Created by mustafa on 11/7/2017.
 */

public class AdsController implements InterstitialAdsShow{



    public AdsController( ) {

    }


    public void RevardedVideoAdsPrepare(){



        Gdx.app.log("paylasim","paylas");



    }

    public void InterstitialAdsPrepare(){

        Gdx.app.log("paylasim","paylas");

    }

    public void showInterstatialAds(){

        Gdx.app.log("paylasim","paylas");
    }

    public void InterstitialLoad(){


    }
    public void loadRevardedVideo(){


    }


    public void  showRevardedVideo(){
        Gdx.app.log("paylasim","paylas");

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
