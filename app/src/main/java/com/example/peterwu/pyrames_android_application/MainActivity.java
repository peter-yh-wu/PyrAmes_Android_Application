package com.example.peterwu.pyrames_android_application;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        Firebase rootRef = new Firebase("https://pyrames-ca318.firebaseio.com/");
        //Firebase testRef = rootRef.child("testInteger");
        int tester = 22;
        //ArrayList<Integer> tester = new ArrayList<Integer>();
        //tester.add(22);
        //tester.add(25);
        //rootRef.setValue(tester);
        
        
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.setValue(tester);
        //hello
   

        //String test = FirebaseDatabase.getSdkVersion();
        //System.out.println(test);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("pyrames-ca318");

        //myRef.setValue("Hello, World!");
        System.out.println("Entered onCreate");

        //Graphs
        //graphview 1
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
        bluetooth = BluetoothAdapter.getDefaultAdapter();

        TextView textView1 = (TextView) findViewById(R.id.tv1);

        if (bluetooth != null) {
            //System.out.println("bluetooth isn't null");
            textView1.setText("bluetooth isn't null");

            String status;
            if (bluetooth.isEnabled()) {
                // Enabled. Work with Bluetooth.
            } else {
                // Disabled. Do something else
                //status = "Bluetooth is not Enabled.";
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
                System.out.println("Paired with a device");
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

    //
    // For generating test data
    //
    //
    //
    //
    @Override
    protected void onResume() {
        super.onResume();
        //simulate real time with thread that appends data to graph
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
    }

    //add random data to graph
    private void addEntry() {
        //display max 10 point on viewport and scroll to end
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), true, 10);
    }


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
