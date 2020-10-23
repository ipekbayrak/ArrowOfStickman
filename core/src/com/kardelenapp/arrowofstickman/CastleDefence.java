package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

import static com.kardelenapp.arrowofstickman.MainClass.mainClass;

public class CastleDefence  implements  Screen, GestureDetector.GestureListener {




	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;
	private Viewport viewport;

	float touchdownX = 0f;
	float touchdownY = 0f;
	float torque = 0.0f;

	BitmapFont font;

	com.kardelenapp.arrowofstickman.Constant c = new com.kardelenapp.arrowofstickman.Constant();
	com.kardelenapp.arrowofstickman.GameWorld gw ;

	Stage stage;
	boolean menuShow = false;

	Button yesButton;
	Button noButton;

	Button playAgainButton;
	Button backToMenuButton;
	Button soundOnOffButton;
	Label gameOverLabel;
	Label scoreLabel;
	Label healthLabel;

	Button can1;
	Button can2;
	Button can3;

	private Table ustTablo;
	private Table ustOrtaTablo;
	private Table merkezTablo;
	private Table anaTablo;
	Label highScoreLabel;

	private Button playButton;

	private SpriteBatch batch;
	private Sprite sprite;

	InputMultiplexer inputMultiplexer;

	boolean pauseGame = false;


	Timer.Task menuTask;
	Timer menutimer;

	boolean menuGerisayimSifirlandi = true;

	int menuDelay = 100;

	Sound arrowSound;

	boolean arrowGerildi = false;
	Sprite arrowTo;
	@Override
	public void show() {


		arrowSound = mainClass.assets.manager.get("sounds/arrow.ogg", Sound.class);

		//camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//viewport = new FitViewport(  c.oyunGenislik, c.oyunYukseklik, camera);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		viewport = new FitViewport(  c.oyunGenislik, c.oyunYukseklik, camera);

		batch = new SpriteBatch();



		sprite = new Sprite(mainClass.assets.manager.get("background.png", Texture.class));;
		sprite.setSize(c.oyunGenislik,c.oyunYukseklik);

		arrowTo= new Sprite(mainClass.assets.manager.get("arrowto.png", Texture.class));;
		arrowTo.setSize(0,0);

		font = mainClass.freeFontFactory.getFont48();

		gw = new com.kardelenapp.arrowofstickman.GameWorld();

		createNewStage();



		debugRenderer = new Box2DDebugRenderer();



	}

	@Override
	public void render(float delta) {
		camera.update();

		if(gw.stickPlayer.can==0){
			can1.setChecked(false);
			can2.setChecked(false);
			can3.setChecked(false);
		}
		else if(gw.stickPlayer.can==1){
			can1.setChecked(true);
			can2.setChecked(false);
			can3.setChecked(false);
		}
		else if(gw.stickPlayer.can==2){
			can1.setChecked(true);
			can2.setChecked(true);
			can3.setChecked(false);
		}
		else if(gw.stickPlayer.can==3){
			can1.setChecked(true);
			can2.setChecked(true);
			can3.setChecked(true);
		}


		highScoreLabel.setText("Score: " + String.valueOf(gw.score));
		if(gw.okSound){

			arrowSound.play();
			gw.okSound = false;
		}



		if(gw.arrowProof){
			if(gw.arrowProofTime > 0){
				gw.arrowProofTime -= delta;
			}
			else
			{
				gw.arrowProofTime = 100;
				gw.arrowProof = false;
			}
		}


		if(gw.yapismaVar){
			gw.yapisanOk.yapis(gw.yapisBody);
			gw.yapismaVar = false;
		}


		//stickman öldü oyun bitti
		if( !gw.stickPlayer.alive){


			if(!menuShow){

				if(menuDelay > 0){
					menuDelay-=delta;
				}
				else{
					menuDelay = 100;


					pauseGame = true;

					timersStop();
					//addRewardQuestion();

					mainClass.adsShow();
					createNewStage();
					addMenu();
				}


			}



		}

		if (!pauseGame)
		{
			gw.world.step(1f/40f,5,5);

			for (Iterator<com.kardelenapp.arrowofstickman.Stickman> i = gw.stickmans.iterator(); i.hasNext();) {
				com.kardelenapp.arrowofstickman.Stickman stickman = i.next();
				stickman.getPositionHelper();
			}

			for (Iterator<YeniOk> i = gw.oklar.iterator(); i.hasNext();) {
				YeniOk ok = i.next();
				ok.getPositionHelper();
			}

			for (Iterator<StickPlayer> i = gw.stickPlayers.iterator(); i.hasNext();) {
				StickPlayer stickplayer = i.next();
				stickplayer.getPositionHelper();
			}

			for (Iterator<Elma> i = gw.elmalar.iterator(); i.hasNext();) {
				Elma elma = i.next();
				elma.getPositionHelper();
			}
		}




		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRendererHelper();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();


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



	private void debugRendererHelper() {
		batch.setProjectionMatrix(camera.combined);

		// Scale down the sprite batches projection matrix to box2D size
		debugMatrix = batch.getProjectionMatrix().cpy().scale(c.PIXELS_TO_METERS,
				c.PIXELS_TO_METERS, 0);

		batch.begin();



		sprite.setPosition(0,0);

		batchDrawHelper(sprite,batch);

		for (Iterator<com.kardelenapp.arrowofstickman.Stickman> i = gw.stickmans.iterator(); i.hasNext();) {
			com.kardelenapp.arrowofstickman.Stickman stickman = i.next();
			stickman.getBatchDrawHelper(batch);
		}

		for (Iterator<YeniOk> i = gw.oklar.iterator(); i.hasNext();) {
			YeniOk ok = i.next();
			ok.getBatchDrawHelper(batch);
		}

		for (Iterator<StickPlayer> i = gw.stickPlayers.iterator(); i.hasNext();) {
			StickPlayer stickplayer = i.next();
			stickplayer .getBatchDrawHelper(batch);
		}

		for (Iterator<Elma> i = gw.elmalar.iterator(); i.hasNext();) {
			Elma elma = i.next();
			elma .getBatchDrawHelper(batch);
		}

		if(arrowGerildi){
			batchDrawHelper(arrowTo,batch);
		}


		//font.draw(batch,"Hello World",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

		batch.end();
		// debugRenderer.render(gw.world, debugMatrix);

	}

	@Override
	public void dispose() {
//		batch.dispose();
//		debugRenderer.dispose();
//		font.dispose();

		//	gw.dispose();

	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {

		this.touchdownX = x;
		this.touchdownY = y;


		arrowTo.setX(gw.stickPlayer.yay.sprite1.getX() + 20);
		arrowTo.setY(gw.stickPlayer.yay.sprite1.getY() + 40);
		arrowTo.setSize(0,0);
		arrowTo.setOrigin(0,20);

		Vector2 degreeDokasan = new Vector2(0, 1);
		Vector2 degree = new Vector2(touchdownX - x, -(touchdownY - y));
		gw.stickPlayer.yay.body1.setTransform(gw.stickPlayer.yay.body1.getPosition(),degree.angleRad());
		gw.stickPlayer.yay.body1.setFixedRotation(true);

		gw.stickPlayer.stickman5.setFixedRotation(true);
		gw.stickPlayer.stickman5.setTransform(gw.stickPlayer.stickman5.getPosition(),degreeDokasan.angleRad());

		arrowGerildi= true;



		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {




		if(x<touchdownX) {



			Vector2 degreeDokasan = new Vector2(0, 1);
			Vector2 degree = new Vector2(touchdownX - x, -(touchdownY - y));
			gw.stickPlayer.yay.body1.setTransform(gw.stickPlayer.yay.body1.getPosition(),degree.angleRad());
			gw.stickPlayer.yay.body1.setFixedRotation(true);

			gw.stickPlayer.stickman5.setFixedRotation(true);
			gw.stickPlayer.stickman5.setTransform(gw.stickPlayer.stickman5.getPosition(),degreeDokasan.angleRad());

			arrowTo.setX(gw.stickPlayer.yay.sprite1.getX() + 20);
			arrowTo.setY(gw.stickPlayer.yay.sprite1.getY() + 40);

			arrowTo.setSize(arrowTo.getWidth() - deltaX, 50);
			arrowTo.setRotation(degree.angle());

		}
		else{
			arrowTo.setSize(0, 0);
		}

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {

		// ok atma
		if(gw.stickPlayer.alive && !pauseGame && x<touchdownX){

			gw.newArrowWithListener( gw.stickPlayer.yay.sprite1.getX() + gw.stickPlayer.yay.sprite1.getWidth() + 30 , gw.stickPlayer.yay.sprite1.getY()  + gw.stickPlayer.yay.sprite1.getHeight() /2 , (touchdownX-x) / 60 ,(y-touchdownY) /60);
			gw.okSound = true;
		}

		gw.stickPlayer.yay.body1.setFixedRotation(false);
		gw.stickPlayer.stickman5.setFixedRotation(false);

		arrowGerildi = false;

		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public void pinchStop() {
	}

	private void  addMenu(){

		scoreLabel = mainClass.buttonFactory.getLabel("Score: "  );

		healthLabel = mainClass.buttonFactory.getLabel("Health: "   );

		if (gw.score > c.highScore)
		{
			//new high
			c.setHighScore(gw.score);

			scoreLabel = mainClass.buttonFactory.getLabel("New High\nScore: " + String.valueOf(gw.score) );
		}
		else{
			scoreLabel = mainClass.buttonFactory.getLabel("Score: " + String.valueOf(gw.score));
		}

		playAgainButton = mainClass.buttonFactory.getPlayAgainButton();
		backToMenuButton = mainClass.buttonFactory.getBackToMenuButton();
		gameOverLabel = mainClass.buttonFactory.getLabel("Game Over!");

		backToMenuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				backToMainMenu();

			}
		});

		playAgainButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				timersStop();
				pauseGame = false;
				menuShow = false;
				merkezTablo.remove();
				createNewStage();
				gw = new com.kardelenapp.arrowofstickman.GameWorld();
				debugRenderer = new Box2DDebugRenderer();

			}
		});

//np
		merkezTablo = new Table();
		merkezTablo.setDebug(false);
		//merkezTablo.setFillParent(true);
		//merkezTablo.setWidth(stage.getWidth()/4);
		merkezTablo.setHeight(stage.getHeight());
		merkezTablo.align(Align.center );


		merkezTablo.row();
		merkezTablo.add(gameOverLabel).pad(20).colspan(2).align(Align.center);;
		merkezTablo.row();
		merkezTablo.add(scoreLabel).pad(20).colspan(2).align(Align.center);
		merkezTablo.row();
		merkezTablo.add(playAgainButton).pad(20)  ;
		merkezTablo.add(backToMenuButton).pad(20) ;

		if (merkezTablo.getCell(scoreLabel).getMaxWidth() > merkezTablo.getCell(gameOverLabel).getMaxWidth()) {
			merkezTablo.setWidth(merkezTablo.getCell(scoreLabel).getMinWidth());
		}
		else
		{
			merkezTablo.setWidth(merkezTablo.getCell(gameOverLabel).getMinWidth());
		}
		merkezTablo.setPosition(c.oyunGenislik/2 - merkezTablo.getWidth()/2,0);


		merkezTablo.setColor(Color.BLACK);
		merkezTablo.background( mainClass.buttonFactory.blackBackgroundA());



		stage.addActor(merkezTablo);
		menuShow = true;
		Gdx.app.log("menu","menu ac");






	}


	public void addRewardQuestion(){
		scoreLabel = mainClass.buttonFactory.getLabel("Watch video\nto continue?");

		yesButton = mainClass.buttonFactory.getYesButton();
		noButton = mainClass.buttonFactory.getNoButton();

		yesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				mainClass.adsRewardShow();

				createNewStage();
				createContinue();

			}
		});

		noButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {


				mainClass.adsShow();
				createNewStage();
				addMenu();

			}
		});

//np
		merkezTablo = new Table();
		merkezTablo.setDebug(false);
		//merkezTablo.setFillParent(true);
		//merkezTablo.setWidth(stage.getWidth()/4);
		merkezTablo.setHeight(stage.getHeight());
		merkezTablo.align(Align.center );


		merkezTablo.row();
		merkezTablo.add(scoreLabel).pad(20).colspan(2).align(Align.center);
		merkezTablo.row();
		merkezTablo.add(yesButton).pad(20)  ;
		merkezTablo.add(noButton).pad(20) ;



		merkezTablo.setWidth(merkezTablo.getCell(scoreLabel).getMinWidth());

		merkezTablo.setPosition(c.oyunGenislik/2 - merkezTablo.getWidth()/2,0);


		merkezTablo.setColor(Color.BLACK);
		merkezTablo.background( mainClass.buttonFactory.blackBackgroundA());



		stage.addActor(merkezTablo);
		menuShow = true;
		Gdx.app.log("menu","menu ac");




	}




	public void createContinue(){
		scoreLabel = mainClass.buttonFactory.getLabel("Press to Continue");

		playButton = mainClass.buttonFactory.getPlayButton(false);

		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				createNewStage();
				timersStart();
				pauseGame = false;
				menuShow = false;
				gw.stickPlayer = new StickPlayer(gw, - c.oyunGenislik/2 + 100,- c.oyunYukseklik/2 + 100);
				gw.stickPlayers.add(gw.stickPlayer );
				gw.stickPlayer.alive = true;

				gw.arrowProof = true;
			}
		});

		merkezTablo = new Table();
		merkezTablo.setDebug(false);
		//merkezTablo.setFillParent(true);
		//merkezTablo.setWidth(stage.getWidth()/4);
		merkezTablo.setHeight(stage.getHeight());
		merkezTablo.align(Align.center );

		merkezTablo.row();
		merkezTablo.add(scoreLabel).pad(20).align(Align.center);;
		merkezTablo.row();
		merkezTablo.add(playButton).pad(20).align(Align.center);;



		merkezTablo.setPosition(c.oyunGenislik/2 - merkezTablo.getWidth()/2,0);


		merkezTablo.setColor(Color.BLACK);
		merkezTablo.background( mainClass.buttonFactory.blackBackgroundA());



		stage.addActor(merkezTablo);
		menuShow = true;
		Gdx.app.log("menu","menu ac");




	}


	private void batchDrawHelper(Sprite sprite, SpriteBatch batch) {
		batch.draw(sprite,
				sprite.getX(), sprite.getY(),
				sprite.getOriginX(), sprite.getOriginY(),
				sprite.getWidth(),sprite.getHeight(),
				sprite.getScaleX(),sprite.getScaleY(),
				sprite.getRotation());

		;
	}


	public void createNewStage(){

		Gdx.app.log("dokun","sdaokdoaksd");

		if (inputMultiplexer != null)
		{inputMultiplexer.clear();}



		stage = new Stage(viewport);

		ustOrtaTablo = new Table();
		ustOrtaTablo. setDebug(false);
		ustOrtaTablo. setWidth(stage.getWidth());
		ustOrtaTablo. setHeight(stage.getHeight());
		ustOrtaTablo. align(Align.center | Align.top );
		ustOrtaTablo. setPosition(0,0);

		ustTablo = new Table();
		ustTablo.setDebug(false);
		ustTablo.setWidth(stage.getWidth());
		ustTablo.setHeight(stage.getHeight());
		ustTablo.align(Align.left | Align.top );
		ustTablo.setPosition(0,0);

		highScoreLabel = mainClass.buttonFactory.getLabel("Score: " + gw.score);
		healthLabel= mainClass.buttonFactory.getLabel("Health: " + String.valueOf(gw.stickPlayer.can) );
		can1 = mainClass.buttonFactory.getCanVarYok();
		can2 = mainClass.buttonFactory.getCanVarYok();
		can3 = mainClass.buttonFactory.getCanVarYok();

		ustTablo.add(highScoreLabel).pad(c.oyunYukseklik/12).row();;

		ustOrtaTablo.add(can1).pad(c.oyunYukseklik/12,c.oyunYukseklik/50,0,0);
		ustOrtaTablo.add(can2).pad(c.oyunYukseklik/12,c.oyunYukseklik/50,0,0);
		ustOrtaTablo.add(can3).pad(c.oyunYukseklik/12,c.oyunYukseklik/50,0,0);

		stage.addActor(ustTablo);
		stage.addActor(ustOrtaTablo);

		// set input
		InputProcessor backProcessor = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				// back geri tuşuna basuldığında pause yap. timerlar durmalı
				if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ){
					if( !gw.stickPlayer.alive  && !menuShow)
						return true ;


					if(menuShow){
						if(pauseGame)
						{
							timersStart();


							//devam et oyuna
							pauseGame = false;
							menuShow = false;

							//remove table
							//set input processor again
							createNewStage();
						}
						else{
							// ana menuye git
							backToMainMenu();
						}
					}
					else{
						// oyun pause.. timerlar dursun render dursun

						timersStop();

						pauseGame = true;
						menuShow = true;

						anaTablo = new Table();
						anaTablo.setDebug(false);
						//anaTablo.setFillParent(true);
						//anaTablo.setWidth(stage.getWidth()/4);
						anaTablo.setHeight(c.oyunYukseklik);
						anaTablo.align(Align.center );

						soundOnOffButton = mainClass.buttonFactory.getSoundOnOffButton();
						backToMenuButton  = mainClass.buttonFactory.getBackToMenuButton();
						playAgainButton = mainClass.buttonFactory.getPlayAgainButton();
						playButton = mainClass.buttonFactory.getPlayButton(true);

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
						backToMenuButton.addListener(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								backToMainMenu();
							}
						});
						playAgainButton.addListener(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								timersStop();

								menuShow = false;
								//merkezTablo.remove();
								pauseGame = false;
								menuShow = false;
								createNewStage();
								gw = new com.kardelenapp.arrowofstickman.GameWorld();
								debugRenderer = new Box2DDebugRenderer();
							}
						});
						playButton.addListener(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								timersStart();

								pauseGame = false;
								menuShow = false;

								//remove table
								//set input processor again
								createNewStage();
							}
						});
						anaTablo.row();
						anaTablo.add(playAgainButton).pad(20).align(Align.center);;  ;
						anaTablo.add(backToMenuButton).pad(20).align(Align.center);; ;
						anaTablo.add(playButton).pad(20).align(Align.center);; ;
						anaTablo.row();
						anaTablo.add(soundOnOffButton).pad(20).align(Align.center).colspan(3);; ;
						//bak


						float tablowidth = playAgainButton.getWidth() + backToMenuButton.getWidth() + backToMenuButton.getWidth() + 120 ;
						//tablowidth = anaTablo.getCell(playButton).getMinWidth() +anaTablo.getCell(playAgainButton).getMinWidth() +anaTablo.getCell(backToMenuButton).getMinWidth()  + 20;
						anaTablo.setWidth(tablowidth);
						anaTablo.setPosition(c.oyunGenislik/2 - tablowidth/2,0);

						anaTablo.setColor(Color.BLACK);
						anaTablo.background( mainClass.buttonFactory.blackBackgroundA());

						stage.addActor(anaTablo);

					}

				}


				return true;
			}
		};


		Gdx.input.setCatchBackKey(true);
		inputMultiplexer = new InputMultiplexer(stage,new GestureDetector(this), backProcessor);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	public void backToMainMenu(){
		timersStop();
		// dispose
		gw.stickmans.clear();
		gw.oklar.clear();
		mainClass.backToMenu();
	}

	public void timersStart(){
		for(com.kardelenapp.arrowofstickman.Stickman stickman : gw.stickmans) {
			if(stickman.alive)

				stickman.timer.start();

		}

		gw.timer.start();


		//bug
		//gw.oklar.clear();
	}

	public void timersStop(){
		for(com.kardelenapp.arrowofstickman.Stickman stickman : gw.stickmans) {
			stickman.timer.delay(100);
			stickman.timer.stop();
		}
		gw.elmaTimer.delay(100);
		gw.elmaTimer.stop();

		gw.elmaRemoverTimer.delay(100);
		gw.elmaRemoverTimer.stop();

		gw.timer.delay(100);
		gw.timer.stop();
	}
}