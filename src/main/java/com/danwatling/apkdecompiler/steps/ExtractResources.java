package com.danwatling.apkdecompiler.steps;

import brut.androlib.ApkDecoder;
import brut.androlib.ApkOptions;
import brut.androlib.ApktoolProperties;
import com.danwatling.apkdecompiler.Logger;

import java.io.File;

/**
 * Runs ApkTool
 */
public class ExtractResources extends BaseStep {
	private File workFolder = null;
	private File apkFile = null;

	public ExtractResources(File workFolder, File apkFile) {
		this.workFolder = workFolder;
		this.apkFile = apkFile;
	}
	public boolean run() {
		boolean result = true;

		extractResources();

		return result;
	}

	private void extractResources() {
		ApkDecoder decoder = new ApkDecoder();

		Logger.info("Extracting resources.");
		try {
			decoder.setOutDir(new File(workFolder.getAbsolutePath() + File.separator + "apktool"));
			decoder.setApkFile(this.apkFile);
			decoder.setForceDelete(true);
			decoder.setDecodeSources((short)0);

			decoder.decode();
		} catch (Exception ex) {
			Logger.error("Unable to extract resources", ex);
		}
	}
}
