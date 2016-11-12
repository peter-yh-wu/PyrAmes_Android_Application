package com.example.peterwu.pyrames_android_application;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 *
 * "Save a File on Internal Storage"
 * https://developer.android.com/training/basics/data-storage/files.html
 *
 * http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
 */
public class MyFileWriter
{
    Context context;
    File path;
    String filename = "config.txt";
    //String directory = ""/**/; // PUT File Name(with directory) HERE

    /**
     *
     * @param context
     */
    public MyFileWriter(Context context)
    {
        /*
        path =
            Environment.getExternalStoragePublicDirectory
            (
                //Environment.DIRECTORY_PICTURES
                Environment.DIRECTORY_DCIM + ""
            );
            */
        this.context = context;
        path = context.getFilesDir();
    }
    public void writeToFile(String data)
    {
        // Get the directory

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }

        final File file = new File(path, filename);

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

    public String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}