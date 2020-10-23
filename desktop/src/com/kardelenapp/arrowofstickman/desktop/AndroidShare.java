package com.kardelenapp.arrowofstickman.desktop;


import com.badlogic.gdx.Gdx;
import com.kardelenapp.arrowofstickman.Interfaces.ShareLink;

import java.io.Console;

/**
 * Created by mustafa on 11/7/2017.
 */

public class AndroidShare implements ShareLink {


    public  AndroidShare( ){

    }

    @Override
    public void share() {
        Gdx.app.log("paylasim","paylas");


    }
}