package engine;

import java.io.File;

public interface IEngine {

//    public void build(Path workspace, Path input, Path output);
        public File build(String workspacePath, String mainScript);

}
