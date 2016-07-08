package com.example.peterwu.pyrames_android_application;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
//import com.jjoe64.graphview_demos.MainActivity;
//import com.jjoe64.graphview_demos.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Entered onCreate");

/*
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetooth  = BluetoothAdapter.getDefaultAdapter();

        if(bluetooth != null)
        {
            System.out.println("Hello");
            // Continue with bluetooth setup.
        }
    }


}
