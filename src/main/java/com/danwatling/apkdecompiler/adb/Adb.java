package com.danwatling.apkdecompiler.adb;

import com.danwatling.apkdecompiler.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Java wrapper for Adb process.
 */
public class Adb {
	Process process;

	public Adb() {
		try {
			process = Runtime.getRuntime().exec("adb shell");
			getProcessOutput(process.getInputStream());
		} catch (IOException ex) {
			Logger.error("Unable to run 'adb shell'", ex);
		}
	}

	private String getProcessOutput(InputStream stream) {
		return getProcessOutput(stream, false);
	}
	private String getProcessOutput(InputStream stream, boolean printWhileRunning) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		try {
			byte[] bytes = new byte[8192];
			boolean done = false;
			while (!done) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException ex) {
					// ignore
				}

				int count = stream.read(bytes, 0, bytes.length);
				if (count > 0) {
					result.write(bytes, 0, count);
				}

				if (printWhileRunning) {
					Logger.info(result.toString());
				}

				// We consider the process done when we see the command prompt again
				// TODO - Ensure the prompt doesn't change!
				if (result.toString().contains("shell@android:/ $") || result.toString().contains("100%")) {
					done = true;
				}
			}
		} catch (IOException ex) {
			Logger.error("Unable to read line from adb!", ex);
		}

		return result.toString();
	}

	private void exec(String command) {
		if (!process.isAlive()) {
			Logger.error("adb process is not alive!");
		}

		try {
			process.getOutputStream().write(command.getBytes());
			process.getOutputStream().write(System.lineSeparator().getBytes());
			process.getOutputStream().flush();
		} catch (IOException ex) {
			Logger.error("Unable to run '" + command + "'", ex);
		}
	}

	public List<AndroidPackage> listPackages(String filter) {
		exec("pm list packages -3 -f " + filter);
		String output = getProcessOutput(process.getInputStream());
		BufferedReader reader = new BufferedReader(new StringReader(output));
		Logger.info(output);

		List<AndroidPackage> result = new ArrayList<>();
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

	public boolean pull(AndroidPackage androidPackage, String destOnDisk) {
		boolean result = false;
		try {
			Logger.info("Downloading " + androidPackage.getPath());
			Process p = Runtime.getRuntime().exec("adb pull -p -a " + androidPackage.getPath());
			if (p.isAlive()) {
				getProcessOutput(p.getErrorStream(), true);


				File localApk = new File(new File(".").getCanonicalPath() + File.separator + androidPackage.getFilename());
				localApk.renameTo(new File(new File(".").getCanonicalPath() + File.separator + destOnDisk));

				result = true;
			}
		} catch (IOException ex) {
			Logger.error("Unable to pull '" + androidPackage.getPath() + "'", ex);
		}

		return result;
	}

	public void exit() {
		exec("exit");
	}
}
