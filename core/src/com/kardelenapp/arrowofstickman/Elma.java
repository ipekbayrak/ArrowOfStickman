package com.kardelenapp.arrowofstickman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.kardelenapp.arrowofstickman.MainClass.mainClass;

/**
 * Created by mustafa on 11/17/2017.
 */

public class Elma {   Constant c = new Constant();
    Texture img1;
    Sprite sprite1;
    Body body1;
    World world ;
    boolean alive = true;


    public Elma(World world, float x, float y   ){
        this.world  = world ;
        img1 = mainClass.assets.manager.get("elma.png", Texture.class);



        sprite1 = spriteCreator(img1,x,y);

        PolygonShape shape1 = polygonShapeCreatorAsPolygon(sprite1);

        body1 = world.createBody(bodyDefCreator(sprite1,0,0));

        FixtureDef fixtureDef1 = fixtureDefCreator ( shape1 ,0.1f,0.5f,c.ELMA_ENTTY, (short) (c.PLATFORM_ENTITY | c.STICK_ENTITY));

        body1.createFixture(fixtureDef1);

        shape1.dispose();


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

    private PolygonShape polygonShapeCreator(Sprite sprite) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(sprite.getWidth()/2 / c.PIXELS_TO_METERS, sprite.getHeight()
                /2 / c.PIXELS_TO_METERS);

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
        bodyDef.fixedRotation = false;

        bodyDef.angularDamping = 0.4f;
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

        if (alive){
            body1.setLinearVelocity(0,0.7f);
        }
        else{


        }

    }

    private void setPositionHelper(Sprite sprite, Body body) {
        sprite.setPosition((body.getPosition().x * c.PIXELS_TO_METERS) - sprite.getWidth()/2 ,
                (body.getPosition().y * c.PIXELS_TO_METERS) -sprite.getHeight()/2 );

        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
    }

    public void getBatchDrawHelper(SpriteBatch batch){
        batchDrawHelper(sprite1,batch);

    }

    private void batchDrawHelper(Sprite sprite, SpriteBatch batch) {
        batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                        getScaleY(),sprite.getRotation());
    }


    public void kill(){

        if (alive) {
            this.alive = false;

        }
    }

}