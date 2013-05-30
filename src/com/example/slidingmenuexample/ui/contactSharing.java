package com.example.slidingmenuexample.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.example.slidingmenuexample.MenuListPages;
import com.example.slidingmenuexample.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class contactSharing extends SlidingFragmentActivity {

	private Fragment CSpieces;
	SlidingMenu pagelist;
	public static final String FB_IMG_REF = "facebook";
	public static final String TWITTER_IMG_REF = "twitter";
	public static final String INSTAGRAM_IMG_REF = "instagram";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pagelist = getSlidingMenu();
		pagelist.setShadowDrawable(R.drawable.shadow);
		pagelist.setBehindOffset(600);
		pagelist.setShadowWidth(15);
		pagelist.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		if (savedInstanceState != null)
			CSpieces = getSupportFragmentManager().getFragment(
					savedInstanceState, "CSpieces");
		if (CSpieces == null)
			CSpieces = new contactSharingView();

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, CSpieces).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListPages()).commit();

		setSlidingActionBarEnabled(true);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "CSpieces", CSpieces);
	}

	public void switchContent(Fragment fragment) {
		CSpieces = fragment;
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

		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}
	}

}