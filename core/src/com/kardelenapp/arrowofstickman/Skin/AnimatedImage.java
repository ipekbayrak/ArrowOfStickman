package com.kardelenapp.arrowofstickman.Skin;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by dell on 09.09.2015.
 */
public class AnimatedImage extends Image
{
    protected Animation animation = null;
    private float stateTime = 0;

    public AnimatedImage(Animation animation) {
        super((Texture) animation.getKeyFrame(0));
        this.animation = animation;
    }

    @Override
    public void act(float delta)
    {
        ((TextureRegionDrawable)getDrawable()).setRegion((TextureRegion) animation.getKeyFrame(stateTime+=delta, true));
        super.act(delta);
    }
}