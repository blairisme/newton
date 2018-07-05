package helpers;

import exceptions.RepoDownloadException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GitHelper {
	

	public static String downloadRepo(File fileDir, String url) throws RepoDownloadException {

		try {
			File repoZip = new File(fileDir, "repo.zip");
			if (fileDir.exists()) {
				fileDir.delete();
			}
			URL repoUrl = new URL(url);
			FileUtils.copyURLToFile(repoUrl, repoZip);
			ZipHelper.unzip(repoZip.getAbsolutePath(), fileDir.getAbsolutePath());

			File[] files = fileDir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					return file.getAbsolutePath();
				}
			}
		}catch (Exception exception){
			throw new RepoDownloadException("Error while downloading Github repository");
		}

		return null;
	}

}
