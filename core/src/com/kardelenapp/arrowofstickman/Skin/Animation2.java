package com.kardelenapp.arrowofstickman.Skin;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by dell on 2.05.2016.
 */
public class Animation2 extends Animation {

   public int i = 0;

    public Animation2(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
        i = 0;
    }




}
