package org.ucl.newton.ui;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ucl.newton.application.system.ApplicationStorage;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.Charset;
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
    ApplicationStorage storage;
    @Inject
    public PluginController(ApplicationStorage applicationStorage){
        this.storage = applicationStorage;
    }

    @RequestMapping(value = "/FizzyoData", method = RequestMethod.GET)
    public String getData(ModelMap model){
        Path path = Paths.get(storage.getRootPath());
        path = path.resolve("Fizzyo").resolve("authCode");
        String authCode = readFile(path.toFile());

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
        List<String[]> properties = new ArrayList<>();
        try {
            CsvReader reader = new CsvReader(path.toString(),',');
            reader.readHeaders();
            while(reader.readRecord()) {
                properties.add(reader.getValues());
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("properties",properties);

        return "plugin/weatherSetting";
    }

    @RequestMapping(value = "/weatherSetting", method = RequestMethod.POST)
    public String set(
        @RequestParam String key,
        @RequestParam(required = false) Collection<String> items,
        ModelMap model)
    {
        List<String[]> properties = new ArrayList<>();
        properties.add(getHeader());
        properties.addAll(getProperties(key,items));
        try {
            OutputStream output = storage.getOutputStream("weather","setting");
            CsvWriter csvWriter = new CsvWriter(output, ',', Charset.forName("utf-8"));
            for (String[] property : properties) {
                csvWriter.writeRecord(property);
            }
            csvWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/weatherSetting";
    }

    private String readFile(File file){
        String ret = null;
        if(!file.exists())
            return ret;
        Long len = file.length();
        byte[] content = new byte[len.intValue()];
        try {
            InputStream input = new FileInputStream(file);
            input.read(content);
            input.close();
            ret = new String(content);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    private String[] getHeader() {
        String[] header = new String[4];
        header[0] = "city";
        header[1] = "country";
        header[2] = "date";
        header[3] = "key";
        return header;
    }

    private List<String[]> getProperties(String key, Collection<String> items) {
        List<String[]> properties= new ArrayList<>();
        for(String item : items){
            String[] i = item.split("_");
            i[1] = i[1].replace("-"," ");
            String[] property = new String[4];
            System.arraycopy(i,0,property,0,i.length);
            property[3] = key;
            properties.add(property);
        }
        return properties;
    }

}
