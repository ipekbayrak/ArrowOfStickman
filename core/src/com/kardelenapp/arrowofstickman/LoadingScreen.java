package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.kardelenapp.arrowofstickman.MainClass.mainClass;

/**
 * Created by mustafa on 11/4/2017.
 */

public class LoadingScreen implements Screen{

    Viewport viewport;
    OrthographicCamera camera;
    private Skin skin;
    private Stage stage;

    private Table ustTablo;
    private Table merkezTablo;
    private Table altTablo;

    private Button playButton;
    private Button soundOnOffButton;
    private Button shareButton;
    private Button exitButton;

    private SpriteBatch batch;
    private Sprite sprite;

    com.kardelenapp.arrowofstickman.Constant c;

    Label highScoreLabel;

    ButtonFactory buttonFactory = new ButtonFactory();

    @Override
    public void show () {

        c = new com.kardelenapp.arrowofstickman.Constant();
        if(c.sound)
        {mainClass.playRandomMuısic();}

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport = new FitViewport(  c.oyunGenislik, c.oyunYukseklik, camera);

        skin = buttonFactory.getSkin();
        stage = new Stage(viewport);


        highScoreLabel = buttonFactory.getLabel("High Score: " + c.highScore);

        ustTablo = new Table();



        ustTablo.setDebug(false);
        ustTablo.setWidth(stage.getWidth());
        ustTablo.setHeight(stage.getHeight());
        ustTablo.align(Align.center | Align.top );
        ustTablo.setPosition(0,0);

        merkezTablo = new Table();
        merkezTablo.setDebug(false);
        merkezTablo.setWidth(stage.getWidth());
        merkezTablo.setHeight(stage.getHeight());
        merkezTablo.align(Align.center );
        merkezTablo.setPosition(0,0);


        playButton = buttonFactory.getPlayButton(false);


        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.startGame();

            }
        });

        merkezTablo.add(playButton);
        ustTablo.add(highScoreLabel).pad(c.oyunYukseklik/10)  ;

        stage.addActor(ustTablo);




        stage.addActor(merkezTablo);

        stage.addActor(buttonGroup());

        batch = new SpriteBatch();
        sprite = new Sprite(mainClass.assets.manager.get("background.png", Texture.class));;
        sprite.setSize(c.oyunGenislik,c.oyunYukseklik);
/*
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                sprite.setFlip(false,!sprite.isFlipY());
            }
        },10,10,10000);
*/

        // ORDER IS IMPORTANT!
        InputMultiplexer im = new InputMultiplexer(stage);
        Gdx.input.setInputProcessor(im);
    }





    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        sprite.draw(batch);
        batch.end();


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();



    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {

        highScoreLabel.setText("High Score: " + c.highScore);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
        batch.dispose();

    }



    public Table buttonGroup(){
        altTablo = new Table();
        altTablo.setDebug(false);
        altTablo.setWidth(stage.getWidth());
        altTablo.setHeight(stage.getHeight());
        altTablo.align(Align.center );
        altTablo.setPosition(0,-1080/3);

        soundOnOffButton = buttonFactory.getSoundOnOffButton();
        shareButton = buttonFactory.getShareButton();
        exitButton = buttonFactory.getBackToMenuButton();



        soundOnOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!soundOnOffButton.isChecked()){
                    mainClass.playRandomMuısic();
                    c.setSound(true);
                }
                else{
                    mainClass.stopMusic();
                    c.setSound(false);
                }
            }
        });
        shareButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.share();

            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    Gdx.app.exit();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        altTablo.add(soundOnOffButton).pad(Gdx.graphics.getHeight()/10);
        altTablo.add(shareButton).pad(Gdx.graphics.getHeight()/10);
        altTablo.add(exitButton).pad(Gdx.graphics.getHeight()/10);

        return altTablo;
    }



}
