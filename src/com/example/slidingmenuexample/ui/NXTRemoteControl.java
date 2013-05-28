/*
 * Copyright (c) 2010 Jacek Fedorynski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is derived from:
 * 
 * http://developer.android.com/resources/samples/BluetoothChat/src/com/example/android/BluetoothChat/BluetoothChat.html
 * 
 * Copyright (c) 2009 The Android Open Source Project
 */

package com.example.slidingmenuexample.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.example.slidingmenuexample.ChooseDeviceActivity;
import com.example.slidingmenuexample.MenuListPages;
import com.example.slidingmenuexample.NXTTalker;
import com.example.slidingmenuexample.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class NXTRemoteControl extends SlidingFragmentActivity implements
		OnSharedPreferenceChangeListener {
	
	public static final String CONNECT_IMG_REF = "button_a";
	public static final String DISCONNECT_IMG_REF = "button_b";
	public static final String UP_IMG_REF = "up_arrow";
	public static final String MIDDLE_IMG_REF = "middle_button";
	public static final String DOWN_IMG_REF = "down_arrow";
	public static final String LEFT_IMG_REF = "left_arrow";
	public static final String RIGHT_IMG_REF = "right_arrow";

	private boolean NO_BT = false;

	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_CONNECT_DEVICE = 2;
	private static final int REQUEST_SETTINGS = 3;

	public static final int MESSAGE_TOAST = 1;
	public static final int MESSAGE_STATE_CHANGE = 2;

	public static final String TOAST = "toast";

	private static final int MODE_BUTTONS = 1;
	
	private BluetoothAdapter mBluetoothAdapter;   
	private PowerManager mPowerManager;
	private PowerManager.WakeLock mWakeLock;
	private NXTTalker mNXTTalker;

	private int mState = NXTTalker.STATE_NONE;
	private int mSavedState = NXTTalker.STATE_NONE;
	private boolean mNewLaunch = true;
	private String mDeviceAddress = null;
	private TextView mStateDisplay;
	private ImageView mConnectButton;
	private ImageView mDisconnectButton;
	SlidingMenu pagelist;

	private int mPower = 80;
	private int mControlsMode = MODE_BUTTONS;

	private boolean mReverse;
	private boolean mReverseLR;
	private boolean mRegulateSpeed;
	private boolean mSynchronizeMotors;
	
	private Fragment mContent;

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		readPreferences(prefs, null);
		prefs.registerOnSharedPreferenceChangeListener(this);

		if (savedInstanceState != null) {
			mNewLaunch = false;
			mDeviceAddress = savedInstanceState.getString("device_address");
			if (mDeviceAddress != null) {
				mSavedState = NXTTalker.STATE_CONNECTED;
			}

			if (savedInstanceState.containsKey("power")) {
				mPower = savedInstanceState.getInt("power");
			}
			if (savedInstanceState.containsKey("controls_mode")) {
				mControlsMode = savedInstanceState.getInt("controls_mode");
			}
		}

		mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
				| PowerManager.ON_AFTER_RELEASE, "NXT Remote Control");

		if (!NO_BT) {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

			if (mBluetoothAdapter == null) {
				Toast.makeText(this, "Bluetooth is not available",
						Toast.LENGTH_LONG).show();
				finish();
				return;
			}
		}

		setupUI();

		mNXTTalker = new NXTTalker(mHandler);
		
		pagelist = getSlidingMenu();
		pagelist.setShadowDrawable(R.drawable.shadow);
		pagelist.setBehindOffset(600);
		pagelist.setShadowWidth(15);
		pagelist.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new NXTRemoteControlView();

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListPages()).commit();
		
		setSlidingActionBarEnabled(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	
		if (mState == NXTTalker.STATE_CONNECTED) {
			outState.putString("device_address", mDeviceAddress);
		} 
		
		outState.putInt("power", mPower);
		outState.putInt("controls_mode", mControlsMode);
	}
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);
	}
	
	@SuppressLint("ValidFragment")
	public class NXTRemoteControlView extends SherlockFragment {
		
		public NXTRemoteControlView() {
			
		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.remote_control, container,
					false);
		}
		
		public void onStart() {
			//TODO Auto-generated method
			super.onStart();
			
			if (!NO_BT) {
				if (!mBluetoothAdapter.isEnabled()) {
					Intent enableIntent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
				} else {
					if (mSavedState == NXTTalker.STATE_CONNECTED) {
						BluetoothDevice device = mBluetoothAdapter
								.getRemoteDevice(mDeviceAddress);
						mNXTTalker.connect(device);
					} else {
						if (mNewLaunch) {
							mNewLaunch = false;
							findBrick();
						}
					}
				}
			}
			
			setupUI();
			
			ImageView button_a = (ImageView) findViewById(R.id.button_a);
			button_a.setImageResource((R.drawable.button_a));
			
			ImageView button_b = (ImageView) findViewById(R.id.button_b);
			button_b.setImageResource((R.drawable.button_b));		
			
			ImageView up_arrow = (ImageView) findViewById(R.id.button_up);
			up_arrow.setImageResource((R.drawable.up_arrow));			
			
			ImageView down_arrow = (ImageView) findViewById(R.id.button_down);
			down_arrow.setImageResource((R.drawable.down_arrow));		
			
			ImageView middle_button = (ImageView) findViewById(R.id.button_middle);
			middle_button.setImageResource((R.drawable.middle_button));	
			
			ImageView left_arrow = (ImageView) findViewById(R.id.button_left);
			left_arrow.setImageResource((R.drawable.left_arrow));	
			
			ImageView right_arrow = (ImageView) findViewById(R.id.button_right);
			right_arrow.setImageResource((R.drawable.right_arrow));
			
		}
		
/*		public void switchContent(Fragment fragment) {
			mContent = fragment;
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			getSlidingMenu().showContent();
		}*/
	}

	private class DirectionButtonOnTouchListener implements OnTouchListener {

		private double lmod;
		private double rmod;

		public DirectionButtonOnTouchListener(double l, double r) {
			lmod = l;
			rmod = r;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			
			if (action == MotionEvent.ACTION_DOWN) {
				byte power = (byte) mPower;
				if (mReverse) {
					power *= -1;
				}
				byte l = (byte) (power * lmod);
				byte r = (byte) (power * rmod);
				if (!mReverseLR) {
					mNXTTalker.motors(l, r, mRegulateSpeed, mSynchronizeMotors);
				} else {
					mNXTTalker.motors(r, l, mRegulateSpeed, mSynchronizeMotors);
				}
			} else if ((action == MotionEvent.ACTION_UP)
					|| (action == MotionEvent.ACTION_CANCEL)) {
				mNXTTalker.motors((byte) 0, (byte) 0, mRegulateSpeed,
						mSynchronizeMotors);
			}
			return true;
		}
	}

	private void setupUI() {
		if (mControlsMode == MODE_BUTTONS) {
			setContentView(R.layout.remote_control);

			ImageView buttonUp = (ImageView) findViewById(R.id.button_up);
			buttonUp.setOnTouchListener(new DirectionButtonOnTouchListener(1, 1));
			
			ImageView buttonLeft = (ImageView) findViewById(R.id.button_left);
			buttonLeft.setOnTouchListener(new DirectionButtonOnTouchListener(
					-0.6, 0.6));
			ImageView buttonDown = (ImageView) findViewById(R.id.button_down);
			buttonDown.setOnTouchListener(new DirectionButtonOnTouchListener(
					-1, -1));
			ImageView buttonRight = (ImageView) findViewById(R.id.button_right);
			buttonRight.setOnTouchListener(new DirectionButtonOnTouchListener(
					0.6, -0.6));

			SeekBar powerSeekBar = (SeekBar) findViewById(R.id.power_seekbar);
			powerSeekBar.setProgress(mPower);
			powerSeekBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							mPower = progress;
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
						}
					});

			mStateDisplay = (TextView) findViewById(R.id.state_display);

			mConnectButton = (ImageView) findViewById(R.id.button_b);
			mConnectButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!NO_BT) {
						findBrick();
					} else {
						mState = NXTTalker.STATE_CONNECTED;
						displayState();
					}
				}
			});

			mDisconnectButton = (ImageView) findViewById(R.id.button_a);
			mDisconnectButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mNXTTalker.stop();
				}
			});

			displayState();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		if (!NO_BT) {
			if (!mBluetoothAdapter.isEnabled()) {
				startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
			} else {
				if (mSavedState == NXTTalker.STATE_CONNECTED) {
					BluetoothDevice device = mBluetoothAdapter
							.getRemoteDevice(mDeviceAddress);
					mNXTTalker.connect(device);
				} else {
					if (mNewLaunch) {
						mNewLaunch = false;
						findBrick();
					}
				}
			}
		}
	}

	private void findBrick() {
		startActivityForResult(new Intent(this, ChooseDeviceActivity.class), REQUEST_CONNECT_DEVICE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				findBrick();
			} else {
				Toast.makeText(this, "Bluetooth not enabled, exiting.",
						Toast.LENGTH_LONG).show();
				finish();
			}
			break;
		case REQUEST_CONNECT_DEVICE:
			if (resultCode == Activity.RESULT_OK) {
				String address = data.getExtras().getString(
						ChooseDeviceActivity.EXTRA_DEVICE_ADDRESS);
				BluetoothDevice device = mBluetoothAdapter
						.getRemoteDevice(address);
				mDeviceAddress = address;
				mNXTTalker.connect(device);
			}
			break;
		case REQUEST_SETTINGS:
		
			break;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		setupUI();
	}

	private void displayState() {
		String stateText = null;
		int color = 0;
		switch (mState) {
		case NXTTalker.STATE_NONE:
			stateText = "NOT CONNECTED";
			color = 0xffff0000;
			mConnectButton.setVisibility(View.VISIBLE);
			mDisconnectButton.setVisibility(View.GONE);
			setProgressBarIndeterminateVisibility(false);
			if (mWakeLock.isHeld()) {
				mWakeLock.release();
			}
			break;
		case NXTTalker.STATE_CONNECTING:
			stateText = "CONNECTING";
			color = 0xffffff00;
			mConnectButton.setVisibility(View.GONE);
			mDisconnectButton.setVisibility(View.GONE);
			setProgressBarIndeterminateVisibility(true);
			if (!mWakeLock.isHeld()) {
				mWakeLock.acquire();
			}
			break;
		case NXTTalker.STATE_CONNECTED:
			stateText = "CONNECTED";
			color = 0xff00ff00;
			mConnectButton.setVisibility(View.GONE);
			mDisconnectButton.setVisibility(View.VISIBLE);
			setProgressBarIndeterminateVisibility(false);
			if (!mWakeLock.isHeld()) {
				mWakeLock.acquire();
			}
			break;
		}
		mStateDisplay.setText(stateText);
		mStateDisplay.setTextColor(color);
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			case MESSAGE_STATE_CHANGE:
				mState = msg.arg1;
				displayState();
				break;
			}
		}
	};

	@Override
	protected void onStop() {
		super.onStop();

		mSavedState = mState;
		mNXTTalker.stop();
		if (mWakeLock.isHeld()) {
			mWakeLock.release();
		}
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		readPreferences(sharedPreferences, key);
	}

	private void readPreferences(SharedPreferences prefs, String key) {
		if (key == null) {
			mReverse = prefs.getBoolean("PREF_SWAP_FWDREV", false);
			mReverseLR = prefs.getBoolean("PREF_SWAP_LEFTRIGHT", false);
			mRegulateSpeed = prefs.getBoolean("PREF_REG_SPEED", false);
			mSynchronizeMotors = prefs.getBoolean("PREF_REG_SYNC", false);
			if (!mRegulateSpeed) {
				mSynchronizeMotors = false;
			}
		} else if (key.equals("PREF_SWAP_FWDREV")) {
			mReverse = prefs.getBoolean("PREF_SWAP_FWDREV", false);
		} else if (key.equals("PREF_SWAP_LEFTRIGHT")) {
			mReverseLR = prefs.getBoolean("PREF_SWAP_LEFTRIGHT", false);
		} else if (key.equals("PREF_REG_SPEED")) {
			mRegulateSpeed = prefs.getBoolean("PREF_REG_SPEED", false);
			if (!mRegulateSpeed) {
				mSynchronizeMotors = false;
			}
		} else if (key.equals("PREF_REG_SYNC")) {
			mSynchronizeMotors = prefs.getBoolean("PREF_REG_SYNC", false);
		}
	}
}