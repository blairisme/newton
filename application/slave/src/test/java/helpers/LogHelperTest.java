package helpers;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;

import static helpers.LogHelper.LOG_FILENAME;

public class LogHelperTest {


    @Test
    public void testCommandExecution(){
        String folderName = "testingdummy123";
        File f = new File(folderName);
        if(f.exists()){
            f.delete();
        }
        f.mkdir();
        File f2 = new File(folderName, "testingdummy456");
        f2.mkdir();

        String filename = "temp.txt";
        File f3 = new File(f2.getAbsolutePath(), filename);
        try {
            f3.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cmnd = "ls";
        LogHelper.startProcess(cmnd, f2.getAbsolutePath(), true, folderName);

        File logFile = new File(folderName, LOG_FILENAME);
        try {
            String logContent = FileUtils.readFileToString(logFile);
            logContent = logContent.replace("\n", "");
            Assert.assertEquals(logContent, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.deleteDirectory(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
