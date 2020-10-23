package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.kardelenapp.arrowofstickman.Constant;
import com.kardelenapp.arrowofstickman.MainClass;

/**
 * Created by mustafa on 11/2/2017.
 */

public class Ok {

    Constant c = new Constant();
    Texture img1;
    Texture img2;
    Texture img3;

    Sprite sprite1;
    Sprite sprite2;
    Sprite sprite3;

    Body body1;
    Body body2;
    Body body3;

    Body cubuk;
    Body tuy;

    World world;

    float dragConstant =0.05f;
    float dampingConstant =1.5f;


     double angle = Math.atan2(0 - 450, 0);

    Vector2 velocity;

    Joint joint1;
    Joint joint2;
    Joint joint3;

    boolean yapisti = false;
    WeldJointDef weldJointDef;

    public Ok(World world, float x, float y, Vector2 velocity){
        this.world = world;
        this.velocity = velocity;
        img1 = MainClass.mainClass.assets.manager.get("ok.png", Texture.class);
        img2 = MainClass.mainClass.assets.manager.get("cubuk.png", Texture.class);
        img3 = MainClass.mainClass.assets.manager.get("tuy.png", Texture.class);

        sprite1 = spriteCreator(img1,x+130,y+1);
        sprite2 = spriteCreator(img2,x,y);
        sprite3 = spriteCreator(img3,x-10,y);

        PolygonShape shape1 = polygonShapeCreatorAsPolygon(sprite1 );
        PolygonShape shape2 = polygonShapeCreatorAsPolygon(sprite2 );
        PolygonShape shape3 = polygonShapeCreatorAsPolygon(sprite3 );

        body1 = world.createBody(bodyDefCreator(sprite1,0,0));  //ok ucu
        body2 = world.createBody(bodyDefCreator(sprite2,0,0));
        body3 = world.createBody(bodyDefCreator(sprite3,20f,0));



        FixtureDef fixtureDef1 = fixtureDefCreator ( shape1 ,0.5f,0.1f,c.ARROW_ENTITY, (short) (c.WORLD_ENTITY | c.STICK_ENTITY| c.ARROW_ENTITY));
        FixtureDef fixtureDef2 = fixtureDefCreator ( shape2 ,1f,0.5f,c.ARROW_ENTITY, (short) (   c.ARROW_ENTITY));
        FixtureDef fixtureDef3 = fixtureDefCreator ( shape3 ,0.1f,0.9f,c.ARROW_ENTITY, (short) (c.WORLD_ENTITY | c.STICK_ENTITY| c.ARROW_ENTITY));



        body1.createFixture(fixtureDef1);
        body2.createFixture(fixtureDef2);
        body3.createFixture(fixtureDef3);

        joint1 = world.createJoint(revoluteJointDefCreatorSolOrta(body2,body3,sprite2,sprite3,false));
        joint2 = world.createJoint(revoluteJointDefCreatorSolOrta(body1,body2,sprite1,sprite2,false));

        shape1.dispose();
        shape2.dispose();
        shape3.dispose();


/*
        Vector2 pointingDirection = body.getWorldVector(new Vector2(1f,0));
        Vector2 flightDirection = body.getLinearVelocity();
        float flightSpeed = flightDirection.len();

        float dragForceMagnitude =  flightSpeed * flightSpeed * dragConstant * body.getMass();

        Vector2 arrowTailPosition =body.getWorldPoint(new Vector2( -1.4f, 0f ) );

        flightDirection.set(flightDirection.x*dragForceMagnitude,flightDirection.y*dragForceMagnitude);
        body.applyForce(flightDirection, arrowTailPosition ,true);
        body.setAngularDamping(3f);

        body.applyForceToCenter(5f,0f,true);
        */
        body2.setLinearVelocity(velocity);



    }


    private PolygonShape polygonShapeCreatorAsPolygon(Sprite sprite) {
        PolygonShape polygonShape = new PolygonShape();
        Vector2 vector1 = new Vector2(-sprite.getWidth() /2 / c.PIXELS_TO_METERS,0);
        Vector2 vector2 = new Vector2(0f,-sprite.getHeight() /2 / c.PIXELS_TO_METERS);
        Vector2 vector3 = new Vector2(sprite.getWidth() /2 / c.PIXELS_TO_METERS,0f);
        Vector2 vector4 = new Vector2(0f,sprite.getHeight() /2 / c.PIXELS_TO_METERS);
        polygonShape.set(new Vector2[]{vector1,vector2,vector3,vector4});

        return polygonShape;
    }

    Sprite spriteCreator(Texture texture, float width, float height){
        Sprite sprite = new Sprite(texture);
        sprite.setPosition( width, height);

        return sprite;
    }


    private BodyDef bodyDefCreator(Sprite sprite, float linearDamping, float angularDamping) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        c.PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / c.PIXELS_TO_METERS);
        bodyDef.bullet = true;
        bodyDef.linearDamping = linearDamping;
        bodyDef.angularDamping = angularDamping;
        bodyDef.angle = (float) Math.atan2(velocity.y,velocity.x);

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

    public void getPositionHelper(){
        setPositionHelper(sprite1, body1);
        setPositionHelper(sprite2, body2);
        setPositionHelper(sprite3, body3);


           // stickman7.setLinearVelocity(0,0.3f);


    }

    private void setPositionHelper(Sprite sprite, Body body) {
        sprite.setPosition((body.getPosition().x * c.PIXELS_TO_METERS) - sprite.getWidth()/2 ,
                (body.getPosition().y * c.PIXELS_TO_METERS) -sprite.getHeight()/2 );

        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
    }

    public void getBatchDrawHelper(SpriteBatch batch){
        batchDrawHelper(sprite1,batch);
        batchDrawHelper(sprite2,batch);
        batchDrawHelper(sprite3,batch);
    }

    private void batchDrawHelper(Sprite sprite, SpriteBatch batch) {
        batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                        getScaleY(),sprite.getRotation());
    }

    private JointDef revoluteJointDefCreatorSolOrta(Body bodyA, Body bodyB, Sprite spriteA, Sprite spriteB , Boolean collideConnected) {

        WeldJointDef revoluteJointDef = new WeldJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = collideConnected;
        revoluteJointDef.localAnchorA.set(-spriteA.getWidth()/2/c.PIXELS_TO_METERS,0);
        revoluteJointDef.localAnchorB.set(spriteB.getWidth()/2/ c.PIXELS_TO_METERS,0);


        return revoluteJointDef;
    }

    public void dispose() {
       // img1.dispose();
        //img2.dispose();
       // img3.dispose();

    }

    public void yapis(Body body){
        if (yapisti)
        {return;}

        yapisti = true;
        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.bodyA = body1;
        weldJointDef.bodyB = body;



        weldJointDef.localAnchorA.set(sprite1.getWidth()/2/c.PIXELS_TO_METERS,0);

        Vector2 v = body1.getWorldPoint( new Vector2(sprite1.getWidth()/2/c.PIXELS_TO_METERS,0) );

        weldJointDef.localAnchorB.set( body.getLocalPoint(v).x  ,body.getLocalPoint(v).y);
        //weldJointDef.referenceAngle = body1.getAngle() - body.getAngle();
        world.createJoint(weldJointDef);


    }
}


