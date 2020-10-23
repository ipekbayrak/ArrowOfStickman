package com.kardelenapp.arrowofstickman;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.kardelenapp.arrowofstickman.MainClass.mainClass;

public class LoadScreen implements Screen {



    private Animation loadAnimation ;
    private Image splashImage;
    private Texture background;
    private Stage stage;
    private Label label;

    Table buttonTable;

    public boolean animationDone = false;


    Viewport viewport;
    OrthographicCamera camera;

    Constant c = new Constant();
    private SpriteBatch batch;
    private Sprite sprite;


    private MainClass game;
    LoadScreen( ){
        this.game = game;
//        width = Gdx.graphics.getWidth();
       // height = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        sprite =  new Sprite(new Texture(Gdx.files.internal("logo.png"))) ;
        sprite.setSize(c.ekranGenislik,c.ekranYukseklik);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport = new FitViewport(  c.oyunGenislik, c.oyunYukseklik, camera);

        //loadAnimation = new AnimationLoad("/loading/loadingPack.atlas", "loading", 12).getAnimation();


      //  background =  new Texture(Gdx.files.internal("background.png"));



        ///label = new Label("Loading...",labelStyle);

      //  splashImage =  new AnimatedImage(loadAnimation);
      /*  buttonTable = new Table();
        buttonTable.setDebug(true);
        buttonTable.setWidth(stage.getWidth());
        buttonTable.setHeight(stage.getHeight());
        buttonTable.align(Align.center | Align.top );
        buttonTable.setPosition(0,0);

        stage = new Stage();
       // buttonTable.add(splashImage) ;
*/

       //  stage.addActor(buttonTable);
    }





    @Override
    public void render(float delta ) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();



       // splashImage.act(Gdx.graphics.getDeltaTime() );

      //  stage.act();
       // stage.draw();
        if(mainClass.assets.update()){ // check if all files are loaded

          //  if(animationDone){ // when the animation is finished, go to MainMenu()
                //Assets.setMenuSkin(); // uses files to create menuSkin

                Gdx.app.log("bittiy" ,"yeni ekran");
                mainClass.assetsLoaded();
                this.dispose();
            //}
        }

       // viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);

    }

    @Override
    public void show() {
        //splashImage.setDrawable(new SpriteDrawable(new Sprite(texture)));

/*
        splashImage.addAction(Actions.sequence(Actions.alpha(0)
                , Actions.fadeIn(0.75f), Actions.delay(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        animationDone = true;
                    }
                })));
*/
        mainClass.assets.queueLoading();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        //splashImage.dispose();
       //stage.dispose();
    }
}
