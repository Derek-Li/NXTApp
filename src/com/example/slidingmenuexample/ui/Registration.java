package com.example.slidingmenuexample.ui;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.slidingmenuexample.R;

public class Registration extends Activity implements OnClickListener {
	public static String filename = "JohnDoe";
	SharedPreferences someData;
	Button okBtn, buttonLoadImage;
	EditText userName, teamName, teamNum, grade, schoolName, teacherName;
	ImageView targetImage;
	String stringUri = "content://media/external/images/media/8775";;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		initialize();
		someData = getSharedPreferences(filename, 0);

		String dataReturned = someData.getString("userName", "");
		userName.setText(dataReturned);

		dataReturned = someData.getString("teamName", "");
		teamName.setText(dataReturned);

		dataReturned = someData.getString("teamNum", "");
		teamNum.setText(dataReturned);

		dataReturned = someData.getString("grade", "");
		grade.setText(dataReturned);

		dataReturned = someData.getString("schoolName", "");
		schoolName.setText(dataReturned);

		dataReturned = someData.getString("teacherName", "");
		teacherName.setText(dataReturned);

		dataReturned = someData.getString("uriName",
				"content://media/external/images/media/8775");

		Uri newUri = Uri.parse(dataReturned);
		Bitmap bitmap;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 10;
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(newUri), null, options);
			targetImage.setImageBitmap(bitmap);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initialize() {
		okBtn = (Button) findViewById(R.id.bOK);
		buttonLoadImage = (Button) findViewById(R.id.loadimage);
		userName = (EditText) findViewById(R.id.user_name);
		teamName = (EditText) findViewById(R.id.team_name);
		teamNum = (EditText) findViewById(R.id.team_number);
		grade = (EditText) findViewById(R.id.grade);
		schoolName = (EditText) findViewById(R.id.school_name);
		teacherName = (EditText) findViewById(R.id.teach_name);
		targetImage = (ImageView) findViewById(R.id.targetimage);

		okBtn.setOnClickListener(this);

		buttonLoadImage.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivityForResult(
						new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
						0);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		String stringData = userName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "";
		}
		SharedPreferences.Editor editor = someData.edit();
		editor.putString("userName", stringData);
		stringData = teamName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "";
		}
		editor.putString("teamName", stringData);
		stringData = teamNum.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "";
		}
		editor.putString("teamNum", stringData);
		stringData = grade.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "";
		}
		editor.putString("grade", stringData);
		stringData = schoolName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "";
		}
		editor.putString("schoolName", stringData);
		stringData = teacherName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "";
		}
		editor.putString("teacherName", stringData);

		stringData = stringUri;
		if (stringData.isEmpty()) {
			stringData = "content://media/external/images/media/8775";
		}
		editor.putString("uriName", stringData);
		editor.commit();

		startActivity(new Intent(Registration.this, MainActivity.class));
		finish();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        Intent backToMA = new Intent(this, MainActivity.class);
	        backToMA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(backToMA);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}   

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();
			stringUri = targetUri.toString();
			Bitmap bitmap;
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 10;

				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(targetUri), null, options);
				targetImage.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}