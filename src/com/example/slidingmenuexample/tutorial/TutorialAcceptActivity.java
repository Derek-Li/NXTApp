package com.example.slidingmenuexample.tutorial;


import com.example.slidingmenuexample.R;
import com.example.slidingmenuexample.R.id;
import com.example.slidingmenuexample.R.layout;
import com.example.slidingmenuexample.ui.RegisterAcceptActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TutorialAcceptActivity extends Activity implements View.OnClickListener {

	Button disagree, agree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);     

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.tutorialaccept);
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
	        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface x, int y) {
	            	TutorialAcceptActivity.super.onBackPressed();
	            	finish();
	            }
	        }).create().show();
	}

	private void agreeCall() {
		startActivity(new Intent(TutorialAcceptActivity.this, TutorialPart1.class));
		finish();
	}

	private void disagreeCall() {
		startActivity(new Intent(TutorialAcceptActivity.this, RegisterAcceptActivity.class));
		finish();
	}
}