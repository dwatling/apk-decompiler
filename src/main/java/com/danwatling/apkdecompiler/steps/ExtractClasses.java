package com.danwatling.apkdecompiler.steps;

import com.danwatling.apkdecompiler.Logger;
import com.googlecode.dex2jar.tools.Dex2jarCmd;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Runs dex2jar
 */
public class ExtractClasses extends BaseStep {
	private File workFolder = null;
	private File apkFile = null;

	public ExtractClasses(File workFolder, File apkFile) {
		this.workFolder = workFolder;
		this.apkFile = apkFile;
	}

	public boolean run() {
		boolean result = true;

		File classesDexFile = extractClassesDex();
		runDex2Jar(classesDexFile);
		cleanup(classesDexFile);

		return result;
	}

	private File extractClassesDex() {
		ZipFile apkFile = null;
		InputStream entryStream = null;
		FileOutputStream outputStream = null;
		File result = null;
		try {
			Logger.info("Extracting classes.dex");
			apkFile = new ZipFile(this.apkFile);
			ZipEntry classesDex = apkFile.getEntry("classes.dex");

			entryStream = apkFile.getInputStream(classesDex);
			result = new File(this.workFolder.getCanonicalPath() + File.separator + "classes.dex");
			outputStream = new FileOutputStream(result);
			IOUtils.copy(entryStream, outputStream);
		} catch (IOException ex) {
			Logger.error("Unable to extract classes.dex from " + this.apkFile.getAbsolutePath(), ex);
		} finally {
			IOUtils.closeQuietly(entryStream);
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(apkFile);
		}

		return result;
	}

	private void cleanup(File classesDexFile) {
		classesDexFile.delete();
	}

	private void runDex2Jar(File classesDexFile) {
		Logger.info("Running dex2jar");

		try {
			String[] args = {"--force", "-o", this.workFolder.getCanonicalPath() + File.separator + "classes.jar", classesDexFile.getCanonicalPath()};
			Dex2jarCmd.main(args);
		} catch (Throwable t) {
			Logger.error("Unable to convert classes.dex into Java class files.", t);
		}
	}
}
