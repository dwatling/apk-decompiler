package com.danwatling.apkdecompiler.steps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Zips resulting files up
 */
public class DecompileApk extends BaseStep {
	List<BaseStep> steps;

	public DecompileApk(String filter) {
		File workFolder = new File(filter);
		File apkFile = new File(workFolder.getAbsolutePath() + File.separator + filter + ".apk");

		steps = new ArrayList<>();
		steps.add(new FetchApk(workFolder, filter, apkFile));
		steps.add(new ExtractClasses(workFolder, apkFile));
//		steps.add(new DecompileClasses(workFolder));
		steps.add(new ExtractResources(workFolder, apkFile));
		steps.add(new GenerateFinalZip(workFolder));
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
