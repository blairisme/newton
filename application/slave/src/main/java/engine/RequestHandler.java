package engine;

import exceptions.AnalysisException;
import pojo.AnalysisRequest;
import pojo.AnalysisResponse;
import pojo.AnalysisResults;
import security.PluginClassLoader;

import java.net.URL;


public class RequestHandler {


    public AnalysisResponse process(AnalysisRequest request) {
        IEngine engine = null;
        AnalysisRequest.ENGINE_TYPE engine_type = AnalysisRequest.ENGINE_TYPE.values()[request.getType()];
        switch (engine_type) {
            case PYTHON:
                engine = new PythonEngine();
                break;
            case R:
                engine = new REngine();
                break;
            case PLUGIN:
                engine = getPluginEngine(request.getPluginJarUrl());
                break;

            //...handle custom engines
        }

        System.out.println("^^^^^^^HERE");
        Executor executor = new Executor(request.getId(), engine, request.getRepoUrl(), request.getMainFilename(), request.getOutputPattern(), null);

        AnalysisResponse response;
        try {
            AnalysisResults results = executor.run();
            response = results.toAnalysisResponse();
            response.setStatus(AnalysisResponse.RESPONSE_STATUS.SUCCESS.ordinal());

        } catch (AnalysisException ae) {
            response = new AnalysisResponse(request.getId(), ae.getMessage(), null, null);
            response.setStatus(AnalysisResponse.RESPONSE_STATUS.FAILURE.ordinal());
            ae.printStackTrace();
        } catch (Exception e) {
            response = new AnalysisResponse(request.getId(), "Error", null, null);
            response.setStatus(AnalysisResponse.RESPONSE_STATUS.FAILURE.ordinal());
            e.printStackTrace();
        }
        return response;
    }

    private IEngine getPluginEngine(String url) {
        try {
            System.out.println("**** get Plugin Engin url="+url);
            URL jarURL = new URL(url);
            ClassLoader pluginLoader = new PluginClassLoader(jarURL);

            Class<?> pluginClass = pluginLoader.loadClass("engine.ZEngine");
            return (IEngine) pluginClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
