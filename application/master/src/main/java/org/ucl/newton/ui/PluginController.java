package org.ucl.newton.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.sdk.publisher.FTPConfig;
import org.ucl.newton.service.publisher.PublisherService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Instances of this class provide an MVC controller for web pages used to
 * list and manage plugin.
 *
 * @author Xiaolong Chen
 */
@Controller
@Scope("session")
@SuppressWarnings("unused")
public class PluginController {
    ApplicationStorage storage;
    PublisherService publisherService;
    @Inject
    public PluginController(ApplicationStorage applicationStorage, PublisherService publisherService){
        this.storage = applicationStorage;
        this.publisherService = publisherService;
    }

    @RequestMapping(value = "/publisherSetting", method = RequestMethod.GET)
    public String getPublisher(@RequestParam(required=false) String id, ModelMap model){
        Collection<DataPublisher> publishers = publisherService.getPublishers();
        if (id == null || id ==""){
            List<String> ids = new ArrayList<>();
            for(DataPublisher publisher : publishers){
                ids.add(publisher.getIdentifier());
            }
            model.addAttribute("publishers",ids);
        }else {
            DataPublisher publisher = getPublisherByIdentifier(id);
            if (publisher!=null)
                setModel(publisher,model);

            model.addAttribute("identifier",id);
        }
        return "plugin/publisherSetting";
    }

    @RequestMapping(value = "/DREPublisherSetting", method = RequestMethod.POST)
    public String setConfig(@RequestParam String identifier,
                            @RequestParam String hostName,
                            @RequestParam String port,
                            @RequestParam String userName,
                            @RequestParam String userPassword,
                            ModelMap model){

        DataPublisher publisher = getPublisherByIdentifier(identifier);

        if(publisher != null){
            FTPConfig config = new FTPConfig(hostName,userName,userPassword,Integer.parseInt(port));
            try {
                OutputStream output = storage.getOutputStream("publisher", publisher.getConfigName());
                FileUtils.writeToFile(output,config,FTPConfig.class);
            }catch (IOException e){
                e.printStackTrace();
            }
            publisher.setConfig(config);
        }
        return "redirect:/publisherSetting";
    }

    @RequestMapping(value = "/FizzyoData", method = RequestMethod.GET)
    public String getData(ModelMap model){
        Path path = Paths.get(storage.getRootPath());
        path = path.resolve("Fizzyo").resolve("authCode");
        String authCode = FileUtils.readFile(path.toFile());

        model.addAttribute("authCode",authCode);
        return "plugin/FizzyoData";
    }

    @RequestMapping(value = "/SetAuthCode", method = RequestMethod.POST)
    public String setAuthCode(@RequestParam String authCode, ModelMap model){
        try{
            OutputStream output = storage.getOutputStream("Fizzyo","authCode");
            output.write(authCode.getBytes("utf-8"));
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/FizzyoData";
    }

    @RequestMapping(value = "/weatherSetting", method = RequestMethod.GET)
    public String list(ModelMap model) {
        Path path = Paths.get(storage.getRootPath());
        path = path.resolve("weather").resolve("setting");

        List<String[]> properties = FileUtils.readCSV(path.toString());

        model.addAttribute("properties",properties);

        return "plugin/weatherSetting";
    }

    @RequestMapping(value = "/weatherSetting", method = RequestMethod.POST)
    public String set(
        @RequestParam String key,
        @RequestParam(required = false) Collection<String> items,
        ModelMap model)
    {
        List<List<String>> properties = new ArrayList<>();
        properties.add(getHeader());
        properties.addAll(getProperties(key,items));
        try {
            OutputStream output = storage.getOutputStream("weather","setting");
            FileUtils.writeCSV(output,properties);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/weatherSetting";
    }

    private void setModel(DataPublisher publisher, ModelMap model) {
        String configFileName = publisher.getConfigName();
        Path path = Paths.get(storage.getRootPath()).resolve("publisher").resolve(configFileName);
        File configFile = path.toFile();
        if (!configFile.exists())
            configFile = PathUtils.getConfigurationPath().resolve(configFileName).toFile();

        String configStr = FileUtils.readFile(configFile);
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(configStr,JsonObject.class);
        model.addAttribute("userName",json.get("userName").getAsString());
        model.addAttribute("userPassword",json.get("userPassword").getAsString());
        model.addAttribute("hostName",json.get("hostName").getAsString());
        model.addAttribute("port",json.get("port").getAsInt());

    }

    private DataPublisher getPublisherByIdentifier(String identifier) {
        Collection<DataPublisher> publishers = publisherService.getPublishers();
        for(DataPublisher publisher : publishers){
            if (identifier.equals(publisher.getIdentifier()))
                return publisher;
        }
        return null;
    }

    private List<String> getHeader() {
        String[] header = new String[4];
        header[0] = "city";
        header[1] = "country";
        header[2] = "date";
        header[3] = "key";
        return Arrays.asList(header);
    }

    private List<List<String>> getProperties(String key, Collection<String> items) {
        List<List<String>> properties= new ArrayList<>();
        for(String item : items){
            String[] i = item.split("_");
            i[1] = i[1].replace("-"," ");
            String[] property = new String[4];
            System.arraycopy(i,0,property,0,i.length);
            property[3] = key;
            properties.add(Arrays.asList(property));
        }
        return properties;
    }

}
