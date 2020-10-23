package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import static com.kardelenapp.arrowofstickman.MainClass.mainClass;

/**
 * Created by mustafa on 11/2/2017.
 */

public class Stickman {

    com.kardelenapp.arrowofstickman.Yay yay;
    Joint yayJoint;

    Body stickman1 , stickman2, stickman3, stickman4, stickman5, stickman6 ,stickman7;
    Sprite Sstickman1 , Sstickman2, Sstickman3, Sstickman4, Sstickman5, Sstickman6 ,Sstickman7;
    Joint stickman1Joint1, stickman1Joint2, stickman1Joint3, stickman1Joint4, stickman1Joint5, stickman1Joint6;

    Texture stickImg1;
    Texture stickImg2;
    Texture stickImg3;

    Constant c = new Constant();

    boolean alive = true;

    boolean disjointed = false;

    final World world;


    com.kardelenapp.arrowofstickman.Platform platform;

    final float x;
    final float y;

    final Timer timer;

    ArrayList<Body> bodies = new ArrayList<Body>();



    public Stickman(final com.kardelenapp.arrowofstickman.GameWorld gameWorld, final float x, final float y){
        this.x = x;
        this.y = y;



        this.world = gameWorld.world;

        stickImg1 =  mainClass.assets.manager.get("body.png", Texture.class);
        stickImg2 =  mainClass.assets.manager.get("part2.png", Texture.class);
        stickImg3 =  mainClass.assets.manager.get("part3.png", Texture.class);



        float SolAyakX = 0;
        float SolAyakY = 0;

        float SagAyakX = stickImg1.getWidth();
        float SagAyakY = 0;

        float GovdeX = stickImg1.getWidth();
        float GovdeY = stickImg1.getHeight();

        float SolKolX = 0;
        float SolKolY = stickImg1.getHeight();

        float SagKolX = stickImg1.getWidth();
        float SagKolY = stickImg1.getHeight();

        float BoyunX = stickImg1.getWidth() + stickImg1.getWidth() /2 - stickImg2.getWidth() /2 ;
        float BoyunY = stickImg1.getHeight() + stickImg1.getHeight() ;

        float KafaX = stickImg1.getWidth() + stickImg1.getWidth() /2 - stickImg3.getWidth() /2 ;
        float KafaY = stickImg1.getHeight() + stickImg1.getHeight() +stickImg2.getHeight() ;


        Sstickman1 = spriteCreator(stickImg1 , x + SolAyakX, y + SolAyakY ); // sol ayak
        Sstickman2 = spriteCreator(stickImg1 , x + SagAyakX, y + SagAyakY ); // sağ ayak
        Sstickman3 = spriteCreator(stickImg1 , x + GovdeX  , y + GovdeY ); // gövde
        Sstickman4 = spriteCreator(stickImg1 , x + SolKolX , y + SolKolY ); // sol kol
        Sstickman5 = spriteCreator(stickImg1 , x + SagKolX , y + SagKolY ); // sağ kol
        Sstickman6 = spriteCreator(stickImg2 , x + BoyunX  , y + BoyunY ); // boyun
        Sstickman7 = spriteCreator(stickImg3 , x + KafaX   , y + KafaY ); // kafa



        stickman1 = world.createBody(bodyDefCreator(Sstickman1));
        stickman2 = world.createBody(bodyDefCreator(Sstickman2));
        stickman3 = world.createBody(bodyDefCreator(Sstickman3));
        stickman4 = world.createBody(bodyDefCreator(Sstickman4));
        stickman5 = world.createBody(bodyDefCreator(Sstickman5));
        stickman6 = world.createBody(bodyDefCreator(Sstickman6));
        stickman7 = world.createBody(bodyDefCreator(Sstickman7));

        PolygonShape  shstickman1 = polygonShapeCreator(Sstickman1);
        PolygonShape shstickman2 = polygonShapeCreator(Sstickman2);
        PolygonShape shstickman3 = polygonShapeCreator(Sstickman3);
        PolygonShape shstickman4 = polygonShapeCreator(Sstickman4);
        PolygonShape shstickman5 = polygonShapeCreator(Sstickman5);
        PolygonShape shstickman6 = polygonShapeCreator(Sstickman6);
        CircleShape shstickman7 = circlenShapeCreator(Sstickman7);

        FixtureDef fixtureDef1 = fixtureDefCreator ( shstickman1,1f,0.5f,c.STICK_ENTITY, (short) ( c.WORLD_ENTITY | c.STICK_ENTITY));
        FixtureDef fixtureDef2 = fixtureDefCreator( shstickman2,1f,0.5f, c.STICK_ENTITY, (short) ( c.WORLD_ENTITY | c.STICK_ENTITY));
        FixtureDef fixtureDef3 = fixtureDefCreator( shstickman3,1f,0.5f, c.STICK_ENTITY, (short) ( c.WORLD_ENTITY | c.STICK_ENTITY));
        FixtureDef fixtureDef4 = fixtureDefCreator( shstickman4,1f,0.5f, c.STICK_ENTITY, (short) ( c.WORLD_ENTITY | c.STICK_ENTITY));
        FixtureDef fixtureDef5 = fixtureDefCreator( shstickman5,1f,0.5f, c.STICK_ENTITY, (short) ( c.WORLD_ENTITY | c.STICK_ENTITY));
        FixtureDef fixtureDef6 = fixtureDefCreator( shstickman6,1f,0.5f, c.STICK_ENTITY, (short) ( c.WORLD_ENTITY | c.STICK_ENTITY));
        FixtureDef fixtureDef7 = fixtureDefCreator( shstickman7,1f,0.5f, c.STICK_ENTITY, (short) ( c.WORLD_ENTITY | c.STICK_ENTITY));

        stickman1.createFixture(fixtureDef1);
        stickman2.createFixture(fixtureDef2);
        stickman3.createFixture(fixtureDef3);
        stickman4.createFixture(fixtureDef4);
        stickman5.createFixture(fixtureDef5);
        stickman6.createFixture(fixtureDef6);
        stickman7.createFixture(fixtureDef7);


        stickman1Joint1 = world.createJoint(revoluteJointDefCreatorSolAlt(stickman3,stickman1,Sstickman3,Sstickman1,false));
        stickman1Joint2 = world.createJoint(revoluteJointDefCreatorSagAlt(stickman3,stickman2,Sstickman3,Sstickman2,false));
        stickman1Joint3 = world.createJoint(revoluteJointDefCreatorSolUst(stickman3,stickman4,Sstickman3,Sstickman4,false));
        stickman1Joint4 = world.createJoint(revoluteJointDefCreatorSagUst(stickman3,stickman5,Sstickman3,Sstickman5,false));
        stickman1Joint5 = world.createJoint(revoluteJointDefCreatorUstOrta(stickman3,stickman6,Sstickman3,Sstickman6,false));
        stickman1Joint6 = world.createJoint(revoluteJointDefCreatorUstOrta(stickman6,stickman7,Sstickman6,Sstickman7,false));


        shstickman1.dispose();
        shstickman2.dispose();
        shstickman3.dispose();
        shstickman4.dispose();
        shstickman5.dispose();
        shstickman6.dispose();
        shstickman7.dispose();

        this.platform = new com.kardelenapp.arrowofstickman.Platform(world,x-50,y-20);

         yay = new com.kardelenapp.arrowofstickman.Yay(world, x , y+ 40 , false);

        yayJoint = world.createJoint(revoluteJointDefCreatorSolOrta(stickman4,yay.body1,Sstickman4,yay.sprite1,false));


        bodies.add(stickman1);
        bodies.add(stickman2);
        bodies.add(stickman3);
        bodies.add(stickman4);
        bodies.add(stickman5);
        bodies.add(stickman6);
        bodies.add(stickman7);



         timer = new Timer();
        final Timer.Task task = new Timer.Task() {
            @Override
            public void run() {

                gameWorld.newArrowWithListener(x-200,y+100,-  MathUtils.random(100,(c.oyunGenislik/2 + Sstickman1.getX()) ) /150  ,   MathUtils.random(-150,(c.oyunYukseklik/2 + Sstickman1.getY()) ) /150 );



            }
        };

        timer.scheduleTask(task,1.25f,2f);

    }

    Sprite spriteCreator(Texture texture, float width, float height){
        Sprite sprite = new Sprite(texture);
        sprite.setPosition( width, height);

        return sprite;
    }

    private JointDef revoluteJointDefCreator(Body bodyA, Body bodyB, boolean collideConnected, float Ax, float Ay, float Bx, float By) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(Ax,Ay);
        revoluteJointDef.localAnchorB.set(Bx,By);
        return revoluteJointDef;
    }

    private JointDef revoluteJointDefCreatorSolAlt(Body bodyA, Body bodyB, Sprite spriteA, Sprite spriteB ,Boolean collideConnected) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(-spriteA.getWidth()/2/c.PIXELS_TO_METERS,-spriteA.getHeight()/2/c.PIXELS_TO_METERS);
        revoluteJointDef.localAnchorB.set(spriteB.getWidth()/2/ c.PIXELS_TO_METERS,spriteB.getHeight()/2/ c.PIXELS_TO_METERS);
        return revoluteJointDef;
    }

    private JointDef revoluteJointDefCreatorSagAlt(Body bodyA, Body bodyB, Sprite spriteA, Sprite spriteB ,Boolean collideConnected) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(spriteA.getWidth()/2/ c.PIXELS_TO_METERS,-spriteA.getHeight()/2/c.PIXELS_TO_METERS);
        revoluteJointDef.localAnchorB.set(-spriteB.getWidth()/2/c.PIXELS_TO_METERS,spriteB.getHeight()/2/ c.PIXELS_TO_METERS);
        return revoluteJointDef;
    }

    private JointDef revoluteJointDefCreatorSolUst(Body bodyA, Body bodyB, Sprite spriteA, Sprite spriteB ,Boolean collideConnected) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(-spriteA.getWidth()/2/c.PIXELS_TO_METERS,spriteA.getHeight()/2/c.PIXELS_TO_METERS);
        revoluteJointDef.localAnchorB.set(spriteB.getWidth()/2/ c.PIXELS_TO_METERS,spriteB.getHeight()/2/c.PIXELS_TO_METERS);
        return revoluteJointDef;
    }

    private JointDef revoluteJointDefCreatorSagUst(Body bodyA, Body bodyB, Sprite spriteA, Sprite spriteB ,Boolean collideConnected) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(spriteA.getWidth()/2/ c.PIXELS_TO_METERS,spriteA.getHeight()/2/c.PIXELS_TO_METERS);
        revoluteJointDef.localAnchorB.set(-spriteB.getWidth()/2/c.PIXELS_TO_METERS,spriteB.getHeight()/2/c.PIXELS_TO_METERS);
        return revoluteJointDef;
    }

    private JointDef revoluteJointDefCreatorUstOrta(Body bodyA, Body bodyB, Sprite spriteA, Sprite spriteB ,Boolean collideConnected) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(0,spriteA.getHeight()/2/ c.PIXELS_TO_METERS);
        revoluteJointDef.localAnchorB.set(0,-spriteB.getHeight()/2/c.PIXELS_TO_METERS);
        return revoluteJointDef;
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

    private BodyDef BodyDefCreatorGround() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        return bodyDef;
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

    private FixtureDef fixtureDefCreator(CircleShape circleShape, float density, float restitution, short PHYSICS_ENTITY, short WORLD_ENTITY) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = PHYSICS_ENTITY;
        fixtureDef.filter.maskBits = WORLD_ENTITY;

        return fixtureDef;
    }

    private PolygonShape polygonShapeCreator(Sprite sprite) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(sprite.getWidth()/2 / c.PIXELS_TO_METERS, sprite.getHeight()
                /2 / c.PIXELS_TO_METERS);

        return polygonShape;
    }

    private CircleShape circlenShapeCreator (Sprite sprite) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(sprite.getWidth()/2 / c.PIXELS_TO_METERS);

        return circleShape;
    }
    private BodyDef bodyDefCreator(Sprite sprite) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        c.PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / c.PIXELS_TO_METERS);
        return bodyDef;
    }

    public void kill(){

        if (alive) {
            this.alive = false;

        }
    }

    private void disjointer(){

        if (!this.disjointed) {
            this.world.destroyJoint(this.stickman1Joint1);
            this.world.destroyJoint(this.stickman1Joint2);
            this.world.destroyJoint(this.stickman1Joint3);
            this.world.destroyJoint(this.stickman1Joint4);
            this.world.destroyJoint(this.stickman1Joint5);
            this.world.destroyJoint(this.stickman1Joint6);
            this.world.destroyJoint(this.yayJoint);
            disjointed = true;
        }
    }

    private void setPositionHelper(Sprite sprite, Body body) {
        sprite.setPosition((body.getPosition().x * c.PIXELS_TO_METERS) - sprite.getWidth()/2 ,
                (body.getPosition().y * c.PIXELS_TO_METERS) -sprite.getHeight()/2 );

        sprite.setRotation((float)Math.toDegrees(body.getAngle()));


    }


    public void getPositionHelper(){
        setPositionHelper(Sstickman1, stickman1);
        setPositionHelper(Sstickman2, stickman2);
        setPositionHelper(Sstickman3, stickman3);
        setPositionHelper(Sstickman4, stickman4);
        setPositionHelper(Sstickman5, stickman5);
        setPositionHelper(Sstickman6, stickman6);
        setPositionHelper(Sstickman7, stickman7);

        this.yay.getPositionHelper();
        this.platform.getPositionHelper();

        if (alive){
            stickman7.setLinearVelocity(0,0.4f);
        }
        else{
            disjointer();
            this.timer.stop();
        }




    }

    public void dispose(){
        //stickImg1.dispose();
        //stickImg2.dispose();
       // stickImg3.dispose();
        timer.stop();
        timer.clear();


    }

    public void getBatchDrawHelper(SpriteBatch batch){
        batchDrawHelper(Sstickman1,batch);
        batchDrawHelper(Sstickman2,batch);
        batchDrawHelper(Sstickman3,batch);
        batchDrawHelper(Sstickman4,batch);
        batchDrawHelper(Sstickman5,batch);
        batchDrawHelper(Sstickman6,batch);
        batchDrawHelper(Sstickman7,batch);

        this.yay.getBatchDrawHelper(batch);
        this.platform.getBatchDrawHelper(batch);
    }

    private void batchDrawHelper(Sprite sprite, SpriteBatch batch) {
        batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                        getScaleY(),sprite.getRotation());
    }

    public void collideListener(Body body){
        final Body bodyExternal = body;

        this.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {


                if (collisionDetection(contact, bodyExternal, stickman1)){
                    if(!alive){
                        return;
                    }


                    kill();
                }

                if (collisionDetection(contact, bodyExternal, stickman2)){
                    if(!alive){
                        return;
                    }


                    kill();
                }

                if (collisionDetection(contact, bodyExternal, stickman3)){
                    if(!alive){
                        return;
                    }


                    kill();
                }

                if (collisionDetection(contact, bodyExternal, stickman4)){
                    if(!alive){
                        return;
                    }


                    kill();
                }

                if (collisionDetection(contact, bodyExternal, stickman5)){
                    if(!alive){
                        return;
                    }


                    kill();
                }

                if (collisionDetection(contact, bodyExternal, stickman6)){
                    if(!alive){
                        return;
                    }


                    kill();
                }

                if (collisionDetection(contact, bodyExternal, stickman7)){
                    if(!alive){
                        return;
                    }


                    kill();
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

    private JointDef revoluteJointDefCreatorSolOrta(Body bodyA, Body bodyB, Sprite spriteA, Sprite spriteB , Boolean collideConnected) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(-spriteA.getWidth()/2/c.PIXELS_TO_METERS,0);
        revoluteJointDef.localAnchorB.set(spriteB.getWidth()/2/ c.PIXELS_TO_METERS,0);


        return revoluteJointDef;
    }


}
