package com.higaguin.uaq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

/**
 * Created by higaguin on 11/09/2016.
 */
public class MyContactListener extends ContactListener {
    @Override
    public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1){
        Gdx.app.log(this.getClass().getName(), "obj1: " + colObj0.userData + " | obj2: " + colObj1.userData);
    }

//    @Override
//    public void onContactStarted  (int userValue0, boolean match0, int userValue1, boolean match1) {
//        if (match0) {
//            Gdx.app.log("ContactCallbackTest", "Contact started " + userValue0);
//        }
//        if (match1) {
//            Gdx.app.log("ContactCallbackTest", "Contact started " + userValue1);
//        }
//    }
//
//    @Override
//    public void onContactEnded (int userValue0, boolean match0, int userValue1, boolean match1) {
//        if (match0) {
//            Gdx.app.log("ContactCallbackTest", "Contact ended " + userValue0);
//        }
//        if (match1) {
//            Gdx.app.log("ContactCallbackTest", "Contact ended " + userValue1);
//        }
//    }
}