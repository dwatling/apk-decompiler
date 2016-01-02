package com.danwatling.apkdecompiler.steps;

import com.danwatling.apkdecompiler.Logger;
import com.strobel.Procyon;
import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Runs Procyon.
 *
 * Unfortunately, Procyon does not do a good job decompiling .class files (problems: majority of resulting Java files are 0 bytes with no error output, excruciatingly slow processing time)
 *
 * Once Procyon is more mature I'll revisit this step.
 */
public class DecompileClasses extends BaseStep {
	private File workFolder = null;

	public DecompileClasses(File workFolder) {
		this.workFolder = workFolder;
	}

	public boolean run() {
		boolean result = true;

		File classesFolder = new File(this.workFolder + File.separator + "classes");
		File srcFolder = new File(this.workFolder + File.separator + "src");

		if (srcFolder.exists()) {
			Logger.info(srcFolder.getPath() + " already exists. Deleting.");
			if (!srcFolder.delete()) {
				Logger.info("\tFailed to delete " + srcFolder.getPath());
			}
		}

		List<File> classes = fetchAllClasses(classesFolder);
		decompileClasses(classes, srcFolder);

		return result;
	}

	private List<File> fetchAllClasses(File folder) {
		List<File> result = new ArrayList<>();
		File[] files = folder.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				result.addAll(fetchAllClasses(f));
			} else {
				if (f.getName().endsWith(".class")) {
					result.add(f);
				}
			}
		}

		return result;
	}

	private void decompileClasses(List<File> classes, File srcFolder) {
		Logger.info("Decompiling classes");

		srcFolder.mkdirs();

		for (File classFile : classes) {
			decompile(classFile, srcFolder);
		}
	}
	private void decompile(File classFile, File srcFolder) {
		try {
			File outputFile = convertClassFileToOutputFile(classFile, srcFolder);
			outputFile.getParentFile().mkdirs();
			PlainTextOutput output = new PlainTextOutput(new OutputStreamWriter(new FileOutputStream(outputFile.getAbsolutePath())));
			DecompilerSettings settings = DecompilerSettings.javaDefaults();
			settings.setForceExplicitImports(true);
			settings.setOutputFileHeaderText("Generated with Procyon v" + Procyon.version());

			Decompiler.decompile(classFile.getAbsolutePath(), output, settings);
		} catch (IOException ex) {
			Logger.error("Unable to decompile " + classFile.getAbsolutePath(), ex);
		}
	}

	private File convertClassFileToOutputFile(File classFile, File srcFolder) {
		String[] tokens = classFile.getPath().split(Pattern.quote(File.separator));
		List<String> paths = new ArrayList<>();
		paths.addAll(Arrays.asList(tokens));

		paths.remove(0);		// workFolder
		paths.remove(0);		// classes
		paths.remove(paths.size()-1);		// Class file name

		paths.add(classFile.getName().substring(0, classFile.getName().lastIndexOf(".")) + ".java");		// Re-add class file name with .java extension

		File result = new File(srcFolder.getPath() + File.separator + String.join(File.separator, paths));

		return result;
	}
}
