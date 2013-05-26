package com.example.slidingmenuexample.ui;

import java.io.FileNotFoundException;

import com.example.slidingmenuexample.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Registration extends Activity implements OnClickListener {
	public static String filename = "JohnDoe";
	SharedPreferences someData;
	Button okBtn, buttonLoadImage;
	EditText userName, teamName, teamNum, grade, schoolName, teacherName;
	ImageView targetImage;
	String stringUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		initialize();
		someData = getSharedPreferences(filename, 0);
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
				startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 0);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		String stringData = userName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "Oops";
		}
		SharedPreferences.Editor editor = someData.edit();
		editor.putString("userName", stringData);
		stringData = teamName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "Oops2";
		}
		editor.putString("teamName", stringData);
		stringData = teamNum.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "Oops3";
		}
		editor.putString("teamNum", stringData);
		stringData = grade.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "Oops4";
		}
		editor.putString("grade", stringData);
		stringData = schoolName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "Oops5";
		}
		editor.putString("schoolName", stringData);
		stringData = teacherName.getText().toString();
		if (stringData.isEmpty()) {
			stringData = "Oops6";
		}
		editor.putString("teacherName", stringData);

		stringData = stringUri;
		if (stringData.isEmpty()) {
			stringData = "content://media/external/images/media/8775";
		}
		editor.putString("uriName", stringData);
		editor.commit();
		
		startActivity(new Intent(Registration.this, MainActivity.class));
		
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
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(targetUri));
				targetImage.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}