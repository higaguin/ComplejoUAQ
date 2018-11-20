package com.higaguin.uaq.Complex_folder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.higaguin.uaq.Constants;
import com.higaguin.uaq.Point.EndPoint;
import com.higaguin.uaq.Point.MiddlePoint;
import com.higaguin.uaq.Point.TriadPoint;

/**
 * Created by higaguin on 19/10/2016.
 */
public class Building extends Complex {

    @Override
    public String[][] models_allowed() {
        return new String [][] {
                { Constants.MODEL_BUILDING1_1_TAG, Constants.MODEL_BUILDING1_1 },
                { Constants.MODEL_BUILDING2_TAG, Constants.MODEL_BUILDING2 },
                { Constants.MODEL_BUILDING3_TAG, Constants.MODEL_BUILDING3 }
        };
    }

    boolean finish;
    float loading_status;
    private Array<TriadPoint> triadPoints;
    private JsonValue floors;

    public Floor currentFloor;

    public Building(String model_json)
    {
        super(new JsonReader().parse(Gdx.files.internal(model_json)).getString("model"));
        floors = new JsonReader().parse(Gdx.files.internal(model_json)).get("floor");
        triadPoints = new Array<TriadPoint>();
        loadTriadPoint(new JsonReader().parse(Gdx.files.internal(model_json)).get("point"));

        finish = false;
        loading_status = 0.0f;
    }

    public void loadFloor(int numFloor, int numPart) {

        Thread t = new Thread( new Runnable()
        {
            public void run()
            {
                try {
                    Thread.sleep(50);
                    loading_status += 100/5.0;
                    Thread.sleep(50);
                    loading_status += 100/5.0;
                    Thread.sleep(50);
                    loading_status += 100/5.0;
                    Thread.sleep(50);
                    loading_status += 100/5.0;
                    Thread.sleep(50);
                    loading_status += 100/5.0;
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally
                {
                    finish = true;
                }
            }
        });

        finish = false;
        loading_status = 0.0f;
        for(JsonValue floor : floors) {
            if (floor.getInt("floor") == numFloor) {
                JsonValue parts = floor.get("part");
                for (JsonValue part : parts) {
                    if (part.getInt("part") == numPart) {
                        String modelFloor = part.getString("modelFloor");
                        currentFloor = new Floor(modelFloor);
                        t.start();
                    }
                }
            }
        }
    }

    private void loadTriadPoint(JsonValue points)
    {
        if (points != null) {
            for (JsonValue point : points) {
                JsonValue position = point.get("position");
                JsonValue dimension = point.get("dimension");
                String typePoint = point.getString("typePoint");
                String t = "END_POINT";
                if(typePoint.contentEquals(t)) {
                    addTriadPoint(new EndPoint(new Vector3(position.getFloat("x"),
                            position.getFloat("y"),
                            position.getFloat("z")),
                            new Vector3(dimension.getFloat("x"),
                                    dimension.getFloat("y"),
                                    dimension.getFloat("z")),
                            point.getInt("floor"),
                            point.getInt("part")
                    ));
                }
                else
                {
                    addTriadPoint(new MiddlePoint(new Vector3(position.getFloat("x"),
                            position.getFloat("y"),
                            position.getFloat("z")),
                            new Vector3(dimension.getFloat("x"),
                                    dimension.getFloat("y"),
                                    dimension.getFloat("z"))
                    ));
                }
            }
        }
    }

    public boolean isFloorFinish() { return finish; }

    public float getLoadingStatus() { return loading_status; }

    public void addTriadPoint(TriadPoint point)
    {
        triadPoints.add(point);
    }

    public Array<TriadPoint> getTriadPoint()
    {
        return triadPoints;
    }
}
