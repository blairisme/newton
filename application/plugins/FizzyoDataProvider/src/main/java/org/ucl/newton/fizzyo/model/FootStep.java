package org.ucl.newton.fizzyo.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class FootStep implements FizzyoDataUnit{
    private String id;
    private String date;
    private String userId;
    private int stepCount;


    @Override
    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        keys.add("id");
        keys.add("date");
        keys.add("userId");
        keys.add("stepCount");
        return keys;
    }

    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(id);
        values.add(date);
        values.add(userId);
        values.add(Integer.toString(stepCount));
        return values;
    }


}
