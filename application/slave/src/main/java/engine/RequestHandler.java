package engine;

import helpers.LogHelper;
import helpers.Constants;
import pojo.AnalysisRequest;
import pojo.AnalysisResponse;
import pojo.AnalysisResults;

import java.io.File;



public class RequestHandler {


    public AnalysisResponse process(AnalysisRequest request){
        Engine engine = null;
        AnalysisRequest.ENGINE_TYPE engine_type = AnalysisRequest.ENGINE_TYPE.values()[request.getType()];
        switch (engine_type){
            case PYTHON:
                engine = new PythonEngine(request.getId(), request.getRepoUrl(), request.getMainFilename(), request.getOutputPattern(), null);
                break;
            case R:
                engine = new REngine(request.getId(), request.getRepoUrl(), request.getMainFilename(), request.getOutputPattern(),null);
                break;
        }

        AnalysisResponse response;
        try {
            AnalysisResults results = engine.run();
          //  String output = LogHelper.getOutputLog(request.getId());
          //  String logFileUrl = LogHelper.getLogFileUrl(request.getId());
          //  String zipOutputFileUrl = engine.getZipOutputFileUrl();
            response = results.toAnalysisResponse();
            response.setStatus( AnalysisResponse.RESPONSE_STATUS.SUCCESS.ordinal());

        } catch (Exception e)
        {
            response = new AnalysisResponse(request.getId(), null, null, null);
            response.setStatus(AnalysisResponse.RESPONSE_STATUS.FAILURE.ordinal());

            e.printStackTrace();
        }
        return response;
    }

}
