package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.DestructionListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Iterator;

import static com.kardelenapp.arrowofstickman.MainClass.mainClass;

/**
 * Created by mustafa on 11/3/2017.
 */

public class GameWorld {

    Body okBody;
    Body yapisBody;
    Sprite okSprite;
    YeniOk yapisanOk;
    boolean yapismaVar = false;


    World world;
    Body bodyEdgeScreen;
    ArrayList<YeniOk> oklar = new ArrayList<YeniOk>();
    ArrayList<Stickman> stickmans = new ArrayList<Stickman>();
    ArrayList<Elma> elmalar = new ArrayList<Elma>();
    ArrayList<StickPlayer> stickPlayers = new ArrayList<StickPlayer>();
    Constant c = new Constant();

    GameWorld gw ;
    Timer timer;
    Timer elmaTimer;
    Timer elmaRemoverTimer;
    Timer.Task elmaremoveTask;
    int enemyCount = 0;
    int score = 0;

    StickPlayer stickPlayer ;

    Sound die1;
    Sound die2;
    Sound die3;
    Sound die4;
    Sound drop1;

    boolean okSound = false;

    boolean arrowProof = false;
    int arrowProofTime = 100;

    Elma elma ;

    public GameWorld() {

        die1 = mainClass.assets.manager.get("sounds/die1.ogg", Sound.class);
        die2= mainClass.assets.manager.get("sounds/die2.ogg", Sound.class);
        die3= mainClass.assets.manager.get("sounds/die3.ogg", Sound.class);
        die4= mainClass.assets.manager.get("sounds/die4.ogg", Sound.class);
        drop1= mainClass.assets.manager.get("sounds/drop1.ogg", Sound.class);


        world = worldCreator();
        stickmans.add( new Stickman(this,c.oyunGenislik-100,100));
        gw = this;

        timer = new Timer();

        final Timer.Task task = new Timer.Task() {

            @Override
            public void run() {


                if (stickmans.size() < 2){
                    stickmans.add( new Stickman(gw, MathUtils.random(c.oyunGenislik*0.25f,c.oyunGenislik) ,MathUtils.random( 0,c.oyunYukseklik*0.75f)));

                }

            }
        };

        timer.scheduleTask(task,2,2);



        elmaTimer = new Timer();

          Timer.Task elmaTask = new Timer.Task() {

            @Override
            public void run() {

                elma =  new Elma(gw.world, MathUtils.random(0,c.oyunGenislik) ,MathUtils.random(0,c.oyunYukseklik));

                newElmaWithListener(elma);

                elmaRemoverTimer.scheduleTask(elmaremoveTask,4);
            }
        };

        elmaTimer.scheduleTask(elmaTask,6,6);


        elmaRemoverTimer = new Timer();
           elmaremoveTask = new Timer.Task() {
            @Override
            public void run() {

            if (elmalar.size()>0){
                world.destroyBody(elma.body1);

                elmalar.clear();
            }



            }
        };


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = c.WORLD_ENTITY;
        fixtureDef.filter.maskBits = c.WORLD_ENTITY;

        float w = c.oyunGenislik/c.PIXELS_TO_METERS;
        float h = c.oyunYukseklik/c.PIXELS_TO_METERS;

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef.shape = edgeShape;
        edgeShape.dispose();

        PolygonShape shape = polygonShapeCreator(w,0);
        FixtureDef fixtureDef1 = fixtureDefCreator(shape,   0, 0, c.WORLD_ENTITY , c.WORLD_ENTITY);

        bodyEdgeScreen = world.createBody(BodyDefCreatorGround());
        bodyEdgeScreen.createFixture(  fixtureDef1 );



        stickPlayer = new StickPlayer(gw,  100,  100);
        stickPlayers.add(stickPlayer )  ;
    }


    private BodyDef BodyDefCreatorGround() {


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        return bodyDef;
    }


    private FixtureDef fixtureDefCreatorGround(short WORLD_ENTITY, short PHYSICS_ENTITY ) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = WORLD_ENTITY;
        fixtureDef.filter.maskBits = PHYSICS_ENTITY;

        float w = c.oyunGenislik/c.PIXELS_TO_METERS;
        float h = c.oyunYukseklik/c.PIXELS_TO_METERS;

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef.shape = edgeShape;
        edgeShape.dispose();
        return fixtureDef;
    }

    private World worldCreator() {
        World world = new World(new Vector2(0, -9.81f),true);
        return world;
    }


    public boolean collisionDetection(Contact contact, Body body1, Body body2){

        if((contact.getFixtureA().getBody() == body1 &&
                contact.getFixtureB().getBody() == body2)
                ||
                (contact.getFixtureA().getBody() == body2 &&
                        contact.getFixtureB().getBody() == body1)) {

            return true;
        }
        return false;

    }

    public void collideListenerUpdate(){


        this.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                for (Iterator<YeniOk> i = oklar.iterator(); i.hasNext(); ) {
                    YeniOk ok = i.next();

                    if (ok.yapisti){
                       continue;


                     }

                    for (Body body : stickPlayer.bodies) {
                        if (collisionDetection(contact, ok.body1, body)) {


                            if (arrowProof) {
                                return;
                            }


                            okSprite = ok.sprite1;
                            okBody = ok.body1;
                            yapisBody = body;
                            yapismaVar = true;
                            yapisanOk = ok;


                            if (!stickPlayer.alive) {
                                return;
                            }

                            if(stickPlayer.can > 0 )
                            {
                                stickPlayer.can -= 1;
                                return;
                            }

                            stickPlayer.kill();
                            Sound sound = mainClass.assets.manager.get("sounds/maindie.ogg", Sound.class);
                            sound.play();

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {

                                    world.destroyBody(stickPlayer.stickman1);
                                    world.destroyBody(stickPlayer.stickman2);
                                    world.destroyBody(stickPlayer.stickman3);
                                    world.destroyBody(stickPlayer.stickman4);
                                    world.destroyBody(stickPlayer.stickman5);
                                    world.destroyBody(stickPlayer.stickman6);
                                    world.destroyBody(stickPlayer.stickman7);
                                    world.destroyBody(stickPlayer.platform.body1);
                                    world.destroyBody(stickPlayer.yay.body1);
                                    stickPlayers.remove(stickPlayer);
                                    //stickPlayer.dispose();

                                    //game end

                                }
                            }, 2);

                            break;
                        }
                    }

                    for (Iterator<Elma> j = elmalar.iterator(); j.hasNext(); ) {
                        final Elma elma = j.next();

                        if (collisionDetection(contact, ok.body1, elma.body1)) {


                            okSprite = ok.sprite1;
                            okBody = ok.body1;
                            yapisBody = elma.body1;
                            yapismaVar = true;
                            yapisanOk = ok;



                            if (elma.alive){
                                if (stickPlayer.can<3){
                                    stickPlayer.can += 1;
                                }

                                elma.kill();
                            }
                            else{

                            }



                        }

                    }




                    for (Iterator<Stickman> j = stickmans.iterator(); j.hasNext();) {
                        final Stickman stickman = j.next();

                        for(Body body : stickman.bodies) {

                            if (collisionDetection(contact, ok.body1,body)){



                                okSprite = ok.sprite1;
                                okBody = ok.body1;
                                yapisBody = body;
                                yapismaVar = true;
                                yapisanOk = ok;


                                if(!stickman.alive){
                                    return;
                                }

                                stickman.kill();
                                score += 1;

                                playRandomKillSound();

                                Timer.schedule(new Timer.Task(){
                                    @Override
                                    public void run() {

                                        world.destroyBody(stickman.stickman1);
                                        world.destroyBody(stickman.stickman2);
                                        world.destroyBody(stickman.stickman3);
                                        world.destroyBody(stickman.stickman4);
                                        world.destroyBody(stickman.stickman5);
                                        world.destroyBody(stickman.stickman6);
                                        world.destroyBody(stickman.stickman7);
                                        world.destroyBody(stickman.platform.body1);
                                        world.destroyBody(stickman.yay.body1);
                                        stickmans.remove(stickman);
                                        stickman.dispose();
                                    }
                                }, 5);

                                break;
                            }
                        }
                    }
                }
            }
            @Override
            public void endContact(Contact contact) {
            }
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }


    public void newArrowWithListener(float posX, float posY, float x, float y){
        final YeniOk ok = new YeniOk(world,posX,posY,new Vector2(x,y));

        oklar.add(ok);
        collideListenerUpdate();

        final Timer timer = new Timer();
        final Timer.Task task = new Timer.Task() {
            @Override
            public void run() {


                world.destroyBody(ok.body1);
                //world.destroyBody(ok.body2);
                world.destroyBody(ok.body3);



                oklar.remove(ok);
                ok.dispose();

            }
        };

        timer.scheduleTask(task,5);
    }

    public void newElmaWithListener(Elma elma ){



        if (elmalar.size() < 1){
            elmalar.add(elma);
            collideListenerUpdate();


        }




    }

    public void playRandomKillSound(){
        int maxSoundNumber = 4;
        int selected = MathUtils.random(1, maxSoundNumber);
        Sound sound =  mainClass.assets.manager.get("sounds/die" + selected + ".ogg", Sound.class);
        sound.play();


    }

    public void dispose() {
        world.dispose();

    }

    private PolygonShape polygonShapeCreator(float x, float y) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(x, y);

        return polygonShape;
    }

    private FixtureDef fixtureDefCreator(PolygonShape polygonShape, float density, float restitution, short PHYSICS_ENTITY, short WORLD_ENTITY) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = PHYSICS_ENTITY;
        fixtureDef.filter.maskBits = WORLD_ENTITY;

        return fixtureDef;
    }
}
