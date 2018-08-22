package org.ucl.newton.fizzyo.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class SyncData {
    private List<String> requestedData;
    private FizzyoData data;

    public List<String> getRequestedData(){
        if(requestedData.contains("all")){
            return new ArrayList<>(data.getMapper().keySet());
        }
        return requestedData;
    }
    public FizzyoData getData(){
        return data;
    }

}
