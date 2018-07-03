package engine;

import datasets.Dataset;

import java.util.List;

public class REngine extends Engine {

    public REngine(String id, String repoUrl, String mainFilename, String outputPattern, List<Dataset> datasets) {
        super(id, repoUrl, mainFilename, outputPattern, datasets);
    }

    @Override
    public void build() {

    }
}
