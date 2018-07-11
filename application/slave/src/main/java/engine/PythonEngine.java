package engine;

import helpers.LogHelper;

import java.io.File;


public class PythonEngine implements IEngine {


    @Override
    public File build(String workspacePath, String mainScriptPath) {
        String cmnd = " python";
        File output = LogHelper.startProcess(cmnd, mainScriptPath, true, workspacePath);
        return output;

    }

}
