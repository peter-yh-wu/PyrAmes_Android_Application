package com.example.peterwu.pyrames_android_application;

import android.os.RecoverySystem;

import com.box.androidsdk.content.BoxApiFile;
import com.box.androidsdk.content.BoxConfig;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxSession;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Srivatsav on 7/8/2016.
 */




import java.sql.Timestamp;
import java.util.Date;

public class BoxUploader
{

    private BoxSession session;
    //private int counter = 1;

    public void authenticate()
    {
        BoxConfig.IS_LOG_ENABLED = true;
        BoxConfig.CLIENT_ID = "3wk497spw0f6v6z379loxp6jjmcyy0ih";
        BoxConfig.CLIENT_SECRET = "qJZn8Q7AGPAffQULpwwwjwr9rr69LIW9";
        BoxConfig.REDIRECT_URL = "https://www.box.com/api/oauth2/authorize";

        session = new BoxSession(MainActivity.this);
        session.authenticate();
    }

    public void uploadData(int[] data) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for(int x : data)
        {
            builder.append(x);
            builder.append("\n");
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream( builder.toString().getBytes("UTF-8") );



    }

    private void upload(InputStream inputStream)
    {
        BoxApiFile fileApi = new BoxApiFile(session);
        String Filename = "data-upload" + fileName() + ".txt";
        String Folderid = "Sensor data and programs\\Pyda_Peter_Data";
        BoxFile uploadedFile = fileApi.getUploadRequest(inputStream, Filename, Folderid)
                // Optional: Set a listener to track upload progress.
                .setProgressListener(new RecoverySystem.ProgressListener() {
                    @Override
                    public void onProgressChanged(long numBytes, long totalBytes) {
                        // Update a progress bar, etc.
                    }
                })
                .send();
    }

    private String fileName()
    {
        java.util.Date date= new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.toString();
    }
}
