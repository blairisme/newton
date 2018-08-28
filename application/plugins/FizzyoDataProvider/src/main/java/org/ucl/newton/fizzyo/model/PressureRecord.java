package org.ucl.newton.fizzyo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class PressureRecord implements FizzyoDataUnit{
    private int goodBreaths;
    private String breaths;
    private String averageBreathLength;
    private String startTime;
    private String endTime;
    private String averagePressure;
    private String patientRecordId;
    @Override
    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        keys.add("patientRecordId");
        keys.add("startTime");
        keys.add("endTime");
        keys.add("breaths");
        keys.add("goodBreaths");
        keys.add("averagePressure");
        keys.add("averageBreathLength");
        return keys;
    }
    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(patientRecordId);
        values.add(startTime);
        values.add(endTime);
        values.add(breaths);
        values.add(Integer.toString(goodBreaths));
        values.add(averagePressure);
        values.add(averageBreathLength);
        return values;
    }
}
