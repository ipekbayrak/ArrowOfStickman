package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import static com.kardelenapp.arrowofstickman.MainClass.mainClass;

/**
 * Created by mustafa on 11/4/2017.
 */

public class ButtonFactory {

    Skin skin;
    BitmapFont font;

    Pixmap pixmap;
    TextButton.TextButtonStyle textButtonStyle;


    Button button;
    Button.ButtonStyle buttonStyle;

    TextField textField;


    Label label;
    private Button backToMenuButton;
    private Button playAgainButton;

    Constant c = new Constant();
    public ButtonFactory(){

        BitmapFont font = mainClass.freeFontFactory.getFont48();

        skin = new Skin();
        skin.add("scoreText", font);
        skin.add("default", font);

        skin.add("canVar", mainClass.assets.manager.get("elmaAcik.png", Texture.class) );
        skin.add("canYok", mainClass.assets.manager.get("elmaKapali.png", Texture.class) );

        skin.add("play_miniButton", mainClass.assets.manager.get("play_mini.png", Texture.class) );
        skin.add("playButton", mainClass.assets.manager.get("play.png", Texture.class) );
        skin.add("refreshButton", mainClass.assets.manager.get("refresh.png", Texture.class) );
        skin.add("exitButton", mainClass.assets.manager.get("exit.png", Texture.class) );
        skin.add("soundOnButton", mainClass.assets.manager.get("soundOff.png", Texture.class) );
        skin.add("soundOffButton", mainClass.assets.manager.get("soundOn.png", Texture.class) );
        skin.add("shareButton", mainClass.assets.manager.get("share.png", Texture.class) );
        skin.add("yesButton", mainClass.assets.manager.get("yes.png", Texture.class) );
        skin.add("noButton", mainClass.assets.manager.get("no.png", Texture.class) );

        pixmap = getPixmapRoundedRectangle((int)c.oyunGenislik/4,(int)c.oyunYukseklik/10, (int)c.oyunYukseklik/50 ,new Color(200,200,200,50));

        skin.add("background",new Texture(pixmap));

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background");
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background");
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;
        skin.add("default", textFieldStyle);


        skin.add("alphaBackground",new Texture( pixmap = getPixmapRoundedRectangle((int) c.oyunGenislik/4,(int) c.oyunYukseklik/10, (int)c.oyunYukseklik/50 ,new Color(0,0,0,0))));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        labelStyle.background = skin.newDrawable("alphaBackground");

        skin.add("default", labelStyle);


    }



    public Skin getSkin(){

        return this.skin;
    }


    public static Pixmap getPixmapRoundedRectangle(int width, int height, int radius, Color color) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        // Pink rectangle
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight()-2*radius);

// Green rectangle
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2*radius, pixmap.getHeight());


// Bottom-left circle
        pixmap.fillCircle(radius, radius, radius);

// Top-left circle
        pixmap.fillCircle(radius, pixmap.getHeight()-radius, radius);

// Bottom-right circle
        pixmap.fillCircle(pixmap.getWidth()-radius, radius, radius);

// Top-right circle
        pixmap.fillCircle(pixmap.getWidth()-radius, pixmap.getHeight()-radius, radius);
        return pixmap;
    }


    public Button getPlayButton(boolean mini){
        buttonStyle = new TextButton.TextButtonStyle();
        if (mini){
            buttonStyle.up = skin.newDrawable("play_miniButton");
            buttonStyle.down = skin.newDrawable("play_miniButton", Color.DARK_GRAY);
            buttonStyle.over = skin.newDrawable("play_miniButton", Color.LIGHT_GRAY);
        }
       else {
            buttonStyle.up = skin.newDrawable("playButton");
            buttonStyle.down = skin.newDrawable("playButton", Color.DARK_GRAY);
            buttonStyle.over = skin.newDrawable("playButton", Color.LIGHT_GRAY);
        }

        button = new Button(buttonStyle);

        return  button;
    }


    public TextField getTextFiel( String string){

        textField = new TextField(string,skin);
        textField.setMessageText("Click here!");
        textField.setAlignment(Align.center);

        return  textField;
    }


    public Label getLabel( String string){

        label = new Label(string,skin);
        label.setAlignment(Align.center);

        return  label;
    }

    public Drawable blackBackground(){

        return  skin.newDrawable("background", Color.DARK_GRAY);
    }

    public Label getBlackLabel(String string) {
        label = new Label(string,skin);
        label.setAlignment(Align.center);
        label.setColor(Color.BLACK);
        return  label;
    }


    public Drawable blackBackgroundA(){

        Pixmap pixmap = new Pixmap((int) (c.oyunGenislik/4),(int) c.oyunYukseklik/10, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(200,200,200,200));
        pixmap.fill();
        this.skin.add("backgroundA",new Texture(pixmap));

        return  skin.newDrawable("backgroundA");
    }

    public static Pixmap getPixmapNotRounded(int width, int height, int radius, Color color) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();


        return pixmap;
    }

    public Button getBackToMenuButton() {


        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("exitButton");
        buttonStyle.down = skin.newDrawable("exitButton", Color.DARK_GRAY);
        buttonStyle.over = skin.newDrawable("exitButton", Color.LIGHT_GRAY);

        button = new Button(buttonStyle);
        return  button;

    }

    public Button getPlayAgainButton() {

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("refreshButton");
        buttonStyle.down = skin.newDrawable("refreshButton", Color.DARK_GRAY);
        buttonStyle.over = skin.newDrawable("refreshButton", Color.LIGHT_GRAY);

        button = new Button(buttonStyle);
        return  button;
    }


    public Button getShareButton() {

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("shareButton");
        buttonStyle.down = skin.newDrawable("shareButton", Color.DARK_GRAY);
        buttonStyle.over = skin.newDrawable("shareButton", Color.LIGHT_GRAY);

        button = new Button(buttonStyle);
        return  button;
    }

    public Button getYesButton() {

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("yesButton");
        buttonStyle.down = skin.newDrawable("yesButton", Color.DARK_GRAY);
        buttonStyle.over = skin.newDrawable("yesButton", Color.LIGHT_GRAY);

        button = new Button(buttonStyle);
        return  button;
    }
    public Button getNoButton() {

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("noButton");
        buttonStyle.down = skin.newDrawable("noButton", Color.DARK_GRAY);
        buttonStyle.over = skin.newDrawable("noButton", Color.LIGHT_GRAY);

        button = new Button(buttonStyle);
        return  button;
    }



    public Button getSoundOnOffButton() {

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("soundOffButton");
        buttonStyle.down = skin.newDrawable("soundOffButton", Color.DARK_GRAY);
        buttonStyle.over = skin.newDrawable("soundOffButton", Color.LIGHT_GRAY);
        buttonStyle.checked =  skin.newDrawable("soundOnButton");
        buttonStyle.checkedOver =  skin.newDrawable("soundOnButton", Color.LIGHT_GRAY);

         button = new Button(buttonStyle);

        if(!c.sound){
            button.setChecked(true);
        }

        return  button;
    }

    public Button getCanVarYok() {

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("canYok");
        buttonStyle.down = skin.newDrawable("canYok");
        buttonStyle.over = skin.newDrawable("canYok");
        buttonStyle.checked =  skin.newDrawable("canVar");
        buttonStyle.checkedOver =  skin.newDrawable("canVar");

        button = new Button(buttonStyle);



        return  button;
    }

}


