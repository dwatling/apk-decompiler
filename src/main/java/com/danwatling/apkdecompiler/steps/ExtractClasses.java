package com.danwatling.apkdecompiler.steps;

/**
 * Runs dex2jar
 */
public class ExtractClasses extends BaseStep {
	private String apkFilename = null;

	public ExtractClasses(String apkFilename) {
		this.apkFilename = apkFilename;
	}

	public boolean run() {
		boolean result = true;

		return result;
	}

	public void cleanup() {

	}
}
