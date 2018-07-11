package engine;

import datasets.Dataset;
import exceptions.DatasetDownloadException;
import exceptions.MatchOutputFilesException;
import helpers.GitHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.ucl.newton.common.archive.ZipUtils;
import pojo.AnalysisResults;
import security.SandboxSecurityPolicy;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.Policy;
import java.util.List;

import static helpers.Constants.REPO_PATH;

public class Executor{


	protected String mRepoUrl;
	protected File mMainFile;
	protected String mMainFilename;
	protected List<Dataset> mDatasets;
	protected String mId;
	protected String mOutputPattern;
	protected String mMainRepoDir;
	protected File mZipOutputFile;
	protected File mLogFile;
	protected File mProjectDir;

	protected IEngine mEngine;

	
	public Executor(String id, IEngine engine, String repoUrl, String mainFilename, String outputPattern, List<Dataset> datasets){
		mId = id;
		mEngine = engine;
		mRepoUrl = repoUrl;
		mMainFilename = mainFilename;
		mDatasets = datasets;
		mOutputPattern = outputPattern;
	}

	/**
	 * Downloads code repo, datasets, build and run the code, and zip the resulting files
	 * @return analysis results files
	 * @throws IOException
	 */

	public AnalysisResults run() throws Exception {
		mProjectDir = new File(REPO_PATH, mId);
		if(mProjectDir.exists()) {
            FileUtils.deleteQuietly(mProjectDir);
		}
        mProjectDir.mkdirs();

		mMainRepoDir = GitHelper.downloadRepo(mProjectDir, mRepoUrl);
		setPluginPolicy(mMainRepoDir);
		mMainFile = new File(mMainRepoDir, mMainFilename);

		downloadDatasets();
		mLogFile = mEngine.build(mMainRepoDir, mMainFile.getAbsolutePath());
		File[] outputFiles = getOutputFiles();
		mZipOutputFile = new File(mProjectDir, "data.zip");
		if(mZipOutputFile.exists()){
			mZipOutputFile.delete();
		}
		ZipUtils.zip(outputFiles, mZipOutputFile);


		File[] visualFiles = getVisualFiles();
        File visualFilesArchive = new File(mProjectDir, "visuals.zip");
        if(visualFilesArchive.exists()){
            visualFilesArchive.delete();
        }
        ZipUtils.zip(visualFiles, visualFilesArchive);

        if (mLogFile != null) {
            File logFile = new File(mProjectDir, "log.txt");
            if (logFile.exists()) {
                logFile.delete();
            }
            FileUtils.moveFile(mLogFile, logFile);
        }

		AnalysisResults analysisResults = new AnalysisResults(mId);
		analysisResults.setLogFile(mLogFile);
		analysisResults.setZipOutputFile(mZipOutputFile);
		return analysisResults;
	}

	public void setPluginPolicy(String path){
		((SandboxSecurityPolicy)Policy.getPolicy()).setDirectory(path);
	}


	public File[] getOutputFiles() throws MatchOutputFilesException {
		try {
			File dir = new File(mMainRepoDir);
			FileFilter fileFilter = new WildcardFileFilter(mOutputPattern);
			File[] files = dir.listFiles(fileFilter);
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
			}
			return files;
		}
		catch (Exception ignore){
			throw new MatchOutputFilesException("Error while trying to find output files");
		}
	}

	private File[] getVisualFiles() throws MatchOutputFilesException {
        try {
            File dir = new File(mMainRepoDir);
            FileFilter fileFilter = new WildcardFileFilter("*.png");
            File[] files = dir.listFiles(fileFilter);
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i]);
            }
            return files;
        }
        catch (Exception ignore){
            throw new MatchOutputFilesException("Error while trying to find visual files");
        }
    }

//	protected void executeCommand(String cmnd){
//		mLogFile = LogHelper.executeCmnd(cmnd, true, mProjectDir.getAbsolutePath());
//	}

	private void downloadDatasets() throws DatasetDownloadException {
		try {
			if (mDatasets != null) {
				for (Dataset dataset : mDatasets) {
					dataset.download();
				}
			}
		}catch (Exception e){
			throw new DatasetDownloadException("Error while downloading datasets");
		}
	}

}
