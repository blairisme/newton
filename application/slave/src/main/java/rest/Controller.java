package rest;

import engine.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.bridge.ExecutionNode;
import org.ucl.newton.bridge.ExecutionNodeServer;
import org.ucl.newton.bridge.ExecutionRequest;
import pojo.AnalysisRequest;
import pojo.AnalysisResponse;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

@Named
public class Controller implements ExecutionNodeServer {

    private RequestHandler requestHandler;
    private ExecutionCoordinator executionCoordinator;

    @Autowired
    public Controller(ExecutionCoordinator executionCoordinator) {
        this.requestHandler = new RequestHandler();
        this.executionCoordinator = executionCoordinator;
    }

    @Override
    public void execute(ExecutionRequest request) {
        analyse(
            request.getId(),
            request.getMainFilename(),
            request.getRepoUrl(),
            request.getType(),
            request.getOutputPattern(),
            "");
    }



//    @RequestMapping("/analyse")
    public AnalysisResponse analyse(@RequestParam(value="id") String id,
                                    @RequestParam(value="mainFilename") String mainFilename,
                                    @RequestParam(value="repoUrl") String repoUrl,
                                    @RequestParam(value="type") int type,
                                    @RequestParam(value="outputPattern") String outputPattern,
                                    @RequestParam(value="pluginJarUrl", required = false) String pluginJarUrl) {


        System.out.println("pluginJarUrl="+pluginJarUrl);
        return requestHandler.process(new AnalysisRequest(id, mainFilename, repoUrl, type, outputPattern, pluginJarUrl));
    }

//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    public ResponseEntity<Resource> download(@PathParam("filename") String filename, HttpServletRequest request) {
//
//
//        try {
//            File file = new File(filename);
//            System.out.println("^^^^^^^^^^^ f exist=" + file.exists() + " " + filename);
//            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//            // Try to determine file's content type
//            String contentType;
//            try {
//                contentType = request.getServletContext().getMimeType(file.getAbsolutePath());
//            } catch (Exception ex) {
//                contentType = "application/octet-stream";
//            }
//
//            System.out.println("contenttype= " + contentType);
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//                    .contentLength(file.length())
//                    .contentType(MediaType.parseMediaType(contentType))
//                    .body(resource);
//        }catch (Exception e){
//            System.out.println("^^^^^^^^^^^^ Exception");
//            e.printStackTrace();
//            return null;
//        }
//    }
}