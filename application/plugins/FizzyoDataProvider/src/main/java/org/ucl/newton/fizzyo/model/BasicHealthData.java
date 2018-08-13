package org.ucl.newton.fizzyo.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class BasicHealthData implements FizzyoDataUnit{
    private String id;
    private List<BasicData> data;
    private String date;
    private String userId;

    @Override
    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        keys.add("id");
        keys.add("date");
        keys.add("userId");
        keys.add("data(Time:Value)");
        return keys;
    }

    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(id);
        values.add(date);
        values.add(userId);
        values.add(convertToArray(data));
        return values;
    }

    private String convertToArray(List<BasicData> data) {
        List<String> replace = new ArrayList<>();
        for(BasicData step : data){
            replace.add(step.toString());
        }
        return StringUtils.join(replace,' ');
    }


}
