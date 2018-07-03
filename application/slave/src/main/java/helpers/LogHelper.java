package helpers;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class LogHelper {


    public static final String LOG_FILENAME = "log.txt";
	
	public static File executeCmnd(String cmnd, boolean writeToFile, String directory){
		
		try {
			String temp, standardLog = "", errorLog = "", output = "";
	        Process p = Runtime.getRuntime().exec(cmnd);
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            output = output.concat("Standard output of the command:\n");
            while ((temp = stdInput.readLine()) != null) {
                standardLog = standardLog.concat(temp).concat("\n");
            }
            
            output = output.concat(standardLog).concat("Standard error of the command (if any):\n");
            while ((temp = stdError.readLine()) != null) {
                errorLog = errorLog.concat(temp).concat("\n");
            }
            output = output.concat(errorLog);
            if(writeToFile){
            	return writeToFile(output, directory);
            }
            return null;
      //      return new String[]{standardLog, errorLog};
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

/*	public static String getOutputLog(String projectId){
	    String output;
        try {
            output = FileUtils.readFileToString(new File(new File(REPO_PATH, projectId), LOG_FILENAME));
        } catch (Exception e) {
            output = "error reading output file";
        }
        return output;

    }*/

	private static File writeToFile(String output, String directory) throws IOException {
		System.out.println(output);
		File logFile = new File(directory, LOG_FILENAME);
		if(!logFile.exists()){
		    logFile.createNewFile();
        }
        FileUtils.writeStringToFile(logFile, output);
		return logFile;
		
	}

/*	public static String getLogFileUrl2(String projectId){
        return Constants.SERVER_URL
                .concat("/download/?filename=")
                .concat(REPO_PATH)
                .concat("/")
                .concat(projectId)
                .concat("/")
                .concat(LOG_FILENAME);
    }*/




}
