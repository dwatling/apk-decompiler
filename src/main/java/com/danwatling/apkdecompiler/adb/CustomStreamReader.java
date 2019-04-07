package com.danwatling.apkdecompiler.adb;

import com.danwatling.apkdecompiler.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class CustomStreamReader {
    protected String getProcessOutput(InputStream stream) {
        return getProcessOutput(stream, true);
    }
    protected String getProcessOutput(InputStream stream, boolean printWhileRunning) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try {
            byte[] bytes = new byte[8192];
            boolean done = false;
            int retries = 3;
            while (!done) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    // ignore
                }

                int count = stream.read(bytes, 0, bytes.length);
                if (count > 0) {
                    retries = 3;
                    result.write(bytes, 0, count);
                } else {
                    // If the buffer shows nothing is available to read, try it a few times before
                    //  assuming the command is done executing
                    retries --;
                    if (retries == 0) {
                        done = true;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.error("Unable to read line from adb!", ex);
        }

        return result.toString();
    }
}
