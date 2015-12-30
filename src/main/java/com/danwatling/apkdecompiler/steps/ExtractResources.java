package com.danwatling.apkdecompiler.steps;

/**
 * Runs ApkTool
 */
public class ExtractResources extends BaseStep {
	private String apkFilename = null;

	public ExtractResources(String apkFilename) {
		this.apkFilename = apkFilename;
	}
	public boolean run() {
		boolean result = true;

		return result;
	}

	public void cleanup() {

	}
}
