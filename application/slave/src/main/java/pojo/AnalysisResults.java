package pojo;

import helpers.FilesHelper;

import java.io.File;

public class AnalysisResults {

    private File mLogFile;
    private File mZipOutputFile;
    private String mId;

    public AnalysisResults(String id){
        mId = id;
    }

    public File getLogFile() {
        return mLogFile;
    }

    public File getZipOutputFile() {
        return mZipOutputFile;
    }

    public void setLogFile(File logFile) {
        this.mLogFile = logFile;
    }

    public void setZipOutputFile(File zipOutputFile) {
        this.mZipOutputFile = zipOutputFile;
    }

    public AnalysisResponse toAnalysisResponse(){
        String logFileUrl = FilesHelper.getFileUrl(mLogFile);
        String zipOutputUrl = FilesHelper.getFileUrl(mZipOutputFile);
        return new AnalysisResponse(mId, "", logFileUrl, zipOutputUrl);
    }
}
