package com.danwatling.apkdecompiler.steps;

import com.strobel.Procyon;

import java.io.File;

/**
 * Runs Procyon
 */
public class DecompileClasses extends BaseStep {
	private File workFolder = null;

	public DecompileClasses(File workFolder) {
		this.workFolder = workFolder;
	}

	public boolean run() {
		boolean result = true;

		return result;
	}
}
