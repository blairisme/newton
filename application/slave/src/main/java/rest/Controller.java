package rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import engine.RequestHandler;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pojo.AnalysisRequest;
import pojo.AnalysisResponse;

import javax.servlet.http.HttpServletRequest;


@RestController
public class Controller {


    private RequestHandler mRequestHandler = new RequestHandler();

    @RequestMapping("/analyse")
    public AnalysisResponse analyse(@RequestParam(value="id") String id,
                                    @RequestParam(value="mainFilename") String mainFilename,
                                    @RequestParam(value="repoUrl") String repoUrl,
                                    @RequestParam(value="type") int type,
                                    @RequestParam(value="outputPattern") String outputPattern) {

        return mRequestHandler.process(new AnalysisRequest(id, mainFilename, repoUrl, type, outputPattern));
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@RequestParam("filename") String filename, HttpServletRequest request) {


        try {
            File file = new File(filename);
            System.out.println("^^^^^^^^^^^ f exist=" + file.exists() + " " + filename);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            // Try to determine file's content type
            String contentType;
            try {
                contentType = request.getServletContext().getMimeType(file.getAbsolutePath());
            } catch (Exception ex) {
                contentType = "application/octet-stream";
            }

            System.out.println("contenttype= " + contentType);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }catch (Exception e){
            System.out.println("^^^^^^^^^^^^ Exception");
            e.printStackTrace();
            return null;
        }
    }





}