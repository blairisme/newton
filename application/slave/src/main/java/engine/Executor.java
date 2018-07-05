package engine;

import datasets.Dataset;
import exceptions.DatasetDownloadException;
import exceptions.MatchOutputFilesException;
import helpers.Constants;
import helpers.GitHelper;
import helpers.LogHelper;
import helpers.ZipHelper;
import org.apache.commons.io.filefilter.WildcardFileFilter;
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
	protected File mProjectFile;

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
		mProjectFile = new File(REPO_PATH, mId);
		if(!mProjectFile.exists()) {
			mProjectFile.mkdir();
		}
		mMainRepoDir = GitHelper.downloadRepo(mProjectFile, mRepoUrl);
		setPluginPolicy(mMainRepoDir);
		mMainFile = new File(mMainRepoDir, mMainFilename);

		downloadDatasets();
		mLogFile = mEngine.build(mMainRepoDir, mMainFile.getAbsolutePath());
		File[] outputFiles = getOutputFiles();
		mZipOutputFile = new File(mMainRepoDir, "output_"+mId+".zip");
		if(mZipOutputFile.exists()){
			mZipOutputFile.delete();
		}
		ZipHelper.zipFiles(outputFiles, mZipOutputFile);

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
		}catch (Exception ignore){
			throw new MatchOutputFilesException("Error while trying to find output files");
		}
	}

//	protected void executeCommand(String cmnd){
//		mLogFile = LogHelper.executeCmnd(cmnd, true, mProjectFile.getAbsolutePath());
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
