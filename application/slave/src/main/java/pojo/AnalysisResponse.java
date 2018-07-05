package pojo;

public class AnalysisResponse {

    public enum RESPONSE_STATUS {
        SUCCESS,
        FAILURE,
    }

    private final String mId;
    private final String mErrorMessage;
    private int mStatus;
    private String mLogFileUrl;
    private String mZipOutputFile;

    public AnalysisResponse(String id, String output, String logFileUrl, String zipOutputFile) {
        this.mId = id;
        this.mErrorMessage = output;
        this.mLogFileUrl = logFileUrl;
        this.mZipOutputFile = zipOutputFile;
    }

    public void setStatus(int status){
        mStatus = status;
    }

    public String getId() {
        return mId;
    }

    public int getStatus() {
        return mStatus;
    }

    public String getLogFileUrl() {
        return mLogFileUrl;
    }

    public String getZipOutputFileUrl() {
        return mZipOutputFile;
    }


    public String getErrorMessage() {
        return mErrorMessage;
    }


}
