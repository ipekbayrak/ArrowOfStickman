package com.kardelenapp.arrowofstickman;

import android.content.Context;
import android.content.Intent;

import com.kardelenapp.arrowofstickman.Interfaces.ShareLink;

/**
 * Created by mustafa on 11/7/2017.
 */

public class AndroidShare implements ShareLink {

    Context context;
    public  AndroidShare(Context context){
        this.context = context;
    }

    @Override
    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey! Check this game");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.kardelenapp.arrowofstickman");
        this.context.startActivity(Intent.createChooser(sharingIntent, "Share Via"));


    }
}