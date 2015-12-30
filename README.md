This project makes it easy to convert an Android app into readable source files.

How to use:
	- Ensure you have the ADB installed and running
	- Attach your Android device
	- Run dist/decompile.bat <package or keyword>

That's it! The output will be a zip file of the passed in package or keyword that contains as much of the decompiled code as possible.

Some of the projects APK Decompiler depends on are not able to parse all APKs, so it isn't guaranteed to be a complete decompilation. If you see any problems during the run of APK Decompiler, I would encourage you to file a bug report with the appropriate project author!

APK Decompiler wraps the following projects:
	- dex2jar (https://bitbucket.org/pxb1988/dex2jar)
	- apktool (https://ibotpeaches.github.io/Apktool/)
	- Procyon (https://bitbucket.org/mstrobel/procyon/)
