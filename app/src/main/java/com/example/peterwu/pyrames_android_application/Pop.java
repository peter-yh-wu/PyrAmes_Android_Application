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
import android.util.Log;
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

    BluetoothAdapter bluetoothAdapter = null;
    ListView listViewPaired;
    ListView listViewDetected;
    ArrayList<String> arrayListpaired;
    ArrayAdapter<String> adapter,detectedAdapter;
    ArrayList<BluetoothDevice> arrayListPairedBluetoothDevices;
    ListItemClicked listItemClicked;
    ListItemClickedonPaired listItemClickedonPaired;
    ArrayList<BluetoothDevice> arrayListBluetoothDevices = null;
    BluetoothDevice btDevice;

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

        listViewPaired = (ListView) findViewById(R.id.listViewPaired);
        listViewDetected = (ListView) findViewById(R.id.listViewDetected);
        arrayListpaired = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(Pop.this, android.R.layout.simple_list_item_1, arrayListpaired);
        detectedAdapter = new ArrayAdapter<String>(Pop.this, android.R.layout.simple_list_item_single_choice);
        listViewDetected.setAdapter(detectedAdapter);
        listViewPaired.setAdapter(adapter);

        arrayListPairedBluetoothDevices = new ArrayList<BluetoothDevice>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listItemClicked = new ListItemClicked();
        listItemClickedonPaired = new ListItemClickedonPaired();
        arrayListBluetoothDevices = new ArrayList<BluetoothDevice>();

        onBluetooth(); //turns on bluetooth
        makeDiscoverable();

        startSearching(); //starts searching in onCreate
        arrayListBluetoothDevices.clear();

        /*
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        if (bluetooth != null) {
            System.out.println("pop-up bluetooth isn't null");

            String status;

            String mydeviceaddress = bluetooth.getAddress();
            //String mydevicename = bluetooth.getName();
            status = mydeviceaddress;
            System.out.println(status);

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
        }
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPairedDevices();
        listViewDetected.setOnItemClickListener(listItemClicked);
        listViewPaired.setOnItemClickListener(listItemClickedonPaired);
    }
    private void getPairedDevices() {
        Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
        if(pairedDevice.size()>0)
        {
            for(BluetoothDevice device : pairedDevice)
            {
                arrayListpaired.add(device.getName()+"\n"+device.getAddress());
                arrayListPairedBluetoothDevices.add(device);
            }
        }
        adapter.notifyDataSetChanged();
    }
    class ListItemClicked implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            btDevice = arrayListBluetoothDevices.get(position);
            Log.i("Log", "The device : "+btDevice.toString());
            Boolean isBonded = false;
            try {
                isBonded = createBond(btDevice);
                if(isBonded)
                {
                    getPairedDevices();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Log", "The bond is created: "+isBonded);
        }
    }
    class ListItemClickedonPaired implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            btDevice = arrayListPairedBluetoothDevices.get(position);
            try {
                Boolean removeBonding = removeBond(btDevice);
                if(removeBonding)
                {
                    arrayListpaired.remove(position);
                    adapter.notifyDataSetChanged();
                }
                Log.i("Log", "Removed"+removeBonding);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean removeBond(BluetoothDevice btDevice)
            throws Exception
    {
        Class btClass = Class.forName("android.bluetooth.BluetoothDevice");
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }
    public boolean createBond(BluetoothDevice device) throws Exception
    {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //NOT SURE WHAT BELOW IF CONDITION DOES
                //CHECK IF WORKS
                //...
                if(arrayListBluetoothDevices.size()<1) // this checks if the size of bluetooth device is 0,then add the
                {                                           // device to the arraylist.
                    detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                    arrayListBluetoothDevices.add(device);
                    detectedAdapter.notifyDataSetChanged();
                }
                else
                {
                    boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                    for(int i = 0; i<arrayListBluetoothDevices.size();i++)
                    {
                        if(device.getAddress().equals(arrayListBluetoothDevices.get(i).getAddress()))
                        {
                            flag = false;
                        }
                    }
                    if(flag == true)
                    {
                        detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                        arrayListBluetoothDevices.add(device);
                        detectedAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    private void startSearching() {
        Log.i("Log", "in the start searching method");
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, intentFilter);
        bluetoothAdapter.startDiscovery();
    }
    private void onBluetooth() {
        if(!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();
            Log.i("Log", "Bluetooth is Enabled");
        }
    }
    private void makeDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.i("Log", "Discoverable ");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }

    //------------------------------------------

    public static BluetoothDevice getDevice() {
        return device;
    }

    //WHEN IS CONNECT METHOD USED?
    //...
    private Boolean connect(BluetoothDevice device) {
        Boolean bool = false;
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }
}
