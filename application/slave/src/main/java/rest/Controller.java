package rest;

import engine.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.ucl.newton.bridge.*;
import pojo.AnalysisRequest;
import pojo.AnalysisResponse;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

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
                request.getId(),
                request.getMainFilename(),
                request.getRepoUrl(),
                request.getType(),
                request.getOutputPattern(),
                ""));
        executionCoordinator.executionComplete(new ExecutionResult(
                response.getId(),
                response.getErrorMessage()));
    }
}