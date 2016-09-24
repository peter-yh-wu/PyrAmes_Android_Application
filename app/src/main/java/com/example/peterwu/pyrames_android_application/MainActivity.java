package com.example.peterwu.pyrames_android_application;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

//import com.jjoe64.graphview_demos.MainActivity;
//import com.jjoe64.graphview_demos.R;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetooth;

    plotDynamic pd;

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;

    private LineGraphSeries<DataPoint> series1;

    //Viewport variables
    int maxY2 = 50;
    int minY2 = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //File
    File dir;
    File file;
    //File file = new File("C:/file.txt");

    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Firebase.setAndroidContext(this);
        //Firebase rootRef = new Firebase("https://pyrames-ca318.firebaseio.com/");
        //Firebase testRef = rootRef.child("testInteger");
        //int tester = 22;
        ArrayList<Integer> tester = new ArrayList<Integer>();
        tester.add(22);
        tester.add(25);
        //rootRef.setValue(tester);

        
        //DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        //mRootRef.setValue(tester);
        //hello


        //String test = FirebaseDatabase.getSdkVersion();
        //System.out.println(test);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.setValue(tester);


        System.out.println("Entered onCreate");

        //File
        //
        //

        /*

        //dir = new File(path);
        //dir.mkdirs();
        //file = new File(path+"/file.txt");
        //file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
        File external = Environment.getExternalStorageDirectory();
        String sdcardPath = external.getPath();
        file = new File(sdcardPath + "/Documents/file.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("IOException while creating file");
        }

        */

        //
        // pop-up window
        //
        //
        //
        Button buttonPopUp = (Button) findViewById(R.id.buttonPopUp);

        buttonPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Pop.class));
            }
        });

        //
        // Graphs
        //
        //graphview 1
        //
        //
        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        //data
        series1 = new LineGraphSeries<DataPoint>();
        graph1.addSeries(series1);
        //customize viewport
        Viewport viewport1 = graph1.getViewport();
        viewport1.setYAxisBoundsManual(true);
        viewport1.setMinY(-5);
        viewport1.setMaxY(5);
        viewport1.setScrollable(true);

        //get graphview instance
        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        //data
        series = new LineGraphSeries<DataPoint>();
        graph2.addSeries(series);
        //customize viewport
        final Viewport viewport2 = graph2.getViewport();
        viewport2.setYAxisBoundsManual(true);
        viewport2.setMinY(minY2);
        viewport2.setMaxY(maxY2);
        viewport2.setScrollable(true);
        viewport2.setScalable(true);

        //Buttons
        //
        //
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                System.out.println("Button Pressed");
            }
        });


        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                maxY2 += 1;
                minY2 += 1;
                viewport2.setMaxY(maxY2);
                viewport2.setMinY(minY2);
            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                maxY2 -= 1;
                minY2 -= 1;
                viewport2.setMaxY(maxY2);
                viewport2.setMinY(minY2);
            }
        });

        //bluetooth
        //
        //
        //
        //

        bluetooth = BluetoothAdapter.getDefaultAdapter();

        TextView textView1 = (TextView) findViewById(R.id.tv1);

        if (bluetooth != null) {
            //System.out.println("bluetooth isn't null");
            textView1.setText("bluetooth isn't null");

            String status;
            if (!bluetooth.isEnabled()) {
                bluetooth.enable();
            }
            String mydeviceaddress = bluetooth.getAddress();
            String mydevicename = bluetooth.getName();
            status = mydevicename + " : " + mydeviceaddress;
            textView1.setText(status);

            //
            // get paired devices
            //
            //
            //
            ArrayList<String> al1 = new ArrayList<String>();
            //ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>();
            Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                System.out.println("Paired with:");
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    al1.add(device.getName());
                    System.out.println(device.getName());
                }
            }
            else {
                System.out.println("Not paired with a device");
            }





        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    final int handlerState = 0;                        //used to identify handler message
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;
    private ReadThread mReadThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    // ?
    // ultimately for parsing data stream?
    // https://wingoodharry.wordpress.com/2014/04/15/android-sendreceive-data-with-arduino-using-bluetooth-part-2/
    //
    // bluetoothIn = new Handler() { ... }

    static boolean isStreaming = false;

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("Resuming Main Activity");

        //
        // streams data if device is connected
        //

        BluetoothDevice device = null;
        Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice tDevice : pairedDevices) {
                device = tDevice;
            }
        }

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        mReadThread = new ReadThread();
        mReadThread.start();

        final Button buttonStream = (Button) findViewById(R.id.buttonStream);
        buttonStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnText = (String)buttonStream.getText();
                if(btnText.equals("Stream"))
                {
                    mConnectedThread.write("Y");
                    buttonStream.setText("Stop");
                    isStreaming = true;
                }
                else
                {
                    mConnectedThread.write("X");
                    buttonStream.setText("Stream");
                    isStreaming = false;
                }
            }
        });

        //------------
        // ? simulate real time with thread that appends data to graph
        //
        // FOR TEST DATA, DELETE LATER
        // DELETE LATER
        //
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                //we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down addition of entries
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        //manage error...
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */
    }

    //add random data to graph
    /*
    private void addEntry() {
        //display max 10 point on viewport and scroll to end
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), true, 10);
    }
    */

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //private static String bigString = "";


    //
    //
    // Display Device Battery Life somewhere
    // TO DO
    // TO DO
    //
    //

    //create new class for read thread
    private class ReadThread extends Thread {
        //private final BufferedReader br;

        private ReadThread() {

            /*
            BufferedReader tBR = null;
            try {
                tBR = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                System.out.println("File not Found");
            }
            br = tBR;
            */
        }

        public void run() {
            while(true)
            {
                try {
                    if (isStreaming) {

                        /*
                        try {

                            System.out.println(br.read());

                        } finally {
                            br.close();
                        }
                        */

                        FileInputStream fis = new FileInputStream(file);
                        try {
                            System.out.println(fis.read());
                        } finally {
                            fis.close();
                        }
                        // sleep to slow down process
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            //manage error...
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e){
                    System.out.println("Read file not found");
                }
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[64]; //256
            int bytes;

            try {
                FileOutputStream stream = new FileOutputStream(file);

                // Keep looping to listen for received messages
                while (true) {
                    try {
                        bytes = mmInStream.read(buffer);            //read bytes from input buffer

                        String readMessage = new String(buffer, 0, bytes);

                        //
                        // IMPLEMENT HANDLER LATER
                        //
                        // Send the obtained bytes to the UI Activity via handler
                        //bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();

                        //bigString.concat(readMessage);

                        //write to file
                        //
                        //
                        try {
                            //stream.write(bytes);
                            //System.out.println(readMessage);
                        } finally {
                            stream.close();
                        }
                    } catch (IOException e) {
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Write File Not Found");
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }


    // ---------------------------------------
    // for Google API Usage
    //
    //

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
