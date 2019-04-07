package com.danwatling.apkdecompiler.adb;

import com.danwatling.apkdecompiler.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Java wrapper for Adb process.
 */
public class Adb extends CustomStreamReader {

	public Adb() {
	}

	public AdbShell shell(String serialNumber) {
		return new AdbShell(serialNumber);
	}

	public List<String> getDevices() {
		List<String> result = new ArrayList<>();
		try {
			Process process = new ProcessBuilder().command(Arrays.asList("adb", "devices")).redirectErrorStream(true).start();
			String output = getProcessOutput(process.getInputStream());
			System.out.println(output);

			String[] lines = output.split("\n");
			for (String line : lines) {
				String[] tokens = line.split("\t");
				if (tokens.length == 2) {
					System.out.println("Found device: " + tokens[0]);
					result.add(tokens[0]);
				}
			}
		} catch (IOException ex) {
			Logger.error("Unable to fetch device list", ex);
		}
		return result;
	}

	public boolean pull(String deviceSerial, AndroidPackage androidPackage, File destOnDisk) {
		boolean result = false;
		try {
			Logger.info("Downloading " + androidPackage.getPath());
			String command = "adb -s " + deviceSerial + " pull -p -a " + androidPackage.getPath();
			Process p = Runtime.getRuntime().exec(command);
			if (p.isAlive()) {
				getProcessOutput(p.getInputStream(), true);


				File localApk = new File(new File(".").getCanonicalPath() + File.separator + androidPackage.getFilename());
				destOnDisk.getParentFile().mkdirs();
				localApk.renameTo(destOnDisk);

				result = true;
			}
		} catch (IOException ex) {
			Logger.error("Unable to pull '" + androidPackage.getPath() + "'", ex);
		}

		return result;
	}
}
