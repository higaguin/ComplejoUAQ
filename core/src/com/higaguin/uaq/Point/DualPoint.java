package com.higaguin.uaq.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by higaguin on 09/01/2017.
 */
public class DualPoint extends Point {
    private Vector3 characterPosition;
    private String shell_building;

    public DualPoint(Vector3 position, Vector3 dimension, Vector3 characterPosition, String shell_building, Color color) {
        super(position, dimension, color);
        this.characterPosition = characterPosition;
        this.shell_building = shell_building;
    }

    public Vector3 getCharacterPosition() { return characterPosition; }

    public String getShellBuilding() { return shell_building; }

    @Override
    public boolean checkBoundings(Vector3 inPosition) {
        return boundingBox.contains(inPosition);
    }

    public static Array<DualPoint> constructPointFromMap(String point_json)
    {
        Array<DualPoint> result_points = new Array<DualPoint>();
        FileHandle point_map = Gdx.files.internal(point_json);
        JsonValue points =  new JsonReader().parse(point_map).get("point");
        if(points != null) {
            for (JsonValue point : points) {
                String modelShell = point.getString("modelShell");
                String modelJSON = point.getString("modelJSON");
                String splashImage = point.getString("splashImage");
                JsonValue entryPoints = point.get("entryPoint");
                JsonValue outPoints = point.get("outPoint");
                if(entryPoints != null) {
                    for(JsonValue entryPoint : entryPoints)
                    {
                        JsonValue entry_position = entryPoint.get("position");
                        JsonValue dimension = entryPoint.get("dimension");
                        JsonValue characterPosition = entryPoint.get("characterPosition");
                        result_points.add(new EntryPoint(new Vector3(entry_position.getFloat("x"), entry_position.getFloat("y"), entry_position.getFloat("z")),
                                new Vector3(dimension.getFloat("x"), dimension.getFloat("y"), dimension.getFloat("z")),
                                new Vector3(characterPosition.getFloat("x"), characterPosition.getFloat("y"), characterPosition.getFloat("z")),
                                modelJSON, splashImage, modelShell));
                    }
                }
                if(outPoints != null) {
                    for(JsonValue outPoint : outPoints)
                    {
                        JsonValue entry_position = outPoint.get("position");
                        JsonValue dimension = outPoint.get("dimension");
                        JsonValue characterPosition = outPoint.get("characterPosition");
                        result_points.add(new com.higaguin.uaq.Point.OutPoint(new Vector3(entry_position.getFloat("x"), entry_position.getFloat("y"), entry_position.getFloat("z")),
                                new Vector3(dimension.getFloat("x"), dimension.getFloat("y"), dimension.getFloat("z")),
                                new Vector3(characterPosition.getFloat("x"), characterPosition.getFloat("y"), characterPosition.getFloat("z")),
                                modelShell));
                    }
                }
            }
        }
        return result_points;
    }
}
