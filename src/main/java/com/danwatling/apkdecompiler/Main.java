package com.danwatling.apkdecompiler;

import com.danwatling.apkdecompiler.steps.DecompileApk;

public class Main {
	public static void main(String[] args) {
		String filter = "netflix";

		if (args.length >= 1) {
			filter = args[0];
		}

		DecompileApk decompiler = new DecompileApk(filter);
		long start = System.currentTimeMillis();
		boolean result = decompiler.run();

		long end = System.currentTimeMillis();

		System.out.println("Process took: " + (end - start) + " ms.");
		if (!result) {
			System.err.println("Something bad happened!");
		}
	}
}