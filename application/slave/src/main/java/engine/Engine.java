package engine;

import datasets.Dataset;
import helpers.Constants;
import helpers.GitHelper;
import helpers.ZipHelper;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import pojo.AnalysisResults;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import static helpers.Constants.REPO_PATH;

public abstract class Engine implements IEngine {


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

	
	public Engine(String id, String repoUrl, String mainFilename, String outputPattern, List<Dataset> datasets){
		mId = id;
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

	public AnalysisResults run() throws IOException {
		mProjectFile = new File(REPO_PATH, mId);
		if(!mProjectFile.exists()) {
			mProjectFile.mkdir();
		}
		mMainRepoDir = GitHelper.downloadRepo(mProjectFile, mRepoUrl);
		mMainFile = new File(mMainRepoDir, mMainFilename);

		downloadDatasets();
		build();
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


	public File[] getOutputFiles(){
		File dir = new File(mMainRepoDir);
		FileFilter fileFilter = new WildcardFileFilter(mOutputPattern);
		File[] files = dir.listFiles(fileFilter);
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);
		}
		return files;
	}

	private void downloadDatasets(){
		if(mDatasets!=null) {
			for (Dataset dataset : mDatasets) {
				dataset.download();
			}
		}
	}

}
