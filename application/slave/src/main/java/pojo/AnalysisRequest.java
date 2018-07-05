package pojo;

public class AnalysisRequest {


    public enum ENGINE_TYPE {
        PYTHON,
        R,
        PLUGIN
    }

    private String mMainFilename;
    private String mRepoUrl;
    private int mType;
    private final String mId;
    private final String mOutputPattern;
    private final String mPluginJarUrl;

    public AnalysisRequest(String id, String mainFilename, String repoUrl, int type, String outputPattern, String jarUrl) {
        this.mMainFilename = mainFilename;
        this.mRepoUrl = repoUrl;
        this.mType = type;
        this.mId = id;
        this.mOutputPattern = outputPattern;
        this.mPluginJarUrl = jarUrl;
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

    public String getPluginJarUrl(){
        return mPluginJarUrl;
    }

    public int getType() {
        return mType;
    }

    public String getOutputPattern(){
        return mOutputPattern;
    }
}
