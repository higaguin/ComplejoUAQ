package com.higaguin.uaq.Complex_folder;

import com.higaguin.uaq.Constants;

/**
 * Created by higaguin on 04/01/2017.
 */
public class Outside extends Complex {
    @Override
    public String[][] models_allowed() {
        return new String [][] {
                { Constants.MODEL_COMPLEXS_EXTERIOR_TAG, Constants.MODEL_COMPLEXS_EXTERIOR },
                { Constants.MODEL_OUTSIDE1_TAG, Constants.MODEL_OUTSIDE1 },
                { Constants.MODEL_OUTSIDE2_TAG, Constants.MODEL_OUTSIDE2 },
                { Constants.MODEL_GRASS1_TAG, Constants.MODEL_GRASS1 },
                { Constants.MODEL_BUILDING_SHELL1_TAG, Constants.MODEL_BUILDING_SHELL1 },
                { Constants.MODEL_BUILDING_SHELL2_TAG, Constants.MODEL_BUILDING_SHELL2 },
                { Constants.MODEL_BUILDING_SHELL3_TAG, Constants.MODEL_BUILDING_SHELL3 }
        };
    }

    public Outside(String model_name)
    {
        super(model_name);
    }
}
