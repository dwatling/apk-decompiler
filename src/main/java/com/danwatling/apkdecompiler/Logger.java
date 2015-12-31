package com.danwatling.apkdecompiler;

import java.io.PrintStream;

public class Logger {
	private static void commonLog(PrintStream writer, String message, Throwable t) {
		writer.println(message);
		if (t != null) {
			t.printStackTrace(writer);
		}
	}
	public static void info(String message) {
		commonLog(System.out, message, null);
	}
	public static void error(String message) {
		commonLog(System.err, message, null);
	}
	public static void error(String message, Throwable t) {
		commonLog(System.err, message, t);
	}
}
