package com.example.peterwu.pyrames_android_application;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by peterwu on 9/11/16.
 */
public class Pop extends Activity {

    BluetoothAdapter bluetooth;

    ArrayAdapter mArrayAdapter;

    private static BluetoothDevice device = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));

        //ArrayAdapter
        //
        //
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        // Implement Bluetooth here
        //
        // REVIEW ; REVIEW
        // REVIEW ; REVIEW
        // REVIEW ; REVIEW
        //

        bluetooth = BluetoothAdapter.getDefaultAdapter();

        if (bluetooth != null) {
            System.out.println("pop-up bluetooth isn't null");

            String status;
            if (bluetooth.isEnabled()) {
                // Enabled. Work with Bluetooth.
            } else {
                // Disabled. Do something else
                //status = "Bluetooth is not Enabled.";
                bluetooth.enable();
            }
            String mydeviceaddress = bluetooth.getAddress();
            //String mydevicename = bluetooth.getName();
            status = mydeviceaddress;
            System.out.println(status);

            //
            // get paired devices
            //
            // OPTIONAL: DELETE
            // OPTIONAL: DELETE
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
                }
            } else {
                System.out.println("Not paired with a device");
            }

            IntentFilter filter = new IntentFilter();

            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

            registerReceiver(mReceiver, filter);
            bluetooth.startDiscovery();

            //ListView
            ListView listView = (ListView) findViewById(R.id.lvItems);
            listView.setAdapter(mArrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str = (String)parent.getItemAtPosition(position);
                    System.out.println("Clicked: "+str);

                    device = bluetooth.getRemoteDevice(str);
                    //pairDevice(bluetooth.getRemoteDevice(str));
                }
            });

            //
            // NEXT STEP
            // NEXT STEP --------------------
            // NEXT STEP
            // NEXT STEP
            //
            // get data stream from bluetooth device android
            //
            //

        }//if bluetooth != null
    }

    public static BluetoothDevice getDevice() {
        return device;
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismiss progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mArrayAdapter.add(device.getAddress());
                //showToast("Found device " + device.getName());
            }
        }
    };

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }
}
