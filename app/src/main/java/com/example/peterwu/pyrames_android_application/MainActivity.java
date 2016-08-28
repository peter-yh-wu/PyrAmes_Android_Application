package com.example.peterwu.pyrames_android_application;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

//import com.jjoe64.graphview_demos.MainActivity;
//import com.jjoe64.graphview_demos.R;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetooth;

    plotDynamic pd;

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;

    private  LineGraphSeries<DataPoint> series1;

    //Viewport variables
    int maxY2 = 50;
    int minY2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        bluetooth  = BluetoothAdapter.getDefaultAdapter();

        TextView textView1 = (TextView) findViewById(R.id.tv1);

        if(bluetooth != null)
        {
            //System.out.println("bluetooth isn't null");
            textView1.setText("bluetooth isn't null");

            String status;
            if (bluetooth.isEnabled()) {
                // Enabled. Work with Bluetooth.
            }
            else
            {
                // Disabled. Do something else
                //status = "Bluetooth is not Enabled.";
                bluetooth.enable();
            }
            String mydeviceaddress = bluetooth.getAddress();
            String mydevicename = bluetooth.getName();
            status = mydevicename + " : " + mydeviceaddress;
            textView1.setText(status);

            /*
            private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();

                    if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                        //discovery starts, we can show progress dialog or perform other tasks
                    } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                        //discovery finishes, dismis progress dialog
                    } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                        //bluetooth device found
                        BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        textView1.setText("Found device " + device.getName());
                    }
                }
            };
            */

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //simulate real time with thread that appends data to graph
        new Thread(new Runnable() {
            @Override
            public void run() {
                //we add 100 new entries
                for(int i = 0; i<100; i++)
                {
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
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d),true,10);
    }


}
