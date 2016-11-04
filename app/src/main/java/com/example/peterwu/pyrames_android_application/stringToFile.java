package com.example.peterwu.pyrames_android_application;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class stringToFile
{
    File path;
    String directory = ""/**/; // PUT File Name(with directory) HERE
    
    public stringToFile()
    {
        path =
            Environment.getExternalStoragePublicDirectory
            (
                //Environment.DIRECTORY_PICTURES
                Environment.DIRECTORY_DCIM + ""
            );
    }
    public void writeToFile(String data)
    {
        // Get the directory for the user's public pictures directory.


        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }

        final File file = new File(path, "config.txt");

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        } 
    }
}