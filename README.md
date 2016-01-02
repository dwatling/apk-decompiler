This project makes it easy to convert an installed Android app that was built with the Android SDK into readable source files.

How to use:
* Ensure you have ADB installed
* Attach your Android device
* Run dist/decompile.bat <package or keyword>

That's it! The output will be a zip file of the passed in package or keyword that contains as much of the decompiled code as possible.

NOTE: There will be no .java files, but .class files will be generated. I'd recommend using something like JD-Gui (https://github.com/java-decompiler/jd-gui/releases) to convert it into Java code.

Some of the dependent projects are not able to parse all APKs, so it isn't guaranteed to be a complete decompilation. If you see any problems during the run of APK Decompiler, I would encourage you to file a bug report with the appropriate project author!

APK Decompiler wraps the following projects:
* dex2jar (https://bitbucket.org/pxb1988/dex2jar) - Generates Java .class files
* apktool (https://ibotpeaches.github.io/Apktool/) - Generates readable resource files
