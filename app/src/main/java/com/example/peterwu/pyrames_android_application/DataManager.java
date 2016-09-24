package com.example.peterwu.pyrames_android_application;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by peterwu on 9/22/16.
 */

public class DataManager
{
    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();

    static  {
        //...
        // Creates a single static instance of PhotoManager
        DataManager sInstance = new DataManager();
    }

    ThreadPoolExecutor mDecodeThreadPool;

    /**
     * Constructs the work queues and thread pools used to...
     * Because the constructor is marked private,
     * it's unavailable to other classes, even in the same package.
     */
    private DataManager() {

        // Defines a Handler object that's attached to the UI thread
        Handler mHandler = new Handler(Looper.getMainLooper()) {
            /*
             * handleMessage() defines the operations to perform when
             * the Handler receives a new Message to process.
             */
            @Override
            public void handleMessage(Message inputMessage) {
                //...
            }
            //...
        };

        // A queue of Runnables
        final BlockingQueue<Runnable> mDecodeWorkQueue;

        // Instantiates the queue of Runnables as a LinkedBlockingQueue
        mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();

        // Sets the amount of time an idle thread waits before terminating
        final int KEEP_ALIVE_TIME = 1;
        // Sets the Time Unit to seconds
        final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        // Creates a thread pool manager
        mDecodeThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDecodeWorkQueue);
    }

    // Called by the [main] to ...
    /*
    static public PhotoTask startDownload(
            PhotoView imageView,
            boolean cacheFlag) {
        ...
        // Adds a download task to the thread pool for execution
        sInstance.
                mDownloadThreadPool.
                execute(downloadTask.getHTTPDownloadRunnable());
        ...
    }
    */

}
