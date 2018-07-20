package helpers;

import exceptions.RepoDownloadException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class GitHelperTest {


    @Test
    public void testRepoDownload(){

        String repoUrl = "https://github.com/ziad-alhalabi/python-test/archive/master.zip";
        File tempDir = new File("GitHelperTest");
        if(tempDir.exists()){
            tempDir.delete();
        }
        tempDir.mkdir();
        try {
            String filePath = GitHelper.downloadRepo(tempDir, repoUrl);
            String mainFilename = "python-test-master";
            File mainFile = new File(filePath, mainFilename);
            Assert.assertEquals(mainFile.getName(), mainFilename);
        } catch (RepoDownloadException e) {
            e.printStackTrace();
        } finally {
            try {
                FileUtils.deleteDirectory(tempDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
