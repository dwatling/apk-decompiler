package com.danwatling.apkdecompiler.steps;

import com.danwatling.apkdecompiler.Logger;
import com.danwatling.apkdecompiler.adb.Adb;
import com.danwatling.apkdecompiler.adb.AdbShell;
import com.danwatling.apkdecompiler.adb.AndroidPackage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Pulls Apk from device
 */
public class FetchApk extends BaseStep {
	private File workFolder = null;
	private String filter = null;
	private File apkFile = null;
	private String deviceSerial = null;
	private Adb adb = null;
	private AdbShell adbShell = null;

	public FetchApk(String deviceSerial, File workFolder, String filter, File apkFile) {
		this.workFolder = workFolder;
		this.filter = filter;
		this.apkFile = apkFile;
		this.deviceSerial = deviceSerial;

		adb = new Adb();
		adbShell = adb.shell(deviceSerial);
	}

	public boolean run() {
		boolean result = false;

		List<AndroidPackage> packages = adbShell.listPackages(this.filter);
		if (packages.size() > 0) {
			if (packages.size() > 1) {
				Logger.info("Found " + packages.size() + " applications that match: " + this.filter);
				for (AndroidPackage p : packages) {
					Logger.info("\t" + p.getPath() + " - " + p.getPackage());
				}
			}

			adb.pull(deviceSerial, packages.get(0), this.apkFile);
			result = true;
		} else {
			Logger.info("No applications matched '" + this.filter + "'");
		}

		return result;
	}
}
