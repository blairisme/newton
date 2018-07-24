package rest;

import com.google.common.base.Stopwatch;
import engine.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.ucl.newton.bridge.*;
import pojo.AnalysisRequest;
import pojo.AnalysisResponse;

import javax.inject.Named;
import java.net.URI;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

        Stopwatch stopWatch = Stopwatch.createStarted();
        AnalysisResponse response = requestHandler.process(new AnalysisRequest(
                request.getExperiment(),
                request.getMainFilename(),
                request.getRepoUrl(),
                request.getType(),
                request.getOutputPattern(),
                ""));
        stopWatch.stop();

        executionCoordinator.executionComplete(new ExecutionResult(
                request.getId(),
                request.getExperimentId(),
                request.getExperimentVersion(),
                URI.create("http://localhost:8080/files/projects/" + request.getExperiment() + "/log.txt"),
                URI.create("http://localhost:8080/files/projects/" + request.getExperiment() + "/data.zip"),
                URI.create("http://localhost:8080/files/projects/" + request.getExperiment() + "/visuals.zip"),
                new Date(),
                Duration.ofMillis(stopWatch.elapsed(TimeUnit.MILLISECONDS)),
                response.getErrorMessage()));
    }

    @Async
    @Override
    public void cancel(ExecutionRequest executionRequest) throws ExecutionException {
    }
}