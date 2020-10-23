package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by mustafa on 11/2/2017.
 */

public class Constant {
    Preferences prefs ;

    final float PIXELS_TO_METERS = 100f;
    final short YAY_ENTITY = 0x0001;    // 0001
    final short WORLD_ENTITY = -1; // 0010 or 0x2 in hex
    final short STICK_ENTITY = 0x0010;
    final short ARROW_ENTITY = 0x0100;
    final short PLATFORM_ENTITY = 0x1100;
    final short ELMA_ENTTY = 0x1101;

    private float elapsed = 0;
    float torque = 0.0f;
    int highScore = 0;
    boolean sound = true;

    float ekranGenislik = Gdx.graphics.getWidth();
    float ekranYukseklik =  Gdx.graphics.getHeight();

    float oyunGenislik =1920;
    float oyunYukseklik =1080;


    private OrthographicCamera cam;


    public  Constant (){
        try{
            prefs = Gdx.app.getPreferences("Preferences");
            highScore = prefs.getInteger("highScore",0);
            sound = prefs.getBoolean("sound",true);
        }catch (Exception e){
            Gdx.app.log("error",e.toString(),e);
        }





    }


    public void setHighScore(int highScore) {
        Preferences prefs = Gdx.app.getPreferences("Preferences");
        prefs.putInteger("highScore",highScore);
        prefs.flush();

        this.highScore = highScore;
    }


    public void setSound(boolean sound) {
        Preferences prefs = Gdx.app.getPreferences("Preferences");
        prefs.putBoolean("sound",sound);
        prefs.flush();

        this.sound = sound;
    }


}
