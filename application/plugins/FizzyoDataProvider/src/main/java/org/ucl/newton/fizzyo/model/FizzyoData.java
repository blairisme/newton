package org.ucl.newton.fizzyo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class FizzyoData {
    @SerializedName("pressure-raw")
    private List<PressureRawRecord> pressureRawRecords;

    @SerializedName("exercise-sessions")
    private List<PressureRecord> pressureRecords;

    @SerializedName("foot-steps")
    private List<FootStep> footSteps;

    @SerializedName("foot-steps-granular")
    private List<FootStepGuranular> footStepGuranulars;

    @SerializedName("games-sessions")
    private List<GameSession> gameSessions;

    @SerializedName("heart-rate")
    private List<HeartRate> heartRates;

    private Map<String,List> mapper;

    public void initMapper(){
        mapper = new HashMap<>();
        mapper.put("pressure-raw",pressureRawRecords);
        mapper.put("exercise-sessions",pressureRecords);
        mapper.put("foot-steps",footSteps);
        mapper.put("foot-steps-granular",footStepGuranulars);
        mapper.put("games-sessions",gameSessions);
        mapper.put("heart-rate",heartRates);
    }

    public List<String> getKeys(String name){
        List units = mapper.get(name);
        if (units.size() == 0)
            return new ArrayList<>();
        for(Object unit : units) {
            FizzyoDataUnit a = (FizzyoDataUnit)unit;
            return a.getKeys();
        }
        return null;
    }
    public List<List<String>> getValues(String name){
        List units = mapper.get(name);
        if (units.size() == 0)
            return new ArrayList<>();
        List<List<String>> list = getValues(units);

        return list;
    }
    private List<List<String>> getValues(List units){
        List<List<String>> list = new ArrayList<>();
        for(Object unit : units) {
            FizzyoDataUnit a = (FizzyoDataUnit)unit;
            list.add(a.getValues());
        }
        return list;
    }
    public Map<String, List> getMapper() {
        return mapper;
    }

}
