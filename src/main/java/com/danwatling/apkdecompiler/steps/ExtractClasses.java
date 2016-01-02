package com.danwatling.apkdecompiler.steps;

import com.danwatling.apkdecompiler.Logger;
import com.googlecode.dex2jar.tools.Dex2jarCmd;
import net.lingala.zip4j.exception.ZipException;
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
		File classesJarFile = runDex2Jar(classesDexFile);
		extractJarFile(classesJarFile);
		cleanup(classesDexFile, classesJarFile);

		return result;
	}

	private void cleanup(File... filesToDelete) {
		for (File f : filesToDelete) {
			f.delete();
		}
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
			result = new File(this.workFolder.getAbsolutePath() + File.separator + "classes.dex");
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

	private File runDex2Jar(File classesDexFile) {
		File result = null;
		Logger.info("Running dex2jar");

		result = new File(this.workFolder.getAbsolutePath() + File.separator + "classes.jar");
		String[] args = {"--force", "-o", result.getAbsolutePath(), classesDexFile.getAbsolutePath()};
		Dex2jarCmd.main(args);

		return result;
	}

	private void extractJarFile(File classesJarFile) {
		Logger.info("Extracting classes");
		try {
			net.lingala.zip4j.core.ZipFile zf = new net.lingala.zip4j.core.ZipFile(classesJarFile);
			zf.extractAll(this.workFolder.getAbsolutePath() + File.separator + "classes");
		} catch (ZipException ex) {
			Logger.error("Unable to extract classes.", ex);
		}
	}
}
