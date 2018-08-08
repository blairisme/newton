/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.Validate;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Utility functions for working with {@link File Files}.
 *
 * @author Blair Butterworth
 */
public class FileUtils
{
    private FileUtils() {
        throw new UnsupportedOperationException();
    }

    public static Collection<File> findChildren(File directory, String pattern) {
        return findChildren(directory, Arrays.asList(pattern));
    }

    public static Collection<File> findChildren(File directory, List<String> patterns) {
        FileFilter fileFilter = new WildcardFileFilter(patterns);
        File[] files = directory.listFiles(fileFilter);
        if (files != null) {
            return Arrays.asList(files);
        }
        return Collections.emptyList();
    }

    public static void createNew(File file) throws IOException {
        Validate.notNull(file);
        File parent = file.getParentFile();

        if (! parent.exists() && ! parent.mkdirs()) {
            throw new IOException("Unable to create containing directory");
        }
        if (! file.exists() && ! file.createNewFile()) {
            throw new IOException("Unable to create new file");
        }
    }

    public static String readFile(File file){
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
    public static void writeCSV(OutputStream output,List<List<String>> listOfRecords) throws IOException {
        if (output != null) {
            CsvWriter csvWriter = new CsvWriter(output, ',', Charset.forName("utf-8"));
            for (List<String> record : listOfRecords) {
                csvWriter.writeRecord(record.toArray(new String[record.size()]));
            }
            csvWriter.close();
        }
    }

    public static List<String[]> readCSV(String path){
        List<String[]> list = new ArrayList<>();
        try {
            CsvReader reader = new CsvReader(path,',');
            reader.readHeaders();
            while(reader.readRecord()) {
                list.add(reader.getValues());
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;

    }

    public static <T> void writeToFile(OutputStream output,T config,Class<T> type) {
        Gson gson = new Gson();
        String str = gson.toJson(config,type);
        try {
            output.write(str.getBytes("utf-8"));
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
