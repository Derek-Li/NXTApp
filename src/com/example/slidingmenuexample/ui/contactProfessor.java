package com.example.slidingmenuexample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.example.slidingmenuexample.R;
import com.example.slidingmenuexample.MenuListPages;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class contactProfessor extends SlidingFragmentActivity {

	private Fragment mContent;

	protected ListFragment mFrag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_professor);

		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidth(15);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset(600);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new contactProfessorView();

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListPages()).commit();

		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		setSlidingActionBarEnabled(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
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

	public static class contactProfessorView extends SherlockFragment {

		EditText contentBar, subjectBar, emailBar;
		TextView igotdis;
		Button submit;
		String inputContent, inputEmail, inputSubject;
		
		public contactProfessorView() {
			
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.contact_professor, container,
					false);
		}
		
		public void onStart() {
			//TODO Auto-generated method
			super.onStart();
			
			emailBar = (EditText) getView().findViewById(R.id.emailInput);
			subjectBar = (EditText) getView().findViewById(R.id.subjectInput);
			contentBar = (EditText) getView().findViewById(R.id.contentInput);

			submit = (Button) getView().findViewById(R.id.submit);
			igotdis = (TextView) getView().findViewById(R.id.received);
			
			addListenerOnButton(submit);
			//addListenerOnTextView(igotdis);
		}
		
//		public void addListenerOnTextView(TextView I_m) {
//
//			I_m.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					startActivity(new Intent(getActivity(), MainActivity.class));
//				}
//			});
//		}

		public void addListenerOnButton(Button I_m) {

			I_m.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					inputEmail = emailBar.getText().toString();
					inputSubject = subjectBar.getText().toString();
					inputContent = contentBar.getText().toString();

					String combined = "Email: " + inputEmail + "\n" + "Subject: "
							+ inputSubject + "\n" + "\t" + inputContent;

					igotdis.setText(combined);
				}
			});
		}

	}

}