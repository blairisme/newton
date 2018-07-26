package org.ucl.FizzyoDataProvider.Fizzyo.model;

public class PressureRecord {
    int goodBreaths;
    int breaths;
    int averageBreathLength;
    String id;
    String startTime;
    String endTime;
    String averagePressure;
    String pressureRawId;
    String patientRecordId;

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
