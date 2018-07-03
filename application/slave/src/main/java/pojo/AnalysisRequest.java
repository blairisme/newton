package pojo;

public class AnalysisRequest {


    public enum ENGINE_TYPE {
        PYTHON,
        R,
    }

    private String mMainFilename;
    private String mRepoUrl;
    private int mType;
    private final String mId;
    private final String mOutputPattern;

    public AnalysisRequest(String id, String mainFilename, String repoUrl, int type, String outputPattern) {
        this.mMainFilename = mainFilename;
        this.mRepoUrl = repoUrl;
        this.mType = type;
        this.mId = id;
        this.mOutputPattern = outputPattern;
    }

    public String getId(){
        return mId;
    }

    public String getMainFilename() {
        return mMainFilename;
    }

    public String getRepoUrl() {
        return mRepoUrl;
    }

    public int getType() {
        return mType;
    }

    public String getOutputPattern(){
        return mOutputPattern;
    }
}
