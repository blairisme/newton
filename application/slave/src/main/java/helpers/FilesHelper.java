package helpers;

public class FilesHelper {


    public static String getFileUrl(String path){
        return Constants.SERVER_URL
                .concat("/download/?filename=")
                .concat(path);

    }
}
