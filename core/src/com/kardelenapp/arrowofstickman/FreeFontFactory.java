package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by mustafa on 11/4/2017.
 */

public class FreeFontFactory {

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font12;
    BitmapFont font24;
    BitmapFont font48;
    BitmapFont font72;

    Constant c = new Constant();

    public  FreeFontFactory(){
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/neuropolxrg.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.size = 24;
        parameter.size = 48;
        parameter.size = (int) c.oyunYukseklik/12;

        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;

        font12 = generator.generateFont(parameter); // font size 12 pixels
        font24 = generator.generateFont(parameter);
        font48 = generator.generateFont(parameter);
        font72 = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


    }

    public BitmapFont getFont12(){
        return font12;
    }

    public BitmapFont getFont24(){
        return font24;
    }

    public BitmapFont getFont48(){
        return font48;
    }

    public BitmapFont getFont72(){
        return font72;
    }
}
