package com.danwatling.apkdecompiler;

import com.danwatling.apkdecompiler.steps.DecompileApk;

public class Main {
	public static void main(String[] args) {
		DecompileApk decompiler = new DecompileApk(args[0]);
		long start = System.currentTimeMillis();
		boolean result = decompiler.run();
		long end = System.currentTimeMillis();

		System.out.println("Process took: " + (end - start) + " ms.");
		if (!result) {
			System.err.println("Something bad happened!");
		}
	}
}