package com.danwatling.apkdecompiler.steps;

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

		return result;
	}
}
