package com.example.slidingmenuexample.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.slidingmenuexample.R;

public class RegisterAcceptActivity extends Activity implements
		View.OnClickListener {

	Button later, now;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.registrationaccept);
		later = (Button) findViewById(R.id.btnLater);
		now = (Button) findViewById(R.id.btnNow);

		later.setOnClickListener(this);
		now.setOnClickListener(this);
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLater:
			laterCall();
			break;
		case R.id.btnNow:
			nowCall();
			break;
		}
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
								RegisterAcceptActivity.super.onBackPressed();
								finish();
							}
						}).create().show();
	}

	private void nowCall() {
		startActivity(new Intent(RegisterAcceptActivity.this,
				Registration.class));
		finish();
	}

	private void laterCall() {
		startActivity(new Intent(RegisterAcceptActivity.this,
				MainActivity.class));
		finish();
	}
}