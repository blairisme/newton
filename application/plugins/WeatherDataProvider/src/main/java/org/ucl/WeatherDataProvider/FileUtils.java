package org.ucl.WeatherDataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */
public class FileUtils {
    public static String readFile(Path path){
        File file = path.toFile();
        String ret = null;
        if(!file.exists())
            return ret;
        try {
            Long len = file.length();
            byte[] content = new byte[len.intValue()];
            InputStream input = new FileInputStream(file);
            input.read(content);
            input.close();
            ret = new String(content);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
