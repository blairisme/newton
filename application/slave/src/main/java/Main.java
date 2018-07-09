import com.sun.jndi.toolkit.url.Uri;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.MalformedURLException;


public class Main {
	
	public static void main(String args[]) {

	    File f = new File("projects/log.txt");
        try {
            String s = FilenameUtils.separatorsToUnix(f.getAbsolutePath());
            Uri uri = new Uri(f.getAbsolutePath());
            System.out.println(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

/*
        try {

            String repoUrl = "https://github.com/ziad-halabi9/AlgorithmicMusicPlayer/archive/master.zip";
            String mainFilename = "test.py";
            List<Dataset> datasets = new ArrayList<>();

            PythonEngine engine = new PythonEngine("120",repoUrl, mainFilename, "", datasets);
        //	engine.run();

            File f1 = new File("projects", "f1.txt");
            File f2 = new File("projects", "f2.txt");
            File f3 = new File("projects2", "f3.txt");
            File[] files =  {f1, f2, f3};
          //  zipFiles(files);

        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
