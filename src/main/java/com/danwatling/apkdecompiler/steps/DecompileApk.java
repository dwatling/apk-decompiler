package com.danwatling.apkdecompiler.steps;

import java.util.ArrayList;
import java.util.List;

/**
 * Zips resulting files up
 */
public class DecompileApk extends BaseStep {
	List<BaseStep> steps;

	public DecompileApk(String filter) {
		String apkFilename = filter + ".apk";

		steps = new ArrayList<>();
		steps.add(new FetchApk(filter, apkFilename));
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
}
