package engine;

import datasets.Dataset;
import helpers.LogHelper;


import java.io.File;
import java.util.List;



public class PythonEngine extends Engine {

    public PythonEngine(String id, String repoUrl, String mainFilename, String outputPattern, List<Dataset> datasets) {
        super(id, repoUrl, mainFilename,outputPattern, datasets);
    }

    @Override
    public void build() {
        String cmnd = "python "+ mMainFile.getAbsolutePath();
        System.out.println("zzz cmnd = " + cmnd);
        mLogFile = LogHelper.executeCmnd(cmnd, true, mProjectFile.getAbsolutePath());

    }
}
