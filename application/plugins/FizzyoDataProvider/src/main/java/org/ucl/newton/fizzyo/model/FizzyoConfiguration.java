package org.ucl.newton.fizzyo.model;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class FizzyoConfiguration {
    private String clientId;
    private String syncSecret;
    private String startData;
    private String endDate;
    private String requestData;
    //default value
    public FizzyoConfiguration(){
        this.clientId = "00000000-0000-0000-0000-000000000001";
        this.syncSecret = "A1oRkpQJ0dNX1z3RA2K2zKKaLOvE2MwzA1oRkpQJ0dNxxDoJNonZWDzaLOvE2Mwz";
        this.startData = "1470744743";
        this.endDate = "1533816743";
        this.requestData = "[ \"all\" ]";
    }
    public FizzyoConfiguration(String[] configuration){
        this.clientId = configuration[0];
        this.syncSecret = configuration[1];
        this.startData = configuration[2];
        this.endDate = configuration[3];
        this.requestData = configuration[4];
    }

    public String getClientId() {
        return clientId;
    }

    public String getSyncSecret() {
        return syncSecret;
    }

    public String getStartData() {
        return startData;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRequestData() {
        return requestData;
    }
}
