package helpers;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class FilesHelper {


    public static String getFileUrl(File file) {

        return Constants.SERVER_URL
                .concat("/download/?filename=")
                .concat(FilenameUtils.separatorsToUnix(file.getAbsolutePath()));

    }
}
