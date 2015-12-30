package com.danwatling.apkdecompiler.steps;

/**
 * Pulls Apk from device
 */
public class FetchApk extends BaseStep {
	private String keyword = null;
	private String apkFile = null;

	public FetchApk(String keyword, String apkFile) {
		this.keyword = keyword;
		this.apkFile = apkFile;
	}

	public boolean run() {
		boolean result = true;

		// TODO - adb shell
		// TODO - pm list packages -f <keyword>
		//			package:/data/app/com.sagosago.RoadTrip.freetime-1.apk=com.sagosago.RoadTrip.freetime
		// TODO - adb pull -p -a <file>
		// TODO - Rename <file> to <outputApk>

		return result;
	}

	public void cleanup() {

	}
}
