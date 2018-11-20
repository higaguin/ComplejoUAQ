package com.higaguin.uaq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.utils.Disposable;
import com.higaguin.uaq.Camera.CameraCharacter;

/**
 * Created by higaguin on 11/09/2016.
 */
public class Character implements Disposable {
    private static Character ourInstance = new Character();

    public float velocity = 70f;
    Vector3 startPosition = new Vector3(-14.9f, 45, -559.9f);
    float height = 22f;                                 //2.0 metros
    ModelInstance instance;
    public Matrix4 characterTransform;
    btKinematicCharacterController characterController;
    btPairCachingGhostObject ghostObject;
    btConvexShape ghostShape;
    public CameraCharacter cameraCharacter;
    private Vector3 characterDirection = new Vector3(), walkDirection = new Vector3();
    private final Vector2 angleLimits = new Vector2(30,-20);
    private float angleTrack;

    public static Character getInstance() {
        return ourInstance;
    }

    private Character()
    {
        init();
    }

    private void init()
    {
        ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createCapsule(height/6, height, 8,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);
        instance.transform.trn(startPosition);

        characterTransform = instance.transform;
        angleTrack = 0;

        ghostObject = new btPairCachingGhostObject();
        ghostObject.setWorldTransform(characterTransform);
        ghostShape = new btCapsuleShape(height/6, height);
        ghostObject.setCollisionShape(ghostShape);
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        characterController = new btKinematicCharacterController(ghostObject, ghostShape, 8f);

        cameraCharacter = CameraCharacter.getInstance();
        cameraCharacter.init();
    }

    public void moveLeft()
    {
        characterTransform.rotate(0, 1, 0, 5f);
        ghostObject.setWorldTransform(characterTransform);
        cameraCharacter.moveLeft(new Vector3(characterTransform.val[12], characterTransform.val[13], characterTransform.val[14]));
    }

    public void moveRight()
    {
        instance.transform.rotate(0, 1, 0, -5f);
        ghostObject.setWorldTransform(characterTransform);
        cameraCharacter.moveRight(new Vector3(characterTransform.val[12], characterTransform.val[13], characterTransform.val[14]));
    }

    public void moveUp()
    {
        if(angleTrack < angleLimits.x) {
            cameraCharacter.moveUp();
            angleTrack +=5;
        }
    }

    public void moveDown()
    {
        if(angleTrack > angleLimits.y){
            cameraCharacter.moveDown();
            angleTrack -=5;
        }
    }

    public void walkBack()
    {
        walkDirection.add(-characterDirection.x, -characterDirection.y, -characterDirection.z);
    }

    public void walkFront() {
        walkDirection.add(characterDirection.x, characterDirection.y, characterDirection.z);
    }

    public void begin()
    {
        characterDirection.set(-1,0,0).rot(characterTransform).nor();
        walkDirection.set(0, 0, 0);
    }

    public void changePosition(Vector3 newPosition)
    {
        instance.transform.setTranslation(newPosition);
        ghostObject.setWorldTransform(characterTransform);
        cameraCharacter.updateCamera(new Vector3(characterTransform.val[12], characterTransform.val[13] + (height / 3), characterTransform.val[14]));
    }

    public void update(float deltaTime)
    {
        checkFPS();
        walkDirection.scl(velocity * deltaTime);
        characterController.setWalkDirection(walkDirection);

        ghostObject.getWorldTransform(characterTransform);
        cameraCharacter.updateCamera(new Vector3(characterTransform.val[12], characterTransform.val[13] + (height / 3), characterTransform.val[14]));
    }

    private void checkFPS()
    {
        int fps = Gdx.graphics.getFramesPerSecond();
        if(fps >= 45){
            velocity = 70;
        }else if (fps >= 30){
            velocity = 55;
        }else if(fps >= 25){
            velocity = 40;
        }else{
            velocity = 30;
        }
    }

    public void render(ModelBatch modelBatch, Environment environment)
    {
        modelBatch.render(instance, environment);
    }

    public void dispose()
    {
        characterController.dispose();
        ghostObject.dispose();
        ghostShape.dispose();
    }

    public btPairCachingGhostObject getGhostObject()
    {
        return this.ghostObject;
    }

    public btKinematicCharacterController getController()
    {
        return this.characterController;
    }

//    public BoundingBox getBounding()
//    {
//        BoundingBox boundingBox = new BoundingBox();
//        instance.calculateBoundingBox(boundingBox);
//        return boundingBox;
//    }
}