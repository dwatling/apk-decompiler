package com.danwatling.apkdecompiler.steps;

import java.util.List;

/**
 * Zips resulting files up
 */
public class DecompileApk extends BaseStep {
	List<BaseStep> steps;

	public DecompileApk(String keyword) {
		String apkFilename = keyword + ".apk";

		steps.add(new FetchApk(keyword, apkFilename));
		steps.add(new ExtractClasses(apkFilename));
		steps.add(new DecompileClasses());
		steps.add(new ExtractResources(apkFilename));
		steps.add(new GenerateFinalZip());
	}

	public boolean run() {
		boolean result = true;

		for (BaseStep step : steps) {
			if (!step.run()) {
				result = false;
				break;
			}
		}

		return result;
	}

	public void cleanup() {

	}
}
