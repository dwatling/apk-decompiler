package com.danwatling.apkdecompiler.adb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidPackage {
	private String path;
	private String filename;
	private String appPackage;

	public AndroidPackage(String line) {
		Pattern pattern = Pattern.compile("package:(.[^=]*)=(.*)");
		Matcher matcher = pattern.matcher(line);
		if (matcher.matches()) {
			path = matcher.group(1);
			filename = path.substring(path.lastIndexOf("/")+1);
			appPackage = matcher.group(2);
		}
	}

	public String getPath() {
		return this.path;
	}

	public String getFilename() {
		return this.filename;
	}

	public String getPackage() {
		return this.appPackage;
	}
}
