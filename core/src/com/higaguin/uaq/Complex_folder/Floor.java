package com.higaguin.uaq.Complex_folder;

import com.higaguin.uaq.Constants;

/**
 * Created by higaguin on 28/12/2016.
 */
public class Floor extends Complex {
    @Override
    public String[][] models_allowed() {
        return new String [][] {
                { Constants.MODEL_COMPLEXS_BUILDING1_FLOOR1_PART1_TAG, Constants.MODEL_COMPLEXS_BUILDING1_FLOOR1_PART1 },
                { Constants.MODEL_COMPLEXS_BUILDING1_FLOOR1_PART2_TAG, Constants.MODEL_COMPLEXS_BUILDING1_FLOOR1_PART2 },
                { Constants.MODEL_COMPLEXS_BUILDING1_FLOOR2_PART1_TAG, Constants.MODEL_COMPLEXS_BUILDING1_FLOOR2_PART1 },
                { Constants.MODEL_COMPLEXS_BUILDING1_FLOOR3_PART1_TAG, Constants.MODEL_COMPLEXS_BUILDING1_FLOOR3_PART1 },
                { Constants.MODEL_COMPLEXS_BUILDING2_FLOOR1_PART1_TAG, Constants.MODEL_COMPLEXS_BUILDING2_FLOOR1_PART1 },
                { Constants.MODEL_COMPLEXS_BUILDING2_FLOOR1_PART2_TAG, Constants.MODEL_COMPLEXS_BUILDING2_FLOOR1_PART2 },
                { Constants.MODEL_COMPLEXS_BUILDING2_FLOOR2_PART1_TAG, Constants.MODEL_COMPLEXS_BUILDING2_FLOOR2_PART1 },
                { Constants.MODEL_COMPLEXS_BUILDING2_FLOOR2_PART2_TAG, Constants.MODEL_COMPLEXS_BUILDING2_FLOOR2_PART2 },
                { Constants.MODEL_COMPLEXS_BUILDING2_FLOOR3_PART1_TAG, Constants.MODEL_COMPLEXS_BUILDING2_FLOOR3_PART1 },
                { Constants.MODEL_COMPLEXS_BUILDING3_FLOOR1_PART1_TAG, Constants.MODEL_COMPLEXS_BUILDING3_FLOOR1_PART1 }
        };
    }

    public Floor(String model_name)
    {
        super(model_name);
    }
}