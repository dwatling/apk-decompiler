package com.danwatling.apkdecompiler.steps;

import com.danwatling.apkdecompiler.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;

/**
 * Zips resulting files up
 */
public class GenerateFinalZip extends BaseStep {
	private File workFolder = null;

	public GenerateFinalZip(File workFolder) {
		this.workFolder = workFolder;
	}

	public boolean run() {
		boolean result = true;

		generateZipFile();
		removeWorkFolder();

		return result;
	}

	private void generateZipFile() {
		try {
			File outputFile = new File(this.workFolder.getName() + ".zip");
			if (outputFile.exists()) {
				Logger.info(outputFile.getAbsolutePath() + " already exists. Deleting.");
				outputFile.delete();
			}
			ZipFile output = new net.lingala.zip4j.core.ZipFile(outputFile);
			Logger.info("Generating " + output.getFile().getAbsolutePath());

			ZipParameters params = new ZipParameters();
			params.setIncludeRootFolder(false);
			params.setRootFolderInZip("");
			output.addFolder(this.workFolder.getAbsolutePath() + File.separator + "apktool", params);
			params.setRootFolderInZip("classes");
			output.addFolder(this.workFolder.getAbsolutePath() + File.separator + "classes", params);
		} catch (Throwable t) {
			Logger.error("Unable to generate final zip file.", t);
		}
	}

	private void removeWorkFolder() {
		if (!workFolder.delete()) {
			Logger.info("Unable to remove " + workFolder.getAbsolutePath());
		}
	}
}
