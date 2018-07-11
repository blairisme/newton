package helpers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class LogHelper {


    public static final String LOG_FILENAME = "log.txt";

    public static File startProcess(String cmnd, String mainFilePath, boolean writeToFile, String directory){

        File log = new File(directory, "log.txt");
        try {
            log.delete();
            log.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            ProcessBuilder pb =
                    new ProcessBuilder( cmnd, mainFilePath);

            pb.directory(new File(directory));
            File f = pb.directory();
            System.out.println(f.exists());

            if(writeToFile) {
                pb.redirectErrorStream(true);
                pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
            }
            Process p = pb.start();
            return log;
        }catch (Exception e){
            e.printStackTrace();
        }
        return log;
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
