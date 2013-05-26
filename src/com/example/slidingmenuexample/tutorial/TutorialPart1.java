package com.example.slidingmenuexample.tutorial;

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
import com.example.slidingmenuexample.ui.MainActivity;

public class TutorialPart1 extends Activity implements View.OnClickListener {

	Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);     

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.tutorial_1);
		next = (Button) findViewById(R.id.nextpage);

		next.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		if(v.getId() == R.id.nextpage) {
			nextPressed();
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
	            	TutorialPart1.super.onBackPressed();
	            	finish();
	            }
	        }).create().show();
	}

	private void nextPressed() {
		startActivity(new Intent(TutorialPart1.this, MainActivity.class));
		finish();
	}
}