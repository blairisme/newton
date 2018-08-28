package org.ucl.newton.fizzyo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class PressureRawRecord implements FizzyoDataUnit{
    private String id;
    private String startTime;
    private String endTime;
    private String pressureValues;
    private String patientRecordId;
    @Override
    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        keys.add("id");
        keys.add("startTime");
        keys.add("endTime");
        keys.add("patientRecord");
        keys.add("pressureValues");
        return keys;
    }
    @Override
    public List<String> getValues(){
        List<String> values = new ArrayList<>();
        values.add(id);
        values.add(startTime);
        values.add(endTime);
        values.add(patientRecordId);
        values.add(pressureValues.replace(","," "));
        return values;
    }
}
