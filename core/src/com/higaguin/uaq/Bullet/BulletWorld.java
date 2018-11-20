package com.higaguin.uaq.Bullet;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btDefaultMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Disposable;
import com.higaguin.uaq.Character;

/**
 * Created by higaguin on 11/09/2016.
 */
public class BulletWorld implements Disposable {
    public static final BulletWorld ourInstance = new BulletWorld();

    private btDefaultCollisionConfiguration collisionConfiguration;
    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase broadphase;
    private btSequentialImpulseConstraintSolver solver;
    private btDiscreteDynamicsWorld world;
    private btAxisSweep3 sweep;
    private btGhostPairCallback ghostPairCallback;
    private DebugDrawer debugDrawer;

    public static BulletWorld getInstance() {
        return ourInstance;
    }
    public boolean debug;

    private BulletWorld(){}

    public void init()
    {
        Bullet.init();
        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btDbvtBroadphase();
        sweep = new btAxisSweep3(new Vector3(-1000, -1000, -1000), new Vector3(1000, 1000, 1000));
        solver = new btSequentialImpulseConstraintSolver();
        world = new btDiscreteDynamicsWorld(dispatcher, sweep, solver, collisionConfiguration);
        world.setGravity(new Vector3(0, -9.8f, 1f));
        ghostPairCallback = new btGhostPairCallback();
        sweep.getOverlappingPairCache().setInternalGhostPairCallback(ghostPairCallback);

        debugDrawer = new DebugDrawer();
        debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
        world.setDebugDrawer(debugDrawer);
        debug = false;
    }

    public void addBulletMainCharacter(Character character)
    {
        character.getGhostObject().userData = "Character";
        world.addCollisionObject(character.getGhostObject(),
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.StaticFilter | btBroadphaseProxy.CollisionFilterGroups.DefaultFilter));
        world.addAction(character.getController());
    }

    public void addBulletComplex(com.higaguin.uaq.Complex_folder.Complex complex)
    {
        addBulletObject(complex);
    }

    public void addBulletConstraint(com.higaguin.uaq.Complex_folder.Constraint constraint)
    {
        addBulletObject(constraint);
    }
    //
    public void addBulletInfo(com.higaguin.uaq.Complex_folder.Info info)
    {
        addBulletObject(info);
    }

    private void addBulletObject(BulletObject bullet_object)
    {
        btDefaultMotionState motionState = new btDefaultMotionState(bullet_object.instance.transform);
        btCollisionShape complexShape = Bullet.obtainStaticNodeShape(bullet_object.model.nodes);
        btCollisionObject obj = new btCollisionObject();
        obj.setWorldTransform(bullet_object.instance.transform);
        obj.setCollisionFlags(btCollisionObject.CollisionFlags.CF_STATIC_OBJECT);
        obj.setCollisionShape(complexShape);
        btRigidBody body = new btRigidBody(0, motionState, complexShape, Vector3.Zero);
        body.userData = bullet_object;
        world.addCollisionObject(body);
        bullet_object.setBulletObject(motionState, body);
    }

    public void removeBulletComplex(com.higaguin.uaq.Complex_folder.Complex complex)
    {
        world.removeRigidBody(complex.body);
    }

    public void update(float delta){
        world.stepSimulation(delta, 2);
    }

    public void updateDebugDraw(com.badlogic.gdx.graphics.Camera camera)
    {
        debugDrawer.begin(camera);
        world.debugDrawWorld();
        debugDrawer.end();
    }

    public btDiscreteDynamicsWorld getWorld(){
        return world;
    }

    @Override
    public void dispose(){
        world.dispose();
        collisionConfiguration.dispose();
        dispatcher.dispose();
        broadphase.dispose();
        solver.dispose();
        world.dispose();
        sweep.dispose();
        ghostPairCallback.dispose();
    }
}