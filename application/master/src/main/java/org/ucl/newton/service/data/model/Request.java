package org.ucl.newton.service.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class Request {
    private String type;
    private String query;

    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        keys.add("location");
        return keys;
    }

    public List<String> getValues(){
        List<String> values = new ArrayList<>();
        values.add(query.replace(",",""));
        return values;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuery() {
        return query.replace(",","");
    }

    public void setQuery(String query) {
        this.query = query;
    }


}
