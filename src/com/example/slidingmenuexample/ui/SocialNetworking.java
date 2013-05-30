package com.example.slidingmenuexample.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.example.slidingmenuexample.MenuListPages;
import com.example.slidingmenuexample.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class SocialNetworking extends SlidingFragmentActivity {

	private Fragment CPpieces;
	SlidingMenu pagelist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_networking);

		pagelist = getSlidingMenu();
		pagelist.setShadowDrawable(R.drawable.shadow);
		pagelist.setBehindOffset(600);
		pagelist.setShadowWidth(15);
		pagelist.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		if (savedInstanceState != null)
			CPpieces = getSupportFragmentManager().getFragment(
					savedInstanceState, "CPpieces");
		if (CPpieces == null)
			CPpieces = new socialNetworkingView();

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, CPpieces).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListPages()).commit();

		setSlidingActionBarEnabled(true);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "CPpieces", CPpieces);
	}

	public void switchContent(Fragment fragment) {
		CPpieces = fragment;
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

	public static class socialNetworkingView extends SherlockFragment {
		Button email, options, wikispacesbtn;
		ImageView instagram, facebook, twitter;

		public socialNetworkingView() {

		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.social_networking, container,
					false);
		}

		public void onStart() {
			super.onStart();

			instagram = (ImageView) getView().findViewById(R.id.instagrambtn);
			facebook = (ImageView) getView().findViewById(R.id.facebookbtn);
			twitter = (ImageView) getView().findViewById(R.id.twitterbtn);
			email = (Button) getView().findViewById(R.id.emailbtn);
			options = (Button) getView().findViewById(R.id.options);
			wikispacesbtn = (Button) getView().findViewById(R.id.wikispacesbtn);

			wikispacesbtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = "http://www.wikispaces.com/";
					Intent wikispacesIntent = new Intent(
							android.content.Intent.ACTION_VIEW);
					wikispacesIntent.setData(Uri.parse(url));
					startActivity(wikispacesIntent);
				}
			});

			facebook.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {

						Intent intent = new Intent(Intent.ACTION_VIEW, Uri
								.parse("fb://?ref=tn_tnmn"));
						startActivity(intent);

					} catch (Exception e) {

						startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse("http://www.facebook.com/?ref=tn_tnmn")));
					}
				}
			});

			twitter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = "https://twitter.com/intent/tweet?source=webclient&text=TWEET+THIS!";
					Intent twitterIntent = new Intent(
							android.content.Intent.ACTION_VIEW);
					twitterIntent.setData(Uri.parse(url));
					startActivity(twitterIntent);
				}
			});

			instagram.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = "https://instagram.com/accounts/login/";
					Intent instagramIntent = new Intent(
							android.content.Intent.ACTION_VIEW);
					instagramIntent.setData(Uri.parse(url));
					startActivity(instagramIntent);
				}
			});

			email.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent emailIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					emailIntent.setType("text/plain");
					startActivity(emailIntent);
				}
			});

			options.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent othersIntent = new Intent(
							android.content.Intent.ACTION_VIEW);
					startActivity(Intent.createChooser(othersIntent,
							"Choose one"));
				}
			});
		}
	}
}
