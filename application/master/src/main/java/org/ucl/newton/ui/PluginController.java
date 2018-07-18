package org.ucl.newton.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ucl.WeatherDataProvider.FileUtils;
import org.ucl.WeatherDataProvider.weather.model.WeatherProperty;
import org.ucl.newton.application.system.ApplicationPreferences;
import org.ucl.newton.application.system.ApplicationStorage;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    @Inject
    public PluginController(){ }

    @RequestMapping(value = "/weatherSetting", method = RequestMethod.GET)
    public String list(ModelMap model) {
        ApplicationStorage storage = new ApplicationStorage(new ApplicationPreferences());
        Path path = Paths.get(storage.getRootPath());
        path = path.resolve("weather").resolve("setting");
        String jsonStr = FileUtils.readFile(path);
        if(jsonStr == null)
            return "plugin/weatherSetting";

        Gson gson = new Gson();
        Type type = new TypeToken<List<WeatherProperty>>(){}.getType();
        List<WeatherProperty> properties = gson.fromJson(jsonStr,type);
        model.addAttribute("properties",properties);

        return "plugin/weatherSetting";
    }

    @RequestMapping(value = "/weatherSetting", method = RequestMethod.POST)
    public String set(
        @RequestParam String key,
        @RequestParam(required = false) Collection<String> items,
        ModelMap model)
    {
        List<WeatherProperty> properties = getProperties(key,items);
        Gson gson = new Gson();
        String jsonProperties = gson.toJson(properties);

        ApplicationStorage storage = new ApplicationStorage(new ApplicationPreferences());
        try {
            OutputStream output = storage.getOutputStream("weather","setting");
            output.write(jsonProperties.getBytes("utf-8"));
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return "redirect:/weatherSetting";
    }

    private List<WeatherProperty> getProperties(String key, Collection<String> items) {
        List<WeatherProperty> properties= new ArrayList<>();
        for(String item : items){
            String[] i = item.split("_");
            WeatherProperty property = new WeatherProperty();
            property.setCity(i[0]);
            property.setCountry(i[1]);
            property.setDate(i[2]);
            property.setKey(key);
            properties.add(property);
        }
        return properties;
    }

    private List<String> toList(Collection<String> collection){
        List<String> list = new ArrayList<>();
        for(String item : collection){
            list.add(item);
        }
        return list;
    }


}
