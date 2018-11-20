package com.higaguin.uaq.World_folder;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.utils.Disposable;
import com.higaguin.uaq.Asset;
import com.higaguin.uaq.Builder;
import com.higaguin.uaq.Camera.CameraDebug;
import com.higaguin.uaq.Camera.CameraGUI;
import com.higaguin.uaq.Camera.CameraHelper;
import com.higaguin.uaq.Character;
import com.higaguin.uaq.Complex_folder.Building;
import com.higaguin.uaq.Constants;
import com.higaguin.uaq.Complex_folder.Info;
import com.higaguin.uaq.Loading;
import com.higaguin.uaq.MovilInput;
import com.higaguin.uaq.Point.DualPoint;
import com.higaguin.uaq.Point.EndPoint;
import com.higaguin.uaq.Point.EntryPoint;
import com.higaguin.uaq.Point.MiddlePoint;
import com.higaguin.uaq.Point.OutPoint;
import com.higaguin.uaq.Point.Point;
import com.higaguin.uaq.Screen.TransitionScreen;

/**
 * Created by higaguin on 19/10/2016.
 */
public class WorldController extends InputAdapter implements Disposable {
    public World world;
    public CameraHelper camerahelper;
    public CameraDebug cameraDebug;
    public MovilInput movilInput;
    public Builder builder;
    ClosestRayResultCallback rayCallBack;
    TransitionScreen transitionScreen;

    public Music music;
    public Loading loadInstance;
    public boolean isInfo;
    public Info infoPressed;
    private boolean allow;

    public WorldController()
    {
        init();
    }

    private void init()
    {
        Gdx.input.setInputProcessor(this);
        loadInstance = Loading.getInstance();
        loadInstance.init();
        world = new World();
        movilInput = MovilInput.getInstance();
        movilInput.init();
        builder = world.builder;
        cameraDebug = CameraDebug.getInstance();
        camerahelper = CameraHelper.getInstance();
        cameraDebug.init();
        camerahelper.init(cameraDebug, world.character.cameraCharacter);
        rayCallBack = new ClosestRayResultCallback(Vector3.Zero,Vector3.Z);
        music = Asset.getInstance().getMusic();
        music.setLooping(true);
        music.play();
        isInfo = false;
    }

    public void update(float deltaTime)
    {
        if(loadInstance.loading){
            isLoading();
        }
        else {
            world.worldInstance.update(deltaTime);
            handleCoreInput(deltaTime);
            characterCollisionsWithPoints();
        }
    }

    public void isLoading()
    {
        if(loadInstance.loadingType == Loading.LoadingType.TriadPoint) {

            if (world.building.isFloorFinish()) {
                loadInstance.loading = false;
                loadInstance.loadingType = Loading.LoadingType.NONE;
            } else {
                loadInstance.loading_status = world.building.getLoadingStatus();
            }
        }
        else if(loadInstance.loadingType ==  Loading.LoadingType.EntryPoint) {
            if (world.building.isFloorFinish()) {
                loadInstance.loadingType = Loading.LoadingType.OutPoint;
            }
            else {
                loadInstance.loading_status = world.building.getLoadingStatus();
            }
        }
        else if(loadInstance.loadingType ==  Loading.LoadingType.OutPoint) {
            if (transitionScreen.isFinish()) {
                loadInstance.loading = false;
                loadInstance.loadingType = Loading.LoadingType.NONE;
            }
        }
    }

    public void characterCollisionsWithPoints()
    {
        if(!colisionBetweenTriad()) {
            colisionBetweenDual();
        }
    }

    private void colisionBetweenDual()
    {
        for (DualPoint point : world.dualPoints) {
            if (point.checkBoundings(new Vector3(world.character.characterTransform.val[12], world.character.characterTransform.val[13], world.character.characterTransform.val[14]))) {
                if (point.getClass() == OutPoint.class) {
                    insideToOutside((OutPoint) point);
                } else if (point.getClass() == EntryPoint.class) {
                    outsideToInside((EntryPoint) point);
                }
            }
        }
    }

    private boolean colisionBetweenTriad()
    {
        for(Point point : world.triadPoints){
            if(point.checkBoundings(new Vector3(world.character.characterTransform.val[12], world.character.characterTransform.val[13], world.character.characterTransform.val[14]))) {
                if(point.getClass() == MiddlePoint.class){
                    clearWorldComplex();
                    allow = true;
                    return true;
                }
                else if(point.getClass() == EndPoint.class && allow){
                    world.building.loadFloor(((EndPoint)point).getFloor(), ((EndPoint)point).getPart());
                    world.complex = world.building.currentFloor;
                    world.worldInstance.addBulletComplex(world.complex);
                    allow = false;
                    loadInstance.loading = true;
                    loadInstance.loadingType = Loading.LoadingType.TriadPoint;
                    return true;
                }
            }
        }
        return false;
    }

    private void insideToOutside(OutPoint point)
    {
        world.character.changePosition(point.getCharacterPosition());
        clearBuildingComplex();
        com.higaguin.uaq.Complex_folder.Outside outsideaux = new com.higaguin.uaq.Complex_folder.Outside(point.getShellBuilding());
        world.worldInstance.addBulletComplex(outsideaux);
        world.worldInstance.removeBulletComplex(world.complex);
        world.complex.dispose();
        world.complex = new com.higaguin.uaq.Complex_folder.Outside(Constants.MODEL_COMPLEXS_EXTERIOR_TAG);
        world.worldInstance.addBulletComplex(world.complex);
        world.outsides.add(outsideaux);
        loadInstance.loading = true;
        loadInstance.loadingType = Loading.LoadingType.OutPoint;
        world.triadPoints.clear();
        transitionScreen = new TransitionScreen(CameraGUI.getInstance().getCamera(), Constants.TEXTURE_TRANSITION_EXT);
        transitionScreen.reset();
    }

    private void outsideToInside(EntryPoint point) {
        Gdx.app.log(this.getClass().getName(), "Entry contact");
        world.character.changePosition(point.getCharacterPosition());
        world.worldInstance.removeBulletComplex(world.complex);
        world.complex.dispose();
        world.building = new Building(point.getBuilding());
        world.building.loadFloor(1, 1);
        world.triadPoints.addAll(world.building.getTriadPoint());
        world.worldInstance.addBulletComplex(world.building);
        world.complex = world.building.currentFloor;
        world.worldInstance.addBulletComplex(world.complex);
        for (com.higaguin.uaq.Complex_folder.Outside outside : world.outsides) {
            if (outside.model_name.contentEquals(point.getShellBuilding())) {
                world.worldInstance.removeBulletComplex(outside);
                outside.dispose();
                world.outsides.removeValue(outside, false);
            }
        }
        transitionScreen = new TransitionScreen(CameraGUI.getInstance().getCamera(), point.getSplashImage());
        transitionScreen.reset();
        loadInstance.loading = true;
        loadInstance.loadingType = Loading.LoadingType.EntryPoint;
    }

    private void clearWorldComplex()
    {
        if(world.complex != null) {
            world.worldInstance.removeBulletComplex(world.complex);
            world.complex.dispose();
            world.complex = null;
        }
    }

    private void clearBuildingComplex()
    {
        if(world.building != null) {
            world.worldInstance.removeBulletComplex(world.building);
            world.building.dispose();
            world.building = null;
        }
    }

    private void handleGameInput(float deltaTime) {
        Character character = world.character;
        character.begin();
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                character.moveLeft();
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                character.moveRight();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.N)) {
                character.moveUp();
            } else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
                character.moveDown();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                character.walkFront();
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                character.walkBack();
            }
            if (Gdx.input.isTouched())
            {
                touchToInfo();
            }
        }
        else if (Gdx.app.getType() == Application.ApplicationType.Android) {
            boolean isWalking = false;
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)) {
                    Vector3 coordenates = movilInput.getRealCoordenates(Gdx.input.getX(i), Gdx.input.getY(i));
                    if (movilInput.left_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        character.moveLeft();
                    } else if (movilInput.right_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        character.moveRight();
                    } else if (movilInput.bottom_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        character.moveUp();
                    } else if (movilInput.top_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        character.moveDown();
                    }
                    else if (movilInput.cornerleft_foward_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        if(!isWalking) {
                            character.walkFront();
                            isWalking = true;
                        }
                        else
                        {
                            isWalking = false;
                        }
                    } else if (movilInput.cornerright_foward_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        if(!isWalking) {
                            character.walkFront();
                            isWalking = true;
                        }
                        else
                        {
                            isWalking = false;
                        }
                    } else if (movilInput.cornerleft_back_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        if(!isWalking) {
                            character.walkBack();
                            isWalking = true;
                        }
                        else
                        {
                            isWalking = false;
                        }
                    } else if (movilInput.cornerright_back_input.getBoundingRectangle().contains(coordenates.x, coordenates.y)) {
                        if(!isWalking) {
                            character.walkBack();
                            isWalking = true;
                        }
                        else
                        {
                            isWalking = false;
                        }
                    }
                    touchToInfo();
                }
            }
        }
        character.update(deltaTime);
    }

    private void touchToInfo()
    {
        int x1 = Gdx.input.getX();
        int y1 = Gdx.input.getY();
        Ray ray = camerahelper.getSelectedCamera().getPickRay(x1, y1);
        Vector3 rayFrom = new Vector3();
        Vector3 rayTo = new Vector3();
        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        rayCallBack.setCollisionObject(null);
        rayCallBack.setClosestHitFraction(1f);
        world.worldInstance.getWorld().rayTest(rayFrom, rayTo, rayCallBack);

        if(rayCallBack.hasHit()){
            if(rayCallBack.getCollisionObject().userData.getClass() == Info.class) {
                isInfo = true;
                infoPressed = ((Info) (rayCallBack.getCollisionObject().userData));
                infoPressed.createControls(this);
            }
        }
    }

    private void handleDebugInput(float deltaTime)
    {
        cameraDebug.velocityOrthogonal = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            cameraDebug.velocityOrthogonal = 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            cameraDebug.zoomIn();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            cameraDebug.zoomOut();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cameraDebug.moveLeft();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cameraDebug.moveRigth();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cameraDebug.moveDown();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cameraDebug.moveUp();
        }
//        else if(Gdx.input.isKeyJustPressed(Input.Keys.Q))
//        {
//            builder.changeReference();
//        }
//        else if(Gdx.input.isKeyJustPressed(Input.Keys.W))
//        {
//            builder.addBound();
//        }
//        else if(Gdx.input.isKeyJustPressed(Input.Keys.E))
//        {
//            builder.removeBound();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.Y) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            builder.rotateLeftLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.U) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            builder.rotateRightLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.Y))
//        {
//            builder.rotateLeft();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.U))
//        {
//            builder.rotateRight();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.I) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            builder.upLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.O) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            builder.downLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.I))
//        {
//            builder.up();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.O))
//        {
//            builder.down();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.C) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            builder.reduceWidthLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.C))
//        {
//            builder.expandWidthLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.V) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            builder.reduceHeightLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.V))
//        {
//            builder.expandHeightLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.B) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            builder.reduceDepthLoadPoint();
//        }
//        else if(Gdx.input.isKeyPressed(Input.Keys.B))
//        {
//            builder.expandDepthLoadPoint();
//        }
//        else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
//        {
//            builder.doJSON();
//        }
//
//        if(Gdx.input.isTouched() && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//        {
//            int x1 = Gdx.input.getX();
//            int y1 = Gdx.input.getY();
//            Ray ray = cameraDebug.camOrthogonal.getPickRay(x1, y1);
//            Vector3 position = ray.origin.cpy();
//            Gdx.app.log(this.getClass().getName(), "X: " + position.x + ", Y: " + position.y + ", Z: " + position.z);
//            builder.updateLoadPoint(new Vector3(position.x, 200, position.z));
//        }
        else if(Gdx.input.isTouched())
        {
            int x1 = Gdx.input.getX();
            int y1 = Gdx.input.getY();
            Ray ray = cameraDebug.camOrthogonal.getPickRay(x1, y1);
            Vector3 position = ray.origin.cpy();
            Gdx.app.log(this.getClass().getName(), "X: " + position.x + ", Y: " + position.y + ", Z: " + position.z);
            builder.update(new Vector3(position.x, 200, position.z));
        }
    }

    private void handleCoreInput(float deltaTime) {
        if ((Gdx.input.isKeyPressed(Input.Keys.X))) {
            camerahelper.setTopCamera();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.A))) {
            camerahelper.setRightCamera();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.Z))) {
            camerahelper.setCharacterCamera();
        }

        if(camerahelper.debug) {
            handleDebugInput(deltaTime);
        }
        else if(!isInfo)
        {
            handleGameInput(deltaTime);
        }

//        if(!isInfo)
//            handleGameInput(deltaTime);

        camerahelper.getSelectedCamera().update();
    }

    @Override
    public void dispose() {
        world.dispose();
        music.dispose();
        if(transitionScreen != null)
            transitionScreen.dispose();
    }
}
