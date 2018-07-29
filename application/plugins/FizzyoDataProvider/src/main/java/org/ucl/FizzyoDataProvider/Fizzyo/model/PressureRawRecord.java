package org.ucl.FizzyoDataProvider.Fizzyo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class provide org.ucl.FizzyoDataProvider.Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class PressureRawRecord {
    String id;
    boolean processed;
    String startTime;
    String endTime;
    String pressureValues;
    String patientRecordId;

    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        keys.add("pressureValues");
        return keys;
    }
    public List<String> getValues(){
        List<String> values = new ArrayList<>();
        values.add(pressureValues.replace(","," "));
        return values;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getPressureValues() { return pressureValues; }
    public void setPressureValues(String pressureValues) { this.pressureValues = pressureValues; }
    public String getPatientRecordId() { return patientRecordId; }
    public void setPatientRecordId(String patientRecordId) { this.patientRecordId = patientRecordId; }
}
