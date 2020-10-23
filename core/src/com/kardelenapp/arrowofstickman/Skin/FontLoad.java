package com.kardelenapp.arrowofstickman.Skin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by dell on 08.09.2015.
 */
public class FontLoad{
    private BitmapFont font;
    //fnt path "bitmapFonts/kaushan.fnt"
    //png path "bitmapFonts/kaushan.png"
    String fntPath= "bitmapFonts/kaushan.fnt";
    String pngPath= "bitmapFonts/kaushan.png";

    public FontLoad(){
        this.font = new BitmapFont(Gdx.files.internal(fntPath),Gdx.files.internal(pngPath), false);
    }

    public BitmapFont getFont(){
        return this.font;
    }
}
