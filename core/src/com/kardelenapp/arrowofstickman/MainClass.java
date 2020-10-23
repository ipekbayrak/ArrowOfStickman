package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by mustafa on 11/5/2017.
 */

public class MainClass extends Game {


    LoadScreen loadScreen;
    LoadingScreen loadingScreen;
    CastleDefence castleDefance;
    Assets assets;
    ButtonFactory buttonFactory;
    FreeFontFactory freeFontFactory ;
    public static MainClass mainClass;

    private Music menuMusic;
    int musicId = 0;
    int maxMusicId = 2;

    private com.kardelenapp.arrowofstickman.Interfaces.ShareLink shareLink;
    private com.kardelenapp.arrowofstickman.Interfaces.InterstitialAdsShow interstitialAdsShow;
    public  MainClass(com.kardelenapp.arrowofstickman.Interfaces.ShareLink shareLink, com.kardelenapp.arrowofstickman.Interfaces.InterstitialAdsShow interstitialAdsShow){
        this.shareLink = shareLink;
        this.interstitialAdsShow = interstitialAdsShow;
    }




    @Override
    public void create() {
        mainClass = this;
        assets = new Assets();
        loadScreen = new LoadScreen( );
        mainClass.setScreen(loadScreen);

        adsPrepare();
       // adsRewardPrepare();
    }

    private void createGameMenu(){
        loadingScreen = new LoadingScreen();
        mainClass.setScreen(loadingScreen);
    }

    public void assetsLoaded(){
        loadScreen.dispose();
        freeFontFactory = new FreeFontFactory();
        buttonFactory = new ButtonFactory();
        createGameMenu();

    }

    public void startGame() {
       // loadingScreen.pause();

        castleDefance = new CastleDefence();
        mainClass.setScreen(castleDefance);

    }

    public void backToMenu() {
        castleDefance.dispose();
        loadingScreen.resume();
        mainClass.setScreen(loadingScreen);


    }

    public void playRandomMuısic(){


            if(menuMusic == null) {
                int selectedMusicId = MathUtils.random(1, maxMusicId);
                menuMusic = mainClass.assets.manager.get("music/" + selectedMusicId + ".ogg", Music.class);
                menuMusic.setVolume(0.03f);
                menuMusic.play();

                menuMusic.setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {

                        playRandomMuısic();

                    }
                });
            }
            else if (!menuMusic.isPlaying()) {
                int selectedMusicId = MathUtils.random(1, maxMusicId);
                menuMusic = mainClass.assets.manager.get("music/" + selectedMusicId + ".ogg", Music.class);
                menuMusic.setVolume(0.06f);
                menuMusic.play();

                menuMusic.setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {

                        playRandomMuısic();

                    }
                });
            }
        else {
                return;
            }

    }

    public void stopMusic(){
        if (menuMusic != null) {
            menuMusic.stop();

            menuMusic = null;
        }
    }


    public void share(){
        shareLink.share();
    }
    public void adsShow(){interstitialAdsShow.showInterstatial();}
    public void adsPrepare(){interstitialAdsShow.prepareInterstatial();}


    public void adsRewardPrepare(){interstitialAdsShow.prepareRewardAds();}
    public void adsRewardShow(){interstitialAdsShow.showRewardAds  ();}

}
