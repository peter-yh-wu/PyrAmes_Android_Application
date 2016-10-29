package com.example.peterwu.pyrames_android_application;

import java.io.File;

/**
 * Created by peterwu on 9/20/16.
 */

//https://www.tutorialspoint.com/java/java_multithreading.htm

public class ParseRunnable implements Runnable {
    private Thread t;

    File readFile;
    File writeFile;

    Thread cThread;

    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        cThread = Thread.currentThread();


    }

    public void start() {
        t = new Thread(this);
        t.start();
    }
}
