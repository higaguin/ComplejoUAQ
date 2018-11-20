package com.higaguin.uaq.Bullet;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by higaguin on 25/10/2016.
 */
public class BulletObject implements Disposable {
    public Model model;
    public ModelInstance instance;
    private btMotionState motionState;
    public btRigidBody body;

    public BulletObject(){}

    public void setBulletObject(btMotionState motionState, btRigidBody body)
    {
        this.motionState = motionState;
        this.body = body;
    }

    @Override
    public void dispose(){
//        model.dispose();
        if(motionState != null){
            motionState.dispose();
        }
        body.dispose();
    }
}