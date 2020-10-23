package com.kardelenapp.arrowofstickman.Skin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by dell on 09.09.2015.
 */
public class StageSkins {
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;

    public StageSkins(){

        font = new BitmapFont(Gdx.files.internal("bitmapFonts/kaushan.fnt"),
                Gdx.files.internal("bitmapFonts/kaushan.png"), false);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));
        textButtonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));
    }
}
