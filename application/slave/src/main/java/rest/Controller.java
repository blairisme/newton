package rest;

import engine.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.bridge.ExecutionNodeServer;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import pojo.AnalysisRequest;
import pojo.AnalysisResponse;

import javax.inject.Named;
import java.net.URI;

@Named
public class Controller implements ExecutionNodeServer
{
    private RequestHandler requestHandler;
    private ExecutionCoordinator executionCoordinator;

    @Autowired
    public Controller(ExecutionCoordinator executionCoordinator) {
        this.requestHandler = new RequestHandler();
        this.executionCoordinator = executionCoordinator;
    }

    @Async
    @Override
    public void execute(ExecutionRequest request) {
        AnalysisResponse response = requestHandler.process(new AnalysisRequest(
                request.getExperiment(),
                request.getMainFilename(),
                request.getRepoUrl(),
                request.getType(),
                request.getOutputPattern(),
                ""));

        executionCoordinator.executionComplete(new ExecutionResult(
                request.getId(),
                request.getExperimentId(),
                URI.create("http://localhost:8080/files/projects/" + request.getExperiment() + "/log.txt"),
                URI.create("http://localhost:8080/files/projects/" + request.getExperiment() + "/output.zip"),
                response.getErrorMessage()));
    }
}