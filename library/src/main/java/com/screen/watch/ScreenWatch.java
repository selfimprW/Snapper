package com.screen.watch;

import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;

/**
 * Created by Joel on 26/10/2015.
 */
public class ScreenWatch extends FileObserver {
    private static final String TAG = "ScreenWatch";
    private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/";

    private OnScreenshotTakenListener mListener;
    private String mLastTakenPath;

    public ScreenWatch(OnScreenshotTakenListener listener) {
        super(PATH, FileObserver.CLOSE_WRITE);
        mListener = listener;
    }

    @Override
    public void onEvent(int event, String path) {
        Log.i(TAG, "Event:" + event + "\t" + path);

        if (path==null || event!=FileObserver.CLOSE_WRITE)
            Log.i(TAG, "Don't care.");
        else if (mLastTakenPath!=null && path.equalsIgnoreCase(mLastTakenPath))
            Log.i(TAG, "This event has been observed before.");
        else {
            mLastTakenPath = path;
            File file = new File(PATH+path);
            mListener.onScreenshotTaken(Uri.fromFile(file));
            Log.i(TAG, "Send event to listener.");
        }
    }

    public void start() {
        super.startWatching();
    }

    public void stop() {
        super.stopWatching();
    }

}