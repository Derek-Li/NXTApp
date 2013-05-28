package com.example.slidingmenuexample.ui;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.example.slidingmenuexample.BaseActivity;
import com.example.slidingmenuexample.MenuListPages;
import com.example.slidingmenuexample.R;

public class MainActivity extends BaseActivity {

	public MainActivity() {
		super(R.string.app_name);
	}

	public Fragment MApieces;   

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		if (savedInstanceState != null)
			MApieces = getSupportFragmentManager().getFragment(
					savedInstanceState, "MApieces");
		if (MApieces == null)
			MApieces = new AllContent();

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, MApieces).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListPages()).commit();
		setSlidingActionBarEnabled(true);

	}
	
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setTitle("Exit?")
				.setMessage("Do you really want to exit?")
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface x, int y) {
								MainActivity.super.onBackPressed();
								finish();
							}
						}).create().show();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "MApieces", MApieces);
	}

	public void switchContent(Fragment fragment) {
		MApieces = fragment;
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

	public static class AllContent extends SherlockFragment {

		ImageView btnOrange, btnGray, avatar;
		TextView userName, teamName, teamNum, grade, schoolName, teacherName;
		SharedPreferences someData;
		public static String filename = "JohnDoe";
		Intent i;
		final static int CameraData = 0;
		Bitmap bmp;

		public AllContent() {

		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.activity_main, container, false);

		}

		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();

			btnOrange = (ImageView) getView().findViewById(R.id.orangebutton);
			btnOrange.setImageResource(R.drawable.camera);
			
			btnGray = (ImageView) getView().findViewById(R.id.graybutton);
			btnGray.setImageResource(R.drawable.graybutton);
			
			avatar = (ImageView) getView().findViewById(R.id.targetimage);

			userName = (TextView) getView().findViewById(R.id.userName);
			teamName = (TextView) getView().findViewById(R.id.teamName);
			teamNum = (TextView) getView().findViewById(R.id.teamNum);
			grade = (TextView) getView().findViewById(R.id.grade);
			schoolName = (TextView) getView().findViewById(R.id.schoolName);
			teacherName = (TextView) getView().findViewById(R.id.teacherName);

			addListenerOnImageView(btnOrange);
			addListenerOnImageView(btnGray);

			InputStream is = getResources().openRawResource(
					R.drawable.ic_launcher);
			bmp = BitmapFactory.decodeStream(is);

			someData = getActivity().getSharedPreferences(filename, 0);

			String dataReturned = someData.getString("userName", "John Doe");
			userName.setText("Username:  \t\t" + dataReturned);

			dataReturned = someData.getString("teamName", "RamRod");
			teamName.setText("Team name: \t" + dataReturned);

			dataReturned = someData.getString("teamNum", "2");
			teamNum.setText("Team #:    \t\t" + dataReturned);

			dataReturned = someData.getString("grade", "5");
			grade.setText("Grade:     \t\t\t" + dataReturned);

			dataReturned = someData.getString("schoolName", "St. Agnes");
			schoolName.setText("School:    \t\t" + dataReturned);

			dataReturned = someData.getString("teacherName", "Mr. Bob");
			teacherName.setText("Teacher:   \t\t" + dataReturned);

			dataReturned = someData.getString("uriName",
					"content://media/external/images/media/8775");

			Uri newUri = Uri.parse(dataReturned);
			Bitmap bitmap;
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 10;
				bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(newUri), null, options);
				avatar.setImageBitmap(bitmap);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
	

		public void addListenerOnImageView(ImageView I_m) {

			I_m.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.orangebutton:
						cameraCall();
						break;
					case R.id.graybutton:
						startActivity(new Intent(
								"com.example.slidingmenuexample.ui.Registration"));
					}
				}
			});
		}

		private void cameraCall() {
			startActivityForResult(new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE),
					CameraData);
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				bmp = (Bitmap) extras.get("data");
			}
		}
	}
}
