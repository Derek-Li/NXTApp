package com.example.slidingmenuexample.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.slidingmenuexample.R;
import com.example.slidingmenuexample.tutorial.TutorialAcceptActivity;

public class SplashActivity extends Activity implements View.OnClickListener {

	Button disagree, agree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash);
		getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		disagree = (Button) findViewById(R.id.bdisagree);
		agree = (Button) findViewById(R.id.bagree);

		agree.setOnClickListener(this);
		disagree.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bdisagree:
			disagreeCall();
			break;
		case R.id.bagree:
			agreeCall();
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
								SplashActivity.super.onBackPressed();
								finish();
							}
						}).create().show();
	}

	private void agreeCall() {
		startActivity(new Intent(SplashActivity.this,
				TutorialAcceptActivity.class));
		finish();
	}

	private void disagreeCall() {
		finish();
		System.exit(0);
	}
}