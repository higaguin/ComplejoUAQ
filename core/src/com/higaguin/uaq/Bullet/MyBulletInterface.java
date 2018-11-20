package com.higaguin.uaq.Bullet;

import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by higaguin on 11/09/2016.
 */
public interface MyBulletInterface extends Disposable {
    public void init();
    public void update(float delta);
    public void remove(btRigidBody body);
    public btDiscreteDynamicsWorld getWorld();
}
