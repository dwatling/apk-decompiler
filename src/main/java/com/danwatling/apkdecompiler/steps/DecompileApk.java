package com.danwatling.apkdecompiler.steps;

import com.danwatling.apkdecompiler.adb.Adb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Zips resulting files up
 */
public class DecompileApk extends BaseStep {
	List<List<BaseStep>> devicesSteps = new ArrayList<>();

	public DecompileApk(String filter) {

		List<String> devices = new Adb().getDevices();
		for (String device : devices) {
			List<BaseStep> steps = new ArrayList<>();
			File workFolder = new File(device + "/" + filter);
			File apkFile = new File(workFolder.getAbsolutePath() + File.separator + filter + ".apk");

			steps.add(new FetchApk(device, workFolder, filter, apkFile));
			steps.add(new ExtractClasses(workFolder, apkFile));
//		steps.add(new DecompileClasses(workFolder));
			steps.add(new ExtractResources(workFolder, apkFile));
			steps.add(new GenerateFinalZip(workFolder));
			devicesSteps.add(steps);
		}
	}

	public boolean run() {
		boolean result = true;

		for (List<BaseStep> deviceSteps : devicesSteps) {
			for (BaseStep step : deviceSteps) {
				if (!step.run()) {
					result = false;
					break;
				}
			}
		}

		return result;
	}
}
