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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//pass: pyrames123
//autoscaling

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetooth;

    //DELETE
    private int lastX = 0;

    private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private LineGraphSeries<DataPoint> series4;

    //Viewport variables
    //
    int minX = 0; int maxX = 30;
    int yRange = 1500;
    int minY1 = 8500; int maxY1 = minY1+yRange;
    int minY2 = 9000; int maxY2 = minY2+yRange;
    int minY3 = 11500; int maxY3 = minY3+yRange;
    int minY4 = 10500; int maxY4 = minY4+yRange;

    //For Firebase
    //
    DatabaseReference mRootRef;
    DatabaseReference ref1, ref2, ref3, ref4;
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

    String bigString = "";
    int bigStringIndex = 0;

    int dataArr[][] = new int[4][50000];
    int dataArrIndex = 0;

    int graphIndex = 0;

    int yIncrement = 100;

    //viewports
    //
    Viewport viewport1 = null; Viewport viewport2 = null; Viewport viewport3 = null; Viewport viewport4 = null;

    //Thread Pool Executor
    //Thread Pool Executor
    //
    // TO DO TO DO TO DO TO DO
    //
    //
    //private ThreadPoolExecutor mPool;

    private boolean autoScaleIsOn = false;
    private void setAutoScalingOn() {
        autoScaleIsOn = true;
    }
    private void setAutoScalingOff() {
        autoScaleIsOn = false;
    }

    public void upload()
    {
        /*ArrayList<Integer> r1 = new ArrayList<Integer>();
        ArrayList<Integer> r2= new ArrayList<Integer>();
        ArrayList<Integer> r3= new ArrayList<Integer>();
        ArrayList<Integer> r4= new ArrayList<Integer>();
        for(int i = 0;i < dataArr[0].length;i++)
        {
            r1.add(dataArr[0][i]);
            r2.add(dataArr[1][i]);
            r3.add(dataArr[2][i]);
            r4.add(dataArr[3][i]);
        }
        ref1.setValue("1");
        ref2.setValue(r2);
        ref3.setValue(r3);*/
        ref4.setValue("is working");
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android.os.Debug.waitForDebugger();

        //startActivity(new Intent(MainActivity.this,Pop.class));

        //Firebase Code
        //
        //
        //
        Firebase.setAndroidContext(this);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.setValue("x");
        System.out.println("here");
        /*ref1 = mRootRef.child("channel_1");
        ref2 = mRootRef.child("channel_2");
        ref3 = mRootRef.child("channel_3");
        ref4 = mRootRef.child("channel_4");
        upload();*/
        //Firebase.setAndroidContext(this);
        //Firebase rootRef = new Firebase("https://pyrames-ca318.firebaseio.com/");
        //Firebase testRef = rootRef.child("testInteger");
        //int tester = 22;
        //ArrayList<Integer> tester = new ArrayList<Integer>();
        //tester.add(22);
        //tester.add(25);
        //rootRef.setValue(tester);
        //DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        //mRootRef.setValue(tester);
        //hello
        //String test = FirebaseDatabase.getSdkVersion();
        //System.out.println(test);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference();
        //myRef.setValue(tester);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //System.out.println("Entered onCreate");

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

        //------------------
        //Graphics
        //
        //
        //
        // upload button
        Button buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        //
        // TO DO: SHOW UPLOAD BUTTON ONLY WHEN "STOP" BUTTON SHOWS
        //

        //
        // pop-up window
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
        //data
        series1 = new LineGraphSeries<DataPoint>();
        series2 = new LineGraphSeries<DataPoint>();
        series3 = new LineGraphSeries<DataPoint>();
        series4 = new LineGraphSeries<DataPoint>();

        //customize viewports
        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        graph1.addSeries(series1);
        viewport1 = graph1.getViewport();
        viewport1.setYAxisBoundsManual(true);
        viewport1.setMinY(minY1);
        viewport1.setMaxY(maxY1);
        viewport1.setScrollable(true);
        viewport1.setXAxisBoundsManual(true);
        viewport1.setMinX(minX);
        viewport1.setMaxX(maxX);

        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        graph2.addSeries(series2);
        viewport2 = graph2.getViewport();
        viewport2.setYAxisBoundsManual(true);
        viewport2.setMinY(minY2);
        viewport2.setMaxY(maxY2);
        viewport2.setScrollable(true);
        viewport2.setScalable(true);
        viewport2.setXAxisBoundsManual(true);
        viewport2.setMinX(minX);
        viewport2.setMaxX(maxX);

        GraphView graph3 = (GraphView) findViewById(R.id.graph3);
        graph3.addSeries(series3);
        viewport3 = graph3.getViewport();
        viewport3.setYAxisBoundsManual(true);
        viewport3.setMinY(minY3);
        viewport3.setMaxY(maxY3);
        viewport3.setScrollable(true);
        viewport3.setScalable(true);
        viewport3.setXAxisBoundsManual(true);
        viewport3.setMinX(minX);
        viewport3.setMaxX(maxX);

        GraphView graph4 = (GraphView) findViewById(R.id.graph4);
        graph4.addSeries(series4);
        viewport4 = graph4.getViewport();
        viewport4.setYAxisBoundsManual(true);
        viewport4.setMinY(minY4);
        viewport4.setMaxY(maxY4);
        viewport4.setScrollable(true);
        viewport4.setScalable(true);
        viewport4.setXAxisBoundsManual(true);
        viewport4.setMinX(minX);
        viewport4.setMaxX(maxX);

        //Buttons
        //
        //
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY1 += yIncrement;
                maxY1 += yIncrement;
                viewport1.setMaxY(maxY1);
                viewport1.setMinY(minY1);
            }
        });
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY1 -= yIncrement;
                maxY1 -= yIncrement;
                viewport1.setMaxY(maxY1);
                viewport1.setMinY(minY1);
            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY2 += yIncrement;
                maxY2 += yIncrement;
                viewport2.setMaxY(maxY2);
                viewport2.setMinY(minY2);
            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY2 -= yIncrement;
                maxY2 -= yIncrement;
                viewport2.setMaxY(maxY2);
                viewport2.setMinY(minY2);
            }
        });
        final Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY3 += yIncrement;
                maxY3 += yIncrement;
                viewport3.setMaxY(maxY3);
                viewport3.setMinY(minY3);
            }
        });
        final Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY3 -= yIncrement;
                maxY3 -= yIncrement;
                viewport3.setMaxY(maxY3);
                viewport3.setMinY(minY3);
            }
        });
        final Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY4 += yIncrement;
                maxY4 += yIncrement;
                viewport4.setMaxY(maxY4);
                viewport4.setMinY(minY4);
            }
        });
        final Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minY4 -= yIncrement;
                maxY4 -= yIncrement;
                viewport4.setMaxY(maxY4);
                viewport4.setMinY(minY4);
            }
        });
        //Auto-scale button
        final Button buttonAutoScale = (Button) findViewById(R.id.buttonAutoScale);
        buttonAutoScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnText = (String)buttonAutoScale.getText();
                if(btnText.equals("Auto-Scale Is Off")) {
                    buttonAutoScale.setText("Auto-Scale Is On");
                    setAutoScalingOn();
                }
                else {
                    buttonAutoScale.setText("Auto-Scale Is Off");
                    setAutoScalingOff();
                }
            }
        });

        //------------------
        //bluetooth
        // code for bluetooth, bluetooth code
        //
        //
        //
        bluetooth = BluetoothAdapter.getDefaultAdapter();

        if (bluetooth != null) {
            System.out.println("bluetooth isn't null");

            if (!bluetooth.isEnabled()) {
                bluetooth.enable();
            }
        }
    }

    final int handlerState = 0;                        //used to identify handler message
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    //Threads
    //
    //

    //Thread Pool Executor

    //ExecutorService fixedPool = Executors.newFixedThreadPool(3);

    //Other Threads
    private ConnectedThread mConnectedThread;
    private ReadThread mReadThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    static boolean isStreaming = false;
    static boolean startReading = false;

    @Override
    protected void onResume() {
        super.onResume();

        //System.out.println("Resuming Main Activity");

        //
        // streams data if device is connected
        //

        BluetoothDevice device = null;
        //temporarily disable
        Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice tDevice : pairedDevices) {
                device = tDevice;
            }
        }

        if(device==null)
            System.out.println("Device null");
        else
            System.out.println("address: "+device.getAddress());

        /*
        if(device==null) {
            device = Pop.getDevice();
        }
        */

        //if(device!=null) {
            /*
            //pair device
            try {
                Method method = device.getClass().getMethod("createBond", (Class[]) null);
                method.invoke(device, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            */

        //create socket...
        try {
            btSocket = createBluetoothSocket(device);
            //Method m= device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            //btSocket = (BluetoothSocket) m.invoke(device, 1);
        } catch (Exception e) {
            System.out.println("Socket creation failed");
            //Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }

        // Establish the Bluetooth socket connection.
        try {
            btSocket.connect();
            System.out.println("Socket Connected");
        } catch (IOException e) {
            System.out.println("Socket Didn't Connect");
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.setPriority(Thread.MAX_PRIORITY);
        //mConnectedThread.setPriority(Thread.NORM_PRIORITY+4);
        mConnectedThread.start();

        mReadThread = new ReadThread();
        mReadThread.start();
        mConnectedThread.setPriority(Thread.NORM_PRIORITY + 2);

        final Button buttonStream = (Button) findViewById(R.id.buttonStream);
        buttonStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnText = (String) buttonStream.getText();
                if (btnText.equals("Stream")) {
                    mConnectedThread.write("Y");
                    buttonStream.setText("Stop");
                    isStreaming = true;
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted Exception");
                    }
                    char tChar = bigString.charAt(bigStringIndex);
                    char tChar2 = bigString.charAt(bigStringIndex+37);
                    while (tChar != 'E' || tChar2 != 'E') {
                        bigStringIndex++;
                        tChar = bigString.charAt(bigStringIndex);
                        tChar2 = bigString.charAt(bigStringIndex+37);
                    }

                    System.out.println(bigStringIndex);
                    System.out.println(bigString);

                    startReading = true;
                } else {
                    mConnectedThread.write("X");
                    buttonStream.setText("Stream");
                    isStreaming = false;
                    startReading = false;
                }
            }
        });

        //------------
        // graphing data thread
        // graph thread, display thread
        //
        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                //we add 100 new entries
                for (int i = 0; i < 10000; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //originally just "addEntry();"
                            int pseudoGraphIndex = graphIndex * 5;

                            if (startReading) {
                                series1.appendData(new DataPoint(lastX++, dataArr[0][pseudoGraphIndex]), true, 10);
                                series2.appendData(new DataPoint(lastX++, dataArr[1][pseudoGraphIndex]), true, 10);
                                series3.appendData(new DataPoint(lastX++, dataArr[2][pseudoGraphIndex]), true, 10);
                                series4.appendData(new DataPoint(lastX++, dataArr[3][pseudoGraphIndex]), true, 10);
                                graphIndex++;

                                if (autoScaleIsOn) {
                                    viewport1.setMinY(series1.getLowestValueY());
                                    viewport1.setMaxY(series1.getHighestValueY());
                                    viewport2.setMinY(series2.getLowestValueY());
                                    viewport2.setMaxY(series2.getHighestValueY());
                                    viewport3.setMinY(series3.getLowestValueY());
                                    viewport3.setMaxY(series3.getHighestValueY());
                                    viewport4.setMinY(series4.getLowestValueY());
                                    viewport4.setMaxY(series4.getHighestValueY());
                                }
                            }
                        }
                    });

                    // sleep to slow down addition of entries
                    try {
                        Thread.sleep(500); // display 30 points &
                    } catch (InterruptedException e) {
                        //manage error...
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //}
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            System.out.println("Bluetooth Socket Didn't Close");
            //insert code to deal with this
        }
    }

    //
    //
    // Display Device Battery Life somewhere
    // TO DO
    // TO DO
    //
    //

    //read thread
    //
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
                if (startReading) {

                    try {
                        Thread.sleep(90);
                    } catch (InterruptedException e) {
                        //manage error...
                        e.printStackTrace();
                    }

                    //System.out.println("Reading");

                    bigStringIndex += 2;
                    String s1 = bigString.substring(bigStringIndex, bigStringIndex + 5);
                    bigStringIndex += 7;
                    String s2 = bigString.substring(bigStringIndex, bigStringIndex + 5);
                    bigStringIndex += 7;
                    String s3 = bigString.substring(bigStringIndex, bigStringIndex + 5);
                    bigStringIndex += 7;
                    String s4 = bigString.substring(bigStringIndex, bigStringIndex + 5);
                    bigStringIndex += 7;
                    String s5 = bigString.substring(bigStringIndex, bigStringIndex + 5);
                    bigStringIndex += 7;

                    dataArr[0][dataArrIndex] = Integer.parseInt(s1);
                    dataArr[1][dataArrIndex] = Integer.parseInt(s2);
                    dataArr[2][dataArrIndex] = Integer.parseInt(s3);
                    dataArr[3][dataArrIndex] = Integer.parseInt(s4);

                    int i1 = dataArr[0][dataArrIndex];
                    int i2 = dataArr[1][dataArrIndex];
                    int i3 = dataArr[2][dataArrIndex];
                    int i4 = dataArr[3][dataArrIndex];

                    //System.out.println(dataArrIndex+": "+i1 + ", " + i2 + ", " + i3 + ", " + i4 + ", " + s5);

                    dataArrIndex++;

                    //instantiate data array
                    //dataArr[0][dataArrIndex] = v1;

                }
                /*
                try {
                    if (isStreaming) {


                        try {

                            System.out.println(br.read());

                        } finally {
                            br.close();
                        }


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
                */
            }
        }
    }

    // stream data thread
    //
    //
    //

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

        int count = 1;

        public void run() {
            byte[] buffer = new byte[256]; // 256, sleep 100: 2466 E / min.
            int bytes;

            while (true) {
                if(isStreaming) {
                    try {
                        bytes = mmInStream.read(buffer);

                        String readMessage = new String(buffer, 0, bytes);
                        bigString = bigString.concat(readMessage);

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            //manage error...
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        break;
                    }
                }
            }
            /*
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
            */
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

        try {
            btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    // ------------------------------------------------------
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
}
