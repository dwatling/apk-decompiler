package com.danwatling.apkdecompiler.adb;

import com.danwatling.apkdecompiler.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdbShell extends CustomStreamReader {
	String serialNumber;

	public AdbShell(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	private String exec(String... shellCommand) {
		List<String> commands = new ArrayList<>(Arrays.asList("adb","-s",serialNumber,"shell"));
		commands.addAll(Arrays.asList(shellCommand));
		String result = null;

		try {
			System.out.println("Running: " + commands.toString());
			Process process = new ProcessBuilder().command(commands).redirectErrorStream(true).start();
			result = getProcessOutput(process.getInputStream());
		} catch (IOException ex) {
			Logger.error("Unable to run '" + commands + "'", ex);
		}

		return result;
	}

	public List<AndroidPackage> listPackages(String filter) {
		List<AndroidPackage> result = new ArrayList<>();

		String output = exec("pm", "list", "packages", "-f", filter);
		System.out.println(output);

		BufferedReader reader = new BufferedReader(new StringReader(output));

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 0) {
					AndroidPackage pkg = new AndroidPackage(line);
					if (pkg.getPath() != null) {
						result.add(pkg);
					}
				}
			}
		} catch (IOException ex) {
			Logger.error("Unable to process output of 'pm list packages'", ex);
		}

		return result;
	}
}
