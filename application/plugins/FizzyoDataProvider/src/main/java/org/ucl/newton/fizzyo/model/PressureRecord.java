package org.ucl.newton.fizzyo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a record type that stores pressure information.
 *
 * @author Xiaolong Chen
 */
public class PressureRecord {
    private int goodBreaths;
    private int breaths;
    private int averageBreathLength;
    private String id;
    private String startTime;
    private  String endTime;
    private  String averagePressure;
    private  String pressureRawId;
    private  String patientRecordId;

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
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(patientRecordId);
        values.add(startTime);
        values.add(endTime);
        values.add(Integer.toString(breaths));
        values.add(Integer.toString(goodBreaths));
        values.add(averagePressure);
        values.add(Integer.toString(averageBreathLength));
        return values;
    }
    public int getGoodBreaths() { return goodBreaths; }
    public void setGoodBreaths(int goodBreaths) { this.goodBreaths = goodBreaths; }
    public int getBreaths() { return breaths; }
    public void setBreaths(int breaths) { this.breaths = breaths; }
    public int getAverageBreathLength() { return averageBreathLength; }
    public void setAverageBreathLength(int averageBreathLength) { this.averageBreathLength = averageBreathLength; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getAveragePressure() { return averagePressure; }
    public void setAveragePressure(String averagePressure) { this.averagePressure = averagePressure; }
    public String getPressureRawId() { return pressureRawId; }
    public void setPressureRawId(String pressureRawId) { this.pressureRawId = pressureRawId; }
    public String getPatientRecordId() { return patientRecordId; }
    public void setPatientRecordId(String patientRecordId) { this.patientRecordId = patientRecordId; }


}
