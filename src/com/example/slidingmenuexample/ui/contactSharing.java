package com.example.slidingmenuexample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.example.slidingmenuexample.R;
import com.example.slidingmenuexample.MenuListPages;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class contactSharing extends SlidingFragmentActivity {

	private Fragment mContent;

	protected ListFragment mFrag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 SlidingMenu sm = getSlidingMenu();
		 sm.setShadowWidth(15);
		 sm.setShadowDrawable(R.drawable.shadow);
		 sm.setBehindOffset(600);
		 sm.setFadeDegree(0.35f);
		 sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setIcon(R.drawable.ic_launcher);

		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new contactSharingView();

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListPages()).commit();
		
		 getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		 setSlidingActionBarEnabled(true);

	}

	public void switchPage(View test) {
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

	public static class contactSharingView extends SherlockFragment {

		public static ImageView fbShare, twitShare, instaShare;

		public contactSharingView() { 
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.sharing, container, false);
		}

//		public void onStart() {
//			// TODO Auto-generated method stub
//			super.onStart();
//
//			fbShare = (ImageView) getView().findViewById(R.id.facebook);
//			twitShare = (ImageView) getView().findViewById(R.id.twitter);
//			instaShare = (ImageView) getView().findViewById(R.id.instagram);
//
//			addListenerOnImageView(fbShare);
//			addListenerOnImageView(twitShare);
//			addListenerOnImageView(instaShare);
//		}
//
//		public void addListenerOnImageView(ImageView I_m) {
//
//			I_m.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					startActivity(new Intent(getActivity(), MainActivity.class));
//				}
//			});
//		}
	}

}