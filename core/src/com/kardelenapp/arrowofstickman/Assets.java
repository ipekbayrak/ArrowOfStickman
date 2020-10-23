package com.kardelenapp.arrowofstickman;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {
    public  AssetManager manager = new AssetManager();

    public  void queueLoading() {

        manager.load("cubuk.png", Texture.class);
        manager.load("ok.png", Texture.class);
        manager.load("part.jpg", Texture.class);
        manager.load("part2.jpg", Texture.class);

        manager.load("arrowto.png", Texture.class);
        manager.load("yeniOk.png", Texture.class);

        manager.load("part3.png", Texture.class);
        manager.load("platform.jpg", Texture.class);
        manager.load("tuy.png", Texture.class);
        manager.load("yay.png", Texture.class);
        manager.load("ok.png", Texture.class);
        manager.load("play.png", Texture.class);
        manager.load("soundOn.png", Texture.class);
        manager.load("soundOff.png", Texture.class);
        manager.load("background.png", Texture.class);

        manager.load("refresh.png", Texture.class);
        manager.load("exit.png", Texture.class);
        manager.load("share.png", Texture.class);

        manager.load("body.png", Texture.class);
        manager.load("part2.png", Texture.class);
        manager.load("part3.png", Texture.class);
        manager.load("platform.png", Texture.class);
        manager.load("play_mini.png", Texture.class);
        manager.load("elma.png", Texture.class);
        manager.load("elmaKapali.png", Texture.class);
        manager.load("elmaAcik.png", Texture.class);
        manager.load("yes.png", Texture.class);
        manager.load("no.png", Texture.class);

        manager.load("music/1.ogg", Music.class);
        manager.load("music/2.ogg", Music.class);
      //  manager.load("music/3.mid", Music.class);
      //  manager.load("music/4.mid", Music.class);
      //  manager.load("music/5.mid", Music.class);
      /*  manager.load("music/6.mid", Music.class);
        manager.load("music/7.mid", Music.class);
        manager.load("music/8.mid", Music.class);
        manager.load("music/9.mid", Music.class);
        manager.load("music/10.mid", Music.class);
        manager.load("music/11.mid", Music.class);
        manager.load("music/12.mid", Music.class);
        manager.load("music/13.mid", Music.class);
        manager.load("music/14.mid", Music.class);
        manager.load("music/15.mid", Music.class);
        manager.load("music/16.mid", Music.class);
        manager.load("music/17.mid", Music.class);
        manager.load("music/18.mid", Music.class);
        manager.load("music/19.mid", Music.class);
        manager.load("music/21.mid", Music.class);
        manager.load("music/22.mid", Music.class);
        manager.load("music/23.mid", Music.class);
*/

        manager.load("sounds/arrow.ogg", Sound.class);
        manager.load("sounds/die1.ogg", Sound.class);
        manager.load("sounds/die2.ogg", Sound.class);
        manager.load("sounds/die3.ogg", Sound.class);
        manager.load("sounds/die4.ogg", Sound.class);
        manager.load("sounds/drop1.ogg", Sound.class);
        manager.load("sounds/maindie.ogg", Sound.class);

        //manager.load("stages.json", Object.class);


        //manager.load("bitmapFonts/kaushan.fnt", BitmapFont.class);
       // manager.load("fonts/neuropolxrg.ttf", f.class);
    }

    public boolean update() {
        return manager.update();
    }
}
