package com.example.slidingmenuexample;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BTSearcher extends Activity {

	public static String DEVICE_ADDRESS = "device_address";

	private ArrayAdapter<String> deviceArrayAd;
	private ArrayAdapter<String> newDevicesArrayAd;
	private BluetoothAdapter blueToothAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		setResult(Activity.RESULT_CANCELED);

		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				findBTDevice();
				v.setVisibility(View.GONE);
			}
		});

		deviceArrayAd = new ArrayAdapter<String>(this, R.layout.device_name);
		newDevicesArrayAd = new ArrayAdapter<String>(this, R.layout.device_name);

		ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
		pairedListView.setAdapter(deviceArrayAd);
		pairedListView.setOnItemClickListener(displayBTInfo);

		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
		newDevicesListView.setAdapter(newDevicesArrayAd);
		newDevicesListView.setOnItemClickListener(displayBTInfo);

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		blueToothAd = BluetoothAdapter.getDefaultAdapter();

		Set<BluetoothDevice> pairedDevices = blueToothAd.getBondedDevices();

		for (BluetoothDevice device : pairedDevices) {
			if ((device.getBluetoothClass() != null)
					&& (device.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.TOY_ROBOT)) {
				deviceArrayAd.add(device.getName() + "\t - \t"
						+ device.getAddress());
			}
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (blueToothAd != null) {
			blueToothAd.cancelDiscovery();
		}

		this.unregisterReceiver(mReceiver);
	}

	private void findBTDevice() {
		setProgressBarIndeterminateVisibility(true);
		setTitle("Searching in Progress");

		if (blueToothAd.isDiscovering()) {
			blueToothAd.cancelDiscovery();
		}

		blueToothAd.startDiscovery();

		newDevicesArrayAd.clear();
	}

	private OnItemClickListener displayBTInfo = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int y, long z) {
			blueToothAd.cancelDiscovery();

			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);

			Intent intent = new Intent();
			intent.putExtra(DEVICE_ADDRESS, address);

			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String x = intent.getAction();

			if (BluetoothDevice.ACTION_FOUND.equals(x)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if ((device.getBondState() != BluetoothDevice.BOND_BONDED)
						&& (device.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.TOY_ROBOT)) {
					newDevicesArrayAd.add(device.getName() + "\t - \t"
							+ device.getAddress());
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(x)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle("Select device");
			}
		}
	};
}
