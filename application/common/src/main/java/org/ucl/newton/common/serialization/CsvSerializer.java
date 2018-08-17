/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.serialization;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.file.FileUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializes data into comma separate value format.
 *
 * @author Xiaolong Chen
 */
public class CsvSerializer
{
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private CsvSerializer() {
        throw new UnsupportedOperationException();
    }

    public static void writeCSV(OutputStream output, List<List<String>> listOfRecords) throws IOException {
        if (output != null) {
            CsvWriter csvWriter = new CsvWriter(output, ',', StandardCharsets.UTF_8);
            for (List<String> record : listOfRecords) {
                csvWriter.writeRecord(record.toArray(new String[record.size()]));
            }
            csvWriter.close();
        }
    }

    public static List<String[]> readCSV(String path) {
        List<String[]> list = new ArrayList<>();
        try {
            CsvReader reader = new CsvReader(path,',');
            reader.readHeaders();
            while(reader.readRecord()) {
                list.add(reader.getValues());
            }
            reader.close();
        }
        catch (Exception e){
            logger.error("Failed to read file", e);
        }
        return list;
    }
}
