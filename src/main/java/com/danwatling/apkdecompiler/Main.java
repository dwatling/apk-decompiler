package com.danwatling.apkdecompiler;

import com.danwatling.apkdecompiler.steps.DecompileApk;

public class Main {
	public static void main(String[] args) {
		String filter = null;

		if (args.length == 0) {
			Logger.info("Usage: decompile <filter>");
		} else {
			filter = args[0];

			DecompileApk decompiler = new DecompileApk(filter);
			long start = System.currentTimeMillis();
			decompiler.run();

			long end = System.currentTimeMillis();

			Logger.info("Process took: " + (end - start) + " ms.");
		}
	}
}